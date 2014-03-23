package com.benparksfordesigner.initiativepicker;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class Encounter_Input_Activity extends Activity {
	EditText editEncounterName;
	EditText editPartyId;
	long id = 0;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.encounter_input);
		
		editEncounterName = (EditText) findViewById(R.id.encounterNameInput);
		editPartyId = (EditText) findViewById(R.id.encounterPartyId);
	}
	
	public void submitEncounter() {
		Encounter e = new Encounter();
		
		if(editEncounterName.getText().toString() != "") {
			e.setEncounterName(editEncounterName.getText().toString());
		} else {
			Toast.makeText(getBaseContext(), getString(R.string.message_encounter_name), 
				Toast.LENGTH_LONG).show();
		}
		
		if(editPartyId.getText().toString() != "") {
			e.setPartyId(Integer.parseInt(editPartyId.getText().toString()));
		} else {
			Toast.makeText(getBaseContext(), getString(R.string.message_encounter_party),
				Toast.LENGTH_LONG).show();
		}
		
		DB_Adapter_Activity db = new DB_Adapter_Activity(this);
		
		db.open();
		id = db.insertEncounter(e);
		db.close();
		
		this.finish();
	}
	
}
