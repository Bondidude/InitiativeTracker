package com.benparksfordesigner.initiativepicker;

public class Character implements Comparable<Character> {
	int characterId;
	String characterName;
	String playerName;
	int initiative;
	int initiativeMod;
	int defense;
	int player;
	
	public Character() {
		characterId = 0;
		characterName = "";
		playerName = "";
		initiative = 0;
		initiativeMod = 0;
		defense = 0;
		player = 1;
	}
	
	public int getCharacterId() {
		return characterId;
	}
	
	public void setCharacterId(int characterId) {
		this.characterId = characterId;
	}
	
	public String getCharacterName() {
		return characterName;
	}
	
	public void setCharacterName(String characterName) {
		this.characterName = characterName;
	}
	
	public String getPlayerName() {
		return playerName;
	}
	
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	
	public int getInitiative() {
		return initiative;
	}
	
	public void setInitiative(int initiative) {
		this.initiative = initiative;
	}
	
	public int getInitiativeMod() {
		return initiativeMod;
	}
	
	public void setInitiativeMod(int initiativeMod) {
		this.initiativeMod = initiativeMod;
	}
	
	public int getDefense() {
		return defense;
	}
	
	public void setDefense(int defense) {
		this.defense = defense;
	}
	
	public int getPlayer() {
		return player;
	}
	
	public void setPlayer(int player) {
		this.player = player;
	}

	@Override
	public int compareTo(Character o) {
		if(this.initiative > o.initiative) {
			return -1;
		} else if (this.initiative < o.initiative) {
			return 1;
		} else if (this.initiative == o.initiative) {
			if(this.initiativeMod > o.initiativeMod) {
				return -1;
			} else if (this.initiativeMod < o.initiativeMod) {
				return 1;
			} else {
				return -1;  //TODO: Pop up dialog that asks which character they want to put on top
			}
		}
		
		return 0;
	}
}
