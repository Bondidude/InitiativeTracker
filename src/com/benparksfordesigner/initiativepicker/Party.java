package com.benparksfordesigner.initiativepicker;

public class Party {
	int partyId;
	String partyName;
	int encounterId;
	Boolean encounterParty;
	
	public Party() {
		partyId = 0;
		partyName = "";
		encounterId = 0;
		encounterParty = false;
	}
	
	public int getPartyId() {
		return partyId;
	}
	
	public void setPartyId(Party p) {
		p.partyId = this.partyId;
	}
	
	public String getPartyName() {
		return partyName;
	}
	
	public void setPartyName(Party p) {
		p.partyName = this.partyName;
	}
	
	public int getPartyEncounterId() {
		return encounterId;
	}
	
	public void setPartyEncounterId(Party p) {
		p.encounterId = this.encounterId;
	}
	
	public Boolean getEncounterParty() {
		return encounterParty;
	}
	
	public void setEncounterParty(Party p) {
		p.encounterParty = this.encounterParty;
	}
	
	public class Party_Member {
		
		int partyMemberId;
		int characterId;
		int partyId;
		
		public Party_Member() {
			partyMemberId = 0;
			characterId = 0;
			partyId = 0;
		}
		
		public int getPartyMemberId() {
			return partyMemberId;
		}
		
		public void setPartyMemberId(Party_Member pm) {
			pm.partyMemberId = this.partyMemberId;
		}
		
		public int getPartyMemberCharacterId() {
			return characterId;
		}
		
		public void setPartyMemberCharacterId(Party_Member pm) {
			pm.characterId = this.characterId;
		}
		
		public int getPartyMemberPartyId() {
			return partyId;
		}
		
		public void setPartyMemberPartyId(Party_Member pm) {
			pm.partyId = partyId;
		}
	}
}
