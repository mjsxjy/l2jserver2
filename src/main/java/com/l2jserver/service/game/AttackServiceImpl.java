/*
 * This file is part of l2jserver <l2jserver.com>.
 *
 * l2jserver is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * l2jserver is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with l2jserver.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.l2jserver.service.game;

import java.util.concurrent.Callable;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.l2jserver.model.server.AttackHit;
import com.l2jserver.model.server.attack.AttackCalculator;
import com.l2jserver.model.server.attack.AttackCalculatorContext;
import com.l2jserver.model.server.attack.PhysicalAttackCalculator;
import com.l2jserver.model.world.Actor;
import com.l2jserver.model.world.actor.event.ActorAttackHitEvent;
import com.l2jserver.service.AbstractService;
import com.l2jserver.service.AbstractService.Depends;
import com.l2jserver.service.core.threading.AsyncFuture;
import com.l2jserver.service.core.threading.ThreadService;
import com.l2jserver.service.game.world.event.WorldEventDispatcher;

/**
 * @author <a href="http://www.rogiel.com">Rogiel</a>
 */
@Depends({ ThreadService.class })
public class AttackServiceImpl extends AbstractService implements AttackService {
	/**
	 * Calculator used to compute physical attacks
	 */
	private static final AttackCalculator PHYSICAL_ATTACK_CALCULATOR = new PhysicalAttackCalculator();

	/**
	 * The {@link ThreadService} is used to schedule asynchronous attacks
	 */
	private final ThreadService threadService;
	/**
	 * The {@link WorldEventDispatcher} is used to dispatch attack events to the
	 * world
	 */
	private final WorldEventDispatcher eventDispatcher;

	@Inject
	public AttackServiceImpl(ThreadService threadService,
			WorldEventDispatcher eventDispatcher) {
		this.threadService = threadService;
		this.eventDispatcher = eventDispatcher;
	}

	@Override
	public AsyncFuture<AttackHit> attack(Actor attacker, Actor target) {
		Preconditions.checkNotNull(attacker, "attacker");
		Preconditions.checkNotNull(target, "target");
		Preconditions.checkArgument(!attacker.equals(target),
				"attacker must not be equal to target");
		return threadService.async(new AttackCallable(attacker, target));
	}

	/**
	 * {@link Callable} implementation used to execute attacks.
	 * 
	 * @author <a href="http://www.rogiel.com">Rogiel</a>
	 */
	private class AttackCallable implements Callable<AttackHit> {
		/**
		 * The attacker
		 */
		private final Actor attacker;
		/**
		 * The target
		 */
		private final Actor target;

		public AttackCallable(Actor attacker, Actor target) {
			this.attacker = attacker;
			this.target = target;
		}

		@Override
		public AttackHit call() throws Exception {
			final double damage = PHYSICAL_ATTACK_CALCULATOR
					.calculate(new AttackCalculatorContext(attacker, target));
			// TODO calculate miss
			// TODO calculate critical
			// TODO calculate soulshot

			final AttackHit hit = new AttackHit(attacker, target, damage);
			eventDispatcher.dispatch(new ActorAttackHitEvent(hit));
			return hit;
		}
	}
}
