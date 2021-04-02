package com.google.baumeredv.monsterspotting.model;

import com.google.baumeredv.monsterspotting.model.entity.Encounter;
import com.google.baumeredv.monsterspotting.model.entity.EncounterInCampaign;
import com.google.baumeredv.monsterspotting.model.entity.Lighting;
import com.google.baumeredv.monsterspotting.model.entity.Monster;
import com.google.baumeredv.monsterspotting.model.entity.Source;
import org.springframework.stereotype.Component;

@Component
public interface MonsterSpottingGateway {

  Iterable<Source> allSources();

  Source addSource(Source source);

  void deleteSource(Source source);

  boolean containsSource(Source sourceInQuestion);

  Iterable<Lighting> allLightings();

  Lighting addLighting(Lighting lighting);

  boolean containsLighting(Lighting lightingInQuestion);

  void deleteLighting(Lighting lighting);

  Monster addMonster(Monster monster);

  Iterable<Monster> allMonsters();

  boolean containsMonster(Monster monsterInQuestion);

  Iterable<Encounter> allEncounters();

  Encounter addEncounter(Encounter encounter);

  boolean containsEncounter(Encounter encounterInQuestion);

  void deleteEncounter(Encounter encounter);

  void deleteMonster(Monster monster);

  Iterable<EncounterInCampaign> allEncounterInCampaigns();

  EncounterInCampaign addEncounterInCampaign(EncounterInCampaign encounterInCampaign);

  boolean containsEncounterAtLevel(Encounter encounter, int partyLevel);

  boolean containsEncounterInCampaign(EncounterInCampaign encounterInCampaign);

  void deleteEncounterInCampaign(EncounterInCampaign encounterInCampaign);
}
