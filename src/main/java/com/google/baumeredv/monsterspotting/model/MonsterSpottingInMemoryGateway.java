package com.google.baumeredv.monsterspotting.model;

import com.google.baumeredv.monsterspotting.model.entity.Encounter;
import com.google.baumeredv.monsterspotting.model.entity.Lighting;
import com.google.baumeredv.monsterspotting.model.entity.Monster;
import com.google.baumeredv.monsterspotting.model.entity.Source;
import java.util.ArrayList;
import org.springframework.stereotype.Component;

@Component("MonsterSpottingInMemoryGateway")
public class MonsterSpottingInMemoryGateway implements MonsterSpottingGateway{

  private ArrayList<Source> sources;
  private ArrayList<Lighting> lightings;
  private ArrayList<Monster> monsters;
  private ArrayList<Encounter> encounters;

  public MonsterSpottingInMemoryGateway(){
    sources = new ArrayList<>();
    lightings = new ArrayList<>();
    monsters = new ArrayList<>();
    encounters = new ArrayList<>();
  }

  @Override
  public Iterable<Source> allSources() {
    ArrayList<Source> result = new ArrayList<>();
    result.addAll(sources);
    return result;
  }

  @Override
  public Source addSource(Source source) {
    sources.add(source);
    return source;
  }

  @Override
  public void deleteSource(Source source) {
    sources.remove(source);
  }

  @Override
  public boolean containsSource(Source sourceInQuestion) {
    for (Source source : sources){
      if (source.equals(sourceInQuestion)){
        return true;
      }
    }
    return false;
  }

  @Override
  public Iterable<Lighting> allLightings() {
    ArrayList<Lighting> result = new ArrayList<>();
    result.addAll(lightings);
    return result;
  }

  @Override
  public Lighting addLighting(Lighting lighting) {
    lightings.add(lighting);
    return lighting;
  }

  @Override
  public boolean containsLighting(Lighting lightingInQuestion) {
    for(Lighting lighting : lightings){
      if(lighting.equals(lightingInQuestion)){
        return true;
      }
    }
    return false;
  }

  @Override
  public void deleteLighting(Lighting lighting) {
    lightings.remove(lighting);
  }

  @Override
  public Monster addMonster(Monster monster) {
    monsters.add(monster);
    return monster;
  }

  @Override
  public Iterable<Monster> allMonsters() {
    ArrayList<Monster> result = new ArrayList<>();
    result.addAll(monsters);
    return result;
  }

  @Override
  public boolean containsMonster(Monster monsterInQuestion) {
    for (Monster monster : monsters){
      if(monster.equals(monsterInQuestion)){
        return true;
      }
    }
    return false;
  }

  @Override
  public Iterable<Encounter> allEncounters() {
    ArrayList<Encounter> result = new ArrayList<>();
    result.addAll(encounters);
    return result;
  }

  @Override
  public Encounter addEncounter(Encounter encounter) {
    encounters.add(encounter);
    return encounter;
  }

  @Override
  public boolean containsEncounter(Encounter encounterInQuestion) {
    for(Encounter encounter : encounters){
      if(encounter.equals(encounterInQuestion)){
        return true;
      }
    }
    return false;
  }

  @Override
  public void deleteEncounter(Encounter encounter) {
    encounters.remove(encounter);
  }

  @Override
  public void deleteMonster(Monster monster) {
    monsters.remove(monster);
  }
}
