package com.google.baumeredv.monsterspotting.model.entity;

public final class EncounterInCampaign {

  private final Encounter _encounter;
  private final float _evToHappenAtThisLevel;
  private final int _partyLevel;


  public EncounterInCampaign(Encounter encounter, float evToHappenAtThisLevel, int partyLevel) {
    _encounter = encounter;
    _evToHappenAtThisLevel = evToHappenAtThisLevel;
    _partyLevel = partyLevel;
  }



  public boolean equals(Object object){
    if(this == object){
      return true;
    }
    if(object == null){
      return false;
    }
    if(!(object instanceof EncounterInCampaign)){
      return false;
    }
    EncounterInCampaign other = (EncounterInCampaign) object;
    return _encounter.equals(other._encounter) &&
        (_partyLevel == other._partyLevel) &&
        (Math.abs(_evToHappenAtThisLevel - other._evToHappenAtThisLevel) < 0.000001f);
  }
}
