package com.google.baumeredv.monsterspotting.model.entity;

public final class Encounter {

  private final Source _source;
  private final int _sourcePage;
  private final Lighting _lighting;
  private final boolean _partyCanSurprise;
  private final String _notes;
  private final boolean _partyCanBeSurprised;
  private final boolean _adventureEncouragesSurprise;
  private final int _maximumFlightHeight;
  private final int _roomBoundingBoxWidth;
  private final int _roomBoundingBoxLength;
  private final int _minEncounterStartDistance;
  private final int _maxEncounterStartDistance;
  private final boolean _encounterIsResistantToGroundDefaultKill;


  public Encounter(Source source, int sourcePage, Lighting lighting, boolean partyCanSurprise,
      String notes, boolean partyCanBeSurprised, boolean adventureEncouragesSurprise,
      int maximumFlightHeight, int roomBoundingBoxWidth,
      int roomBoundingBoxLength, int minEncounterStartDistance,
      int maxEncounterStartDistance, boolean encounterIsResistantToGroundDefaultKill) {
    _source = source;
    _sourcePage = sourcePage;
    _lighting = lighting;
    _partyCanSurprise = partyCanSurprise;
    _notes = notes;
    _partyCanBeSurprised = partyCanBeSurprised;
    _adventureEncouragesSurprise = adventureEncouragesSurprise;
    _maximumFlightHeight = maximumFlightHeight;
    _roomBoundingBoxWidth = roomBoundingBoxWidth;
    _roomBoundingBoxLength = roomBoundingBoxLength;
    _minEncounterStartDistance = minEncounterStartDistance;
    _maxEncounterStartDistance = maxEncounterStartDistance;
    _encounterIsResistantToGroundDefaultKill = encounterIsResistantToGroundDefaultKill;
  }

  public Source source() {
    return _source;
  }

  public int sourcePage() {
    return _sourcePage;
  }

  public Lighting lighting() {
    return _lighting;
  }

  public boolean partyCanSurprise() {
    return _partyCanSurprise;
  }

  public String notes() {
    return _notes;
  }

  public boolean partyCanBeSurprised(){
    return _partyCanBeSurprised;
  }

  public boolean adventureEncouragesSurprise(){
    return _adventureEncouragesSurprise;
  }

  public int maximumFlightHeight(){
    return _maximumFlightHeight;
  }

  public int roomBoundingBoxWidth(){
    return _roomBoundingBoxWidth;
  }

  public int roomBoundingBoxLength(){
    return _roomBoundingBoxLength;
  }

  public int minEncounterStartDistance(){
    return _minEncounterStartDistance;
  }

  public int maxEncounterStartDistance(){
    return _maxEncounterStartDistance;
  }

  public boolean encounterIsResistantToGroundDefaultKill(){
    return _encounterIsResistantToGroundDefaultKill;
  }



  public boolean equals(Object object){
    if(object == this){
      return true;
    }
    if(!(object instanceof Encounter)){
      return false;
    }
    Encounter other = (Encounter) object;
    return (_source.equals(other._source)) &&
        (_sourcePage == other._sourcePage) &&
        (_lighting.equals(other._lighting)) &&
        (_partyCanSurprise == other._partyCanSurprise) &&
        (_notes.equals(other._notes)) &&
        (_partyCanBeSurprised == other._partyCanBeSurprised) &&
        (_adventureEncouragesSurprise == other._adventureEncouragesSurprise) &&
        (_maximumFlightHeight == other._maximumFlightHeight) &&
        (_roomBoundingBoxWidth == other._roomBoundingBoxWidth) &&
        (_roomBoundingBoxLength == other._roomBoundingBoxLength) &&
        (_minEncounterStartDistance == other._minEncounterStartDistance) &&
        (_maxEncounterStartDistance == other._maxEncounterStartDistance) &&
        (_encounterIsResistantToGroundDefaultKill == other._encounterIsResistantToGroundDefaultKill);
  }
}
