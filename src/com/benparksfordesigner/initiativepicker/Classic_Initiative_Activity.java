package com.benparksfordesigner.initiativepicker;

import java.util.ArrayList;
import java.util.Collections;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TextView;
import android.widget.Toast;


public class Classic_Initiative_Activity extends ListActivity {
	
	private ArrayList<Character> characterNames = null;
	private CharacterAdapter characterNamesArrAd;
	int request_Code = 1;
	DB_Adapter_Activity db = new DB_Adapter_Activity(this);
	private Runnable buildCharacterList;
	private ProgressDialog characterProgressDialog = null;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.classic_initiative);
		
		characterNames = new ArrayList<Character>();
		this.characterNamesArrAd = new CharacterAdapter(this, R.layout.character_list_row, characterNames);
		setListAdapter(this.characterNamesArrAd);
		
		Log.i("ArrayAr", Integer.toString(characterNamesArrAd.getCount()));
		
		buildCharacterList = new Runnable() {
			@Override
			public void run() {
				getCharacters();
			}
		};
		
		Thread thread = new Thread(null, buildCharacterList, "MagnetoBackground");
		thread.start();
		
		characterProgressDialog = ProgressDialog.show(Classic_Initiative_Activity.this, 
				getString(R.string.message_loading_characters), 
				getString(R.string.message_please_wait), true);
	}
	
	private Runnable returnCharacters = new Runnable() {
		@Override
		public void run() {
			characterProgressDialog.dismiss();
			characterNamesArrAd.notifyDataSetChanged();
		}
	};
	
	private void getCharacters() {
		
		try {
			db.open();
			Cursor c = db.getAllCharacters();
			
			if(c.moveToFirst()) {
				do {
					buildCharacterList(c);
				} while (c.moveToNext());
			}
			db.close();
		} catch (Exception e) {
			Log.e("Error", "Failed to retrieve characters");
		}
		orderNameList(characterNames);
	}
	
	private void buildCharacterList(Cursor c) {
		
		Character aC = new Character();
		
		aC.characterId = c.getInt(0);
		aC.characterName = c.getString(1);
		//aC.playerName = c.getString(x);
		aC.initiative = c.getInt(2);
		aC.initiativeMod = c.getInt(3);
		aC.defense = c.getInt(4);
		aC.player = c.getInt(5);
		
		characterNames.add(aC);
	}
	
	private void orderNameList (ArrayList<Character> characterNames) {
		//order the names based on initiative
		//go to buildNameList()
		try{
			Collections.sort(characterNames);
		} catch (Exception e) {
			Log.e("Error", "Unable to sort characters");
		}
		//buildNameList(characterNames);
		runOnUiThread(returnCharacters);
	}
	
	private void moveToBottom(int position) {
		Character item = characterNames.get(position);
		characterNames.remove(item);
		characterNames.add(item);
		characterNamesArrAd.notifyDataSetChanged();
	}
	
	private void deleteCharacter(int position) {
		Character item = characterNames.get(position);
		int positionId = item.getCharacterId();
		try {
			db.open();	
			db.deleteCharacter(positionId);
			db.close();
		} catch (Exception e) {
			Log.e("Error", "Failed to delete character");
		}
		
		characterNames.remove(item);
		characterNamesArrAd.notifyDataSetChanged();
		Toast.makeText(this, R.string.message_character_deleted, Toast.LENGTH_SHORT).show();
	}

	private void editCharacter(int position) {
		Character item = characterNames.get(position);
		Intent editC = new Intent(this, Character_Input_Activity.class);
		Bundle character = editC.getExtras();
		
		character.putInt("C_ID", item.getCharacterId());
		
		
	}
	
	//Start the intent to add a new character to the list
	public void addCharacter(View view) {
		startActivityForResult(new Intent(this, Character_Input_Activity.class), 
				request_Code);
	}
	
	private class CharacterAdapter extends ArrayAdapter<Character> {
		
		private ArrayList<Character> characters;
		
		public CharacterAdapter(Context context, int textViewResourceId, 
				ArrayList<Character> characters) {
			super(context, textViewResourceId, characters);
			this.characters = characters;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			final int viewPosition = position;
			
			if(v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.character_list_row, null);
			}
			
			Character c = characters.get(position);
			if(c != null) {
				TextView cN = (TextView) v.findViewById(R.id.list_characterName);
				TextView iV = (TextView) v.findViewById(R.id.list_iniativeValue);
				TextView dV = (TextView) v.findViewById(R.id.list_defenseValue);
				
				if(cN != null) cN.setText(c.characterName);
				if(iV != null) iV.setText(getResources().getString(R.string.label_character_initiative) + ": " + c.getInitiative());
				if(dV != null) dV.setText(getResources().getString(R.string.label_character_defense) + ": " + c.getDefense());
			}
			
			v.setOnLongClickListener(new OnLongClickListener() {
				
				@Override
				public boolean onLongClick(View v) {
					
					showCharacterActionMenu(viewPosition, v);
					
					return true;
				}

				private void showCharacterActionMenu(int viewPosition, View v) {
					
					final int position = viewPosition;
					
					PopupMenu selectAction = new PopupMenu(getBaseContext(), v);
					//selectAction.getMenu().add(0, 0, 0, R.string.menu_edit_character);
					selectAction.getMenu().add(0, 1, 0, R.string.menu_delete_character);
					
					selectAction.show();
					
					selectAction.setOnMenuItemClickListener(new OnMenuItemClickListener() {
						
						@Override
						public boolean onMenuItemClick(MenuItem item) {
							
							switch(item.getItemId()) {
							//case 0:
							//editCharacter(position);
							//break;
							
							case 1:
								deleteCharacter(position);
								break;
								
							}
							
							return true;
						}
					});
				}
			});
			
			v.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					moveToBottom(viewPosition);
				}
				
			});
			
			return v;
		}
	}
}
