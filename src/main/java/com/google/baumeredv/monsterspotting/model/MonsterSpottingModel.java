package com.google.baumeredv.monsterspotting.model;

import com.google.baumeredv.monsterspotting.model.entity.Encounter;
import com.google.baumeredv.monsterspotting.model.entity.Lighting;
import com.google.baumeredv.monsterspotting.model.entity.Monster;
import com.google.baumeredv.monsterspotting.model.entity.Source;
import com.google.baumeredv.monsterspotting.model.exceptions.DuplicateEncounterException;
import com.google.baumeredv.monsterspotting.model.exceptions.DuplicateLightingException;
import com.google.baumeredv.monsterspotting.model.exceptions.DuplicateMonsterException;
import com.google.baumeredv.monsterspotting.model.exceptions.DuplicateSourceException;
import com.google.baumeredv.monsterspotting.model.exceptions.ThereIsNoSuchEncounterException;
import com.google.baumeredv.monsterspotting.model.exceptions.ThereIsNoSuchLightingException;
import com.google.baumeredv.monsterspotting.model.exceptions.ThereIsNoSuchMonsterException;
import com.google.baumeredv.monsterspotting.model.exceptions.ThereIsNoSuchSourceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class MonsterSpottingModel {

  private MonsterSpottingGateway gateway;

  @Autowired
  public MonsterSpottingModel(
      @Qualifier("MonsterSpottingInMemoryGateway") MonsterSpottingGateway gateway) {
    this.gateway = gateway;
  }


  public Source addSource(Source source) throws DuplicateSourceException {
    if (source == null) {
      throw new IllegalArgumentException("Source must not be null");
    }
    if (source.name() == null || source.name().equals("")) {
      throw new IllegalArgumentException("Name of a source cannot be empty");
    }
    if (gateway.containsSource(source)) {
      throw new DuplicateSourceException();
    }
    return gateway.addSource(source);
  }

  public Iterable<Source> allSources() {
    return gateway.allSources();
  }

  public void deleteSource(Source source) throws ThereIsNoSuchSourceException {
    if (source == null) {
      throw new IllegalArgumentException("Cannot delete a source that is null");
    }
    if (gateway.containsSource(source) == false) {
      throw new ThereIsNoSuchSourceException();
    }
    gateway.deleteSource(source);
  }

  public Lighting addLighting(Lighting lighting) throws DuplicateLightingException {
    if (lighting == null) {
      throw new IllegalArgumentException("Lighting must not be null");
    }
    if (lighting.description() == null || lighting.description().equals("")) {
      throw new IllegalArgumentException("lighting description cannot be empty");
    }
    if (gateway.containsLighting(lighting)) {
      throw new DuplicateLightingException();
    }
    return gateway.addLighting(lighting);
  }

  public Iterable<Lighting> allLightings() {
    return gateway.allLightings();
  }

  public void deleteLighting(Lighting lighting) throws ThereIsNoSuchLightingException {
    if (lighting == null) {
      throw new IllegalArgumentException("Lighting must not be null");
    }
    if (gateway.containsLighting(lighting) == false) {
      throw new ThereIsNoSuchLightingException();
    }
    gateway.deleteLighting(lighting);
  }

  public Monster addMonster(Monster monster) throws DuplicateMonsterException {
    if (monster == null) {
      throw new IllegalArgumentException("Added monster must not be null");
    }
    if (monster.source() == null) {
      throw new IllegalArgumentException("Added monster source must not be null");
    }
    if (monster.name() == null || monster.name().equals("")) {
      throw new IllegalArgumentException("Monster name must not be empty");
    }
    if (monster.source().name() == null || monster.source().name().equals("")) {
      throw new IllegalArgumentException("Monster source name must not be empty");
    }
    if (monster.sourcePage() <= 0) {
      throw new IllegalArgumentException("Monster source page must not be zero or negative");
    }
    if (gateway.containsMonster(monster)) {
      throw new DuplicateMonsterException();
    }
    return gateway.addMonster(monster);
  }

  public Iterable<Monster> allMonsters() {
    return gateway.allMonsters();
  }

  public Encounter addEncounter(Encounter encounter) throws DuplicateEncounterException {
    if (encounter.source() == null) {
      throw new IllegalArgumentException("Encounter source must not be null");
    }
    if (encounter.notes() == null) {
      throw new IllegalArgumentException("Encounter notes must not be null");
    }
    if (encounter.lighting() == null) {
      throw new IllegalArgumentException("Encounter lighting must not be null");
    }
    if (encounter.source().name() == null || encounter.source().name().equals("")) {
      throw new IllegalArgumentException("Encounter source name must not be empty");
    }
    if ((encounter.roomBoundingBoxLength() < 0 && encounter.roomBoundingBoxLength() != -1) ||
        (encounter.roomBoundingBoxWidth() < 0 && encounter.roomBoundingBoxWidth() != -1)) {
      throw new IllegalArgumentException(
          "Encounter bounding box dimensions must not have a noncoded"
              + " negative value");
    }
    if ((encounter.minEncounterStartDistance() < 0 && encounter.minEncounterStartDistance() != -1)
        ||
        (encounter.maxEncounterStartDistance() < 0
            && encounter.maxEncounterStartDistance() != -1)) {
      throw new IllegalArgumentException("Encounter starting distance must not have a noncoded "
          + "negative value");
    }
    if (encounter.maximumFlightHeight() < 0 && encounter.maximumFlightHeight() != -1) {
      throw new IllegalArgumentException("Encounter maximum flight height must not have a noncoded"
          + " negative value");
    }
    if ((encounter.minEncounterStartDistance() > encounter.maxEncounterStartDistance()) &&
        (encounter.minEncounterStartDistance() != -1) &&
        (encounter.maxEncounterStartDistance() != -1)) {
      throw new IllegalArgumentException(
          "Encounter maximum start distance must not be smaller than "
              + "minimum start distance");
    }
    if (encounter.sourcePage() < 0) {
      throw new IllegalArgumentException("Encounter source page must not be negative");
    }
    if (encounter.lighting().description() == null || encounter.lighting().description()
        .equals("")) {
      throw new IllegalArgumentException("Encounter lightning description must not be empty");
    }
    if (encounter.roomBoundingBoxWidth() == 0 || encounter.roomBoundingBoxLength() == 0) {
      throw new IllegalArgumentException("Encounter bounding box dimensions must not be zero");
    }
    if (gateway.containsEncounter(encounter)) {
      throw new DuplicateEncounterException();
    }
    return gateway.addEncounter(encounter);
  }

  public Iterable<Encounter> allEncounters() {
    return gateway.allEncounters();
  }

  public void deleteEncounter(Encounter encounter) throws ThereIsNoSuchEncounterException {
    if (encounter == null) {
      throw new IllegalArgumentException("Encounter to be deleted must not be null");
    }
    if (gateway.containsEncounter(encounter) == false){
      throw new ThereIsNoSuchEncounterException();
    }
    gateway.deleteEncounter(encounter);
  }

  public void deleteMonster(Monster monster) throws ThereIsNoSuchMonsterException {
    if(monster == null){
      throw new IllegalArgumentException("Monster to be deleted must not be null");
    }
    if(gateway.containsMonster(monster) == false){
      throw new ThereIsNoSuchMonsterException();
    }
    gateway.deleteMonster(monster);
  }
}
