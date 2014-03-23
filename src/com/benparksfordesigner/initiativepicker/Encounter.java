package com.benparksfordesigner.initiativepicker;

public class Encounter {
	int encounterId;
	String encounterName;
	int partyId;
	
	public Encounter() {
		encounterId = 0;
		encounterName = "";
		partyId = 0;
	}
	
	public int getEncounterId() {
		return encounterId;
	}
	
	public void setEncounterId(int encounterId) {
		this.encounterId = encounterId;
	}
	
	public String getEncounterName() {
		return encounterName;
	}
	
	public void setEncounterName(String encounterName) {
		this.encounterName = encounterName;
	}
	
	public int getPartyId() {
		return partyId;
	}
	
	public void setPartyId(int partyId) {
		this.partyId = partyId;
	}
}
