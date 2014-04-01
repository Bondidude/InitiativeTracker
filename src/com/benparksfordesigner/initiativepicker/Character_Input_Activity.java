package com.benparksfordesigner.initiativepicker;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class Character_Input_Activity extends Activity {
	
	EditText editName;
	EditText editInitiative;
	EditText edtInitiativeMod;
	EditText editDefense;
	CheckBox editType;
	Character newC = new Character();
	Boolean editMode = false;
	long id = 0;
	Runnable buildNewCharacter;
	//private ProgressDialog saveCharacterDialog = null;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.character_input);
        
        if(savedInstanceState != null) {
        	savedInstanceState.getBundle("C_ID");
        	editMode = true;
        }
        
		editName = (EditText) findViewById(R.id.characterName);
    	editInitiative = (EditText) findViewById(R.id.initiative);
    	edtInitiativeMod = (EditText) findViewById(R.id.initiative_mod);
    	editDefense = (EditText) findViewById(R.id.defense);
    	editType = (CheckBox) findViewById(R.id.characterType);
	}
	
	public void submitCharacter(View view) {
    	
    	buildNewCharacter = new Runnable() {
    		@Override
    		public void run() {
    			buildCharacter();
    		}
    	};
		
    	Thread thread = new Thread(null, buildNewCharacter, "MagnetoBackground");
    	thread.run();
    	
    	//saveCharacterDialog = ProgressDialog.show(this, "Please wait...", "Saving character data", true);
	}
	
	private Runnable returnToInitiative = new Runnable() {
		@Override
		public void run() {
			//saveCharacterDialog.dismiss();
			newC = new Character();
	    	if(newC != null) {
				//Start the Intent to pass it back then add in the bits and pieces as extras
				Intent i = new Intent(getBaseContext(), Classic_Initiative_Activity.class);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
					finish();
	    	}
		}
	};
	
	private void buildCharacter() {
		
    	if(editName.getText().toString() != "") {
    		newC.setCharacterName(editName.getText().toString());
    	} else {
    		Toast.makeText(getBaseContext(), "You must enter a name", Toast.LENGTH_LONG).show();
    	}
    	
    	if(editInitiative.getText().toString() != "") {
    		newC.initiative = Integer.parseInt(editInitiative.getText().toString());
    	} else {
    		Toast.makeText(getBaseContext(), "You must enter an Initiative", Toast.LENGTH_LONG).show();    	
    	}
    	
    	if(edtInitiativeMod.getText().toString() != "") {
    		newC.initiativeMod = Integer.parseInt(edtInitiativeMod.getText().toString());
    	} else {
    		newC.initiativeMod = 0;
    	}
    	
    	if(editDefense.getText().toString() != "") {
    		newC.defense = Integer.parseInt(editDefense.getText().toString());
    	} else {
    		Toast.makeText(getBaseContext(), "You must enter a Defense", Toast.LENGTH_LONG).show();
    	}
    	
    	if(editType.isChecked()) {
    		newC.player = 1;
    	} else {
    		newC.player = 0;
    	}
    	
    	DB_Adapter_Activity db = new DB_Adapter_Activity(this);
    	
    	//--add character to database
    	db.open();
    	id = db.insertCharacter(newC);
    	db.close();
    	
		runOnUiThread(returnToInitiative);
	}
}