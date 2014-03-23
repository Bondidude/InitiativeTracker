package com.benparksfordesigner.initiativepicker;

import com.benparksfordesigner.initiativepicker.Party.Party_Member;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DB_Adapter_Activity {
	static final String KEY_ROWID = "_id";
	static final String KEY_CHARACTER_NAME = "name";
	static final String KEY_PLAYER_NAME = "player_name";
	static final String KEY_INITIATIVE = "initiative";
	static final String KEY_INITIATIVE_MOD = "initiative_mod";
	static final String KEY_DEFENSE = "defense";
	static final String KEY_PLAYER_TYPE = "player_character";
	
	static final String KEY_ENCOUNTER_NAME = "encounter_name";
	
	static final String KEY_PARTY_NAME = "party_name";
	static final String KEY_ENCOUNTER_ID = "encounter_id";
	static final String KEY_ENCOUNTER_PARTY = "encounter_party";
	
	static final String KEY_PARTY_MEMBER_ID = "_id";
	static final String KEY_PARTY_CHARACTER_ID = "character_id";
	static final String KEY_PARTY_ID = "party_id";
	
	static final String TAG = "DBAdapter";
	
	static final String DATABASE_NAME = "gm_tool_db";
	static final String INIT_TABLE = "initiative";
	static final String ENCOUNTER_TABLE = "encounters";
	static final String PARTY_TABLE = "parties";
	static final String PARTY_MEMBER_TABLE = "party_members";
	static final int DATABASE_VERSION = 1;
	
	static final String[] characterColumnArray = new String[] {KEY_ROWID, KEY_CHARACTER_NAME, /*KEY_PLAYER_NAME,*/
		KEY_INITIATIVE, KEY_INITIATIVE_MOD, KEY_DEFENSE, KEY_PLAYER_TYPE};
	static final String[] encounterColumnArray = new String[] {KEY_ROWID, KEY_ENCOUNTER_NAME };
	static final String[] partyColumnArray = new String[] {KEY_ROWID, KEY_PARTY_NAME, KEY_ENCOUNTER_ID,
		KEY_ENCOUNTER_PARTY};
	static final String[] partyMemberColumnArray = new String[] {KEY_PARTY_MEMBER_ID, KEY_PARTY_CHARACTER_ID, 
		KEY_PARTY_ID};
	
	static final String CREATE_INIT_TABLE =
			"create table initiative (_id integer primary key autoincrement, "
				+ "name text not null," + /*player_name text not null,*/ "initiative integer not null, "
				+ "initiative_mod integer not null, defense integer not null,"
				+ "player_character integer not null);";
			
	static final String CREATE_ENC_TABLE = 
			"create table encounters (_id integer primary key autoincrement, "
				+ "encounter_name text not null);";
	
	static final String CREATE_PARTY_TABLE = 
			"create table parties (_id integer primary key autoincrement,"
				+"party_name text not null, encounter_id int not null, encounter_party int not null);";
	
	static final String CREATE_PARTY_MEMBER_TABLE =
			"create table party_members (_id integer primary key autoincrement,"
				+"character_id integer not null, party_id integer not null);";
	
	final Context context;
	
	DatabaseHelper DBHelper;
	SQLiteDatabase db;
	
	public DB_Adapter_Activity(Context ctx) {
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}
	
	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		
		@Override
		public void onCreate(SQLiteDatabase db) {
			try {
				db.execSQL(CREATE_INIT_TABLE);
				db.execSQL(CREATE_ENC_TABLE);
				db.execSQL(CREATE_PARTY_TABLE);
				db.execSQL(CREATE_PARTY_MEMBER_TABLE);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS initiative");
			onCreate(db);
		}
	}
	
	//--open the db--
	public DB_Adapter_Activity open() throws SQLException {
		db = DBHelper.getWritableDatabase();
		return this;
	}
	
	//--close the db--
	public void close() {
		DBHelper.close();
	}
	
	//Character DB Actions
	
	//--insert a Character into the db--
	public long insertCharacter (Character c) {
		ContentValues initInitialValues = new ContentValues();
		initInitialValues.put(KEY_CHARACTER_NAME, c.getCharacterName());
		//initialValues.put(KEY_PLAYER_NAME, c.getPlayerName());
		initInitialValues.put(KEY_INITIATIVE, c.getInitiative());
		initInitialValues.put(KEY_INITIATIVE_MOD, c.getInitiativeMod());
		initInitialValues.put(KEY_DEFENSE, c.getDefense());
		initInitialValues.put(KEY_PLAYER_TYPE, c.getPlayer());
		
		return db.insert(INIT_TABLE, null, initInitialValues);
	}
	
	//--delete a character from the db--
	public boolean deleteCharacter(long rowId) {
		return db.delete(INIT_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
	}
	
	//--retrieve all the characters from the db--
	public Cursor getAllCharacters() {
		return db.query(INIT_TABLE, characterColumnArray,
			null, null, null, null, null);
	}
	
	//--retrieve a particular character --
	public Cursor getCharacter(long rowId) throws SQLException {
		Cursor cCursor =
			db.query(true, INIT_TABLE, characterColumnArray, 
					KEY_ROWID + "=" + rowId, null, null, null, null, null);
		if(cCursor != null) {
			cCursor.moveToFirst();
		}
		
		return cCursor;
	}
	
	//--update a character --
	public boolean updateCharacter(long rowId, String characterName, /*String playerName,*/ int initiative, 
			int initiativeMod, int defense, Boolean player) {
		ContentValues args = new ContentValues();
		args.put(KEY_CHARACTER_NAME, characterName);
		//args.put(KEY_PLAYER_NAME, playerName);
		args.put(KEY_INITIATIVE, initiative);
		args.put(KEY_INITIATIVE_MOD, initiativeMod);
		args.put(KEY_DEFENSE, defense);
		args.put(KEY_PLAYER_TYPE, player);
		return db.update(INIT_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
	}
	
	//Encounter DB Actions
	public long insertEncounter(Encounter e) {
		ContentValues encounterInitialValues = new ContentValues();
		
		encounterInitialValues.put(KEY_ENCOUNTER_NAME, e.getEncounterName());
		
		return db.insert(ENCOUNTER_TABLE, null, encounterInitialValues);
	}
	
	public Boolean deleteEncounter(long rowId) {
		return db.delete(ENCOUNTER_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
	}
	
	public Cursor getAllEncounters() {
		return db.query(ENCOUNTER_TABLE, encounterColumnArray, 
				null, null, null, null, null);
	}
	
	public Cursor getEncounter(long rowId) throws SQLException {
		Cursor eCursor = 
			db.query(true, ENCOUNTER_TABLE, encounterColumnArray,
				KEY_ROWID + "=" + rowId, null, null, null, null, null);
		if(eCursor != null) {
			eCursor.moveToFirst();
		}
		
		return eCursor;
	}
	
	public boolean updateEncounter(long rowId, String encounterName) {
		ContentValues args = new ContentValues();
		
		args.put(KEY_ENCOUNTER_NAME, encounterName);
		
		return db.update(ENCOUNTER_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
	}
	
	//Party DB actions
	
	public long insertParty(Party p) {
		ContentValues initialParty = new ContentValues();
		
		initialParty.put(KEY_PARTY_NAME, p.getPartyName());
		initialParty.put(KEY_ENCOUNTER_ID, p.getPartyEncounterId());
		initialParty.put(KEY_ENCOUNTER_PARTY, p.getEncounterParty());
		
		return db.insert(PARTY_TABLE, null, initialParty);
		
	}
	
	public boolean deleteParty(long rowId) {
		return db.delete(PARTY_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
	}
	
	public Cursor getAllNonEncounterParties() {
		return db.query(PARTY_TABLE, partyColumnArray, "encounter_party = 1", null, null, null, null);
	}
	
	public Cursor getParty(long rowId) throws SQLException {
		Cursor pCursor = db.query(
				PARTY_TABLE, partyColumnArray, KEY_ROWID + "=" + rowId, null, null, null, null);
		if(pCursor != null) {
			pCursor.moveToFirst();
		}
		
		return pCursor;
	}
	
	public boolean updateParty(long rowId, String partyName, int encounterId,
		Boolean encounterParty) {
		ContentValues updateParty = new ContentValues();
		
		updateParty.put(KEY_PARTY_NAME, partyName);
		updateParty.put(KEY_ENCOUNTER_ID, encounterId);
		updateParty.put(KEY_ENCOUNTER_PARTY, encounterParty);
		
		return db.update(PARTY_TABLE, updateParty, KEY_ROWID + "=" + rowId, null) > 0;
	}
	
	//Party Member DB Actions
	
	public void insertPartyMember(Party_Member pm) {
		ContentValues initialPM = new ContentValues();
		
		initialPM.put(KEY_PARTY_MEMBER_ID, pm.getPartyMemberId());
		initialPM.put(KEY_PARTY_CHARACTER_ID, pm.getPartyMemberCharacterId());
		initialPM.put(KEY_PARTY_ID, pm.getPartyMemberPartyId());
		
		db.insert(PARTY_MEMBER_TABLE, null, initialPM);
	}
	
	public boolean deletePartyMember(long rowId) {
		return db.delete(PARTY_MEMBER_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
	}
	
	public Cursor getPartyMembers(int partyId) throws SQLException {
		Cursor pmCursor = db.query(PARTY_MEMBER_TABLE, partyMemberColumnArray, "partyId = " + 
			partyId, null, null, null, null);
		
		if(pmCursor != null) {
			pmCursor.moveToFirst();
		}
		
		return pmCursor;
	}
	
	public boolean updatePartyMember(long rowId, int characterId, int partyId) {
		ContentValues updatePM = new ContentValues();
		
		updatePM.put(KEY_PARTY_CHARACTER_ID, characterId);
		updatePM.put(KEY_PARTY_ID, partyId);
		
		return db.update(PARTY_MEMBER_TABLE, updatePM, KEY_ROWID + "=" + rowId, null) > 0;
	}
}
