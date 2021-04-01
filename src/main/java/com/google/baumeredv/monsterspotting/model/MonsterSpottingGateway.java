package com.google.baumeredv.monsterspotting.model;

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
}
