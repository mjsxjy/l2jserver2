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
package com.l2jserver.service.database.orientdb;

import java.sql.SQLException;
import java.util.List;

import com.google.inject.Inject;
import com.l2jserver.model.Model;
import com.l2jserver.model.dao.CharacterDAO;
import com.l2jserver.model.dao.ClanDAO;
import com.l2jserver.model.id.ChatMessageID;
import com.l2jserver.model.id.object.CharacterID;
import com.l2jserver.model.id.object.ClanID;
import com.l2jserver.model.id.object.provider.CharacterIDProvider;
import com.l2jserver.model.id.object.provider.ClanIDProvider;
import com.l2jserver.model.world.Clan;
import com.l2jserver.service.database.AbstractOrientDatabaseService.CachedMapper;
import com.l2jserver.service.database.AbstractOrientDatabaseService.InsertUpdateQuery;
import com.l2jserver.service.database.AbstractOrientDatabaseService.Mapper;
import com.l2jserver.service.database.AbstractOrientDatabaseService.SelectListQuery;
import com.l2jserver.service.database.AbstractOrientDatabaseService.SelectSingleQuery;
import com.l2jserver.service.database.DatabaseService;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.query.nativ.ONativeSynchQuery;
import com.orientechnologies.orient.core.query.nativ.OQueryContextNative;
import com.orientechnologies.orient.core.record.impl.ODocument;

/**
 * {@link CharacterDAO} implementation for JDBC
 * 
 * @author <a href="http://www.rogiel.com">Rogiel</a>
 */
public class OrientDBClanDAO extends AbstractOrientDBDAO<Clan, ClanID>
		implements ClanDAO {
	/**
	 * The {@link ChatMessageID} factory
	 */
	private final ClanIDProvider idFactory;
	/**
	 * The {@link CharacterID} factory
	 */
	private final CharacterIDProvider charIdFactory;

	/**
	 * Clan table name
	 */
	public static final String CLASS_NAME = Clan.class.getSimpleName();
	// FIELDS
	public static final String CLAN_ID = "clan_id";
	public static final String CHAR_ID_LEADER = "character_id_leader";

	/**
	 * @param database
	 *            the database service
	 * @param idFactory
	 *            the chat message id provider
	 * @param charIdFactory
	 *            the character id provider
	 */
	@Inject
	public OrientDBClanDAO(DatabaseService database, ClanIDProvider idFactory,
			final CharacterIDProvider charIdFactory) {
		super(database);
		this.idFactory = idFactory;
		this.charIdFactory = charIdFactory;
	}

	/**
	 * The {@link Mapper} for {@link ClanID}
	 */
	private final Mapper<ClanID> idMapper = new Mapper<ClanID>() {
		@Override
		public ClanID map(ODocument document) throws SQLException {
			return idFactory.resolveID((Integer) document.field(CLAN_ID));
		}
	};

	/**
	 * The {@link Mapper} for {@link Clan}
	 */
	private final Mapper<Clan> mapper = new CachedMapper<Clan, ClanID>(
			database, idMapper) {
		@Override
		protected Clan map(ClanID id, ODocument document) throws SQLException {
			final Clan clan = new Clan();

			clan.setID(id);
			clan.setLeaderID(charIdFactory.resolveID((Integer) document
					.field(CHAR_ID_LEADER)));

			return clan;
		}
	};

	@Override
	public Clan select(final ClanID id) {
		return database.query(new SelectSingleQuery<Clan>() {
			@Override
			protected ONativeSynchQuery<OQueryContextNative> createQuery(
					ODatabaseDocumentTx database) {
				return new ONativeSynchQuery<OQueryContextNative>(database,
						CLASS_NAME, new OQueryContextNative()) {
					private static final long serialVersionUID = 1L;

					@Override
					public boolean filter(OQueryContextNative criteria) {
						return criteria.field(CLAN_ID).eq(id.getID()).go();
					};
				};
			}

			@Override
			protected Mapper<Clan> mapper() {
				return mapper;
			}
		});
	}

	@Override
	public List<ClanID> selectIDs() {
		return database.query(new SelectListQuery<ClanID>() {
			@Override
			protected ONativeSynchQuery<OQueryContextNative> createQuery(
					ODatabaseDocumentTx database) {
				return new ONativeSynchQuery<OQueryContextNative>(database,
						CLASS_NAME, new OQueryContextNative()) {
					private static final long serialVersionUID = 1L;

					@Override
					public boolean filter(OQueryContextNative criteria) {
						return true;
					};
				};
			}

			@Override
			protected Mapper<ClanID> mapper() {
				return idMapper;
			}
		});
	}

	@Override
	public int insertObjects(Clan... clans) {
		return database.query(new InsertUpdateQuery<Clan>(clans) {
			@Override
			protected ONativeSynchQuery<OQueryContextNative> createQuery(
					ODatabaseDocumentTx database, Clan object) {
				return null;
			}

			@Override
			protected ODocument update(ODocument document, Clan object)
					throws SQLException {
				return null;
			}

			@Override
			protected ODocument insert(ODocument document, Clan clan)
					throws SQLException {
				document.field(CLAN_ID, clan.getID().getID());
				document.field(CHAR_ID_LEADER, clan.getLeaderID().getID());
				return document;
			}
		});
	}

	@Override
	public int updateObjects(Clan... clans) {
		// cannot update chat message logs
		return 0;
	}

	@Override
	public int deleteObjects(Clan... clans) {
		return database.query(new InsertUpdateQuery<Clan>(clans) {
			@Override
			protected ONativeSynchQuery<OQueryContextNative> createQuery(
					ODatabaseDocumentTx database, final Clan clan) {
				return new ONativeSynchQuery<OQueryContextNative>(database,
						CLASS_NAME, new OQueryContextNative()) {
					private static final long serialVersionUID = 1L;

					@Override
					public boolean filter(OQueryContextNative criteria) {
						return criteria.field(CLAN_ID).eq(clan.getID()).go();
					};
				};
			}

			@Override
			protected ODocument update(ODocument document, Clan clan)
					throws SQLException {
				document.delete();
				return null;
			}

			@Override
			protected ODocument insert(ODocument document, Clan object)
					throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
		});
	}

	@Override
	protected Clan[] wrap(Model<?>... objects) {
		final Clan[] array = new Clan[objects.length];
		int i = 0;
		for (final Model<?> object : objects) {
			array[i++] = (Clan) object;
		}
		return array;
	}
}
