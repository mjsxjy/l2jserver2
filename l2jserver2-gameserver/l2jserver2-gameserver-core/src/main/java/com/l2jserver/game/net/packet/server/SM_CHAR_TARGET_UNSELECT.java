/*
 * This file is part of l2jserver2 <l2jserver2.com>.
 *
 * l2jserver2 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * l2jserver2 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with l2jserver2.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.l2jserver.game.net.packet.server;

import org.jboss.netty.buffer.ChannelBuffer;

import com.l2jserver.model.world.L2Character;
import com.l2jserver.service.network.model.Lineage2Client;
import com.l2jserver.service.network.model.packet.AbstractServerPacket;

/**
 * This packet notifies the client that the chosen character has been
 * successfully selected.
 * 
 * @author <a href="http://www.rogiel.com">Rogiel</a>
 */
public class SM_CHAR_TARGET_UNSELECT extends AbstractServerPacket {
	/**
	 * The packet OPCODE
	 */
	public static final int OPCODE = 0x2a;

	/**
	 * The character that as unselected an object
	 */
	private final L2Character character;

	/**
	 * @param character
	 *            the character
	 */
	public SM_CHAR_TARGET_UNSELECT(L2Character character) {
		super(OPCODE);
		this.character = character;
	}

	@Override
	public void write(Lineage2Client conn, ChannelBuffer buffer) {
		buffer.writeInt(character.getID().getID());
		buffer.writeInt(character.getPoint().getX());
		buffer.writeInt(character.getPoint().getY());
		buffer.writeInt(character.getPoint().getZ());
		buffer.writeInt(0x00); // ??
	}
}
