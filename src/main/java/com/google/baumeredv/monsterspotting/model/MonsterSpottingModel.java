package com.google.baumeredv.monsterspotting.model;

import com.google.baumeredv.monsterspotting.model.entity.Lighting;
import com.google.baumeredv.monsterspotting.model.entity.Monster;
import com.google.baumeredv.monsterspotting.model.entity.Source;
import com.google.baumeredv.monsterspotting.model.exceptions.DuplicateLightingException;
import com.google.baumeredv.monsterspotting.model.exceptions.DuplicateMonsterException;
import com.google.baumeredv.monsterspotting.model.exceptions.DuplicateSourceException;
import com.google.baumeredv.monsterspotting.model.exceptions.ThereIsNoSuchLightingException;
import com.google.baumeredv.monsterspotting.model.exceptions.ThereIsNoSuchSourceException;
import java.util.ArrayList;
import net.bytebuddy.pool.TypePool.Resolution.Illegal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class MonsterSpottingModel {

  private MonsterSpottingGateway gateway;

  @Autowired
  public MonsterSpottingModel(
      @Qualifier("MonsterSpottingInMemoryGateway") MonsterSpottingGateway gateway){
    this.gateway = gateway;
  }


  public Source addSource(Source source) throws DuplicateSourceException {
    if(source == null){
      throw new IllegalArgumentException("Source must not be null");
    }
    if(source.name() == null || source.name().equals("")){
      throw new IllegalArgumentException("Name of a source cannot be empty");
    }
    if(gateway.containsSource(source)){
      throw new DuplicateSourceException();
    }
    return gateway.addSource(source);
  }

  public Iterable<Source> allSources() {
    return gateway.allSources();
  }

  public void deleteSource(Source source) throws ThereIsNoSuchSourceException {
    if(source == null){
      throw new IllegalArgumentException("Cannot delete a source that is null");
    }
    if(gateway.containsSource(source) == false){
      throw new ThereIsNoSuchSourceException();
    }
    gateway.deleteSource(source);
  }

  public Lighting addLighting(Lighting lighting) throws DuplicateLightingException {
    if(lighting == null){
      throw new IllegalArgumentException("Lighting must not be null");
    }
    if(lighting.description() == null || lighting.description().equals("")){
      throw new IllegalArgumentException("lighting description cannot be empty");
    }
    if(gateway.containsLighting(lighting)){
      throw new DuplicateLightingException();
    }
    return gateway.addLighting(lighting);
  }

  public Iterable<Lighting> allLightings() {
    return gateway.allLightings();
  }

  public void deleteLighting(Lighting lighting) throws ThereIsNoSuchLightingException {
    if(lighting == null){
      throw new IllegalArgumentException("Lighting must not be null");
    }
    if(gateway.containsLighting(lighting) == false){
      throw new ThereIsNoSuchLightingException();
    }
    gateway.deleteLighting(lighting);
  }

  public Monster addMonster(Monster monster) throws DuplicateMonsterException {
    if (monster == null){
      throw new IllegalArgumentException("Added monster must not be null");
    }
    if (monster.source() == null){
      throw new IllegalArgumentException("Added monster source must not be null");
    }
    if (monster.name() == null || monster.name().equals("")){
      throw new IllegalArgumentException("Monster name must not be empty");
    }
    if(monster.source().name() == null || monster.source().name().equals("")){
      throw new IllegalArgumentException("Monster source name must not be empty");
    }
    if(monster.sourcePage() <= 0){
      throw new IllegalArgumentException("Monster source page must not be zero or negative");
    }
    if(gateway.containsMonster(monster)){
      throw new DuplicateMonsterException();
    }
    return gateway.addMonster(monster);
  }

  public Iterable<Monster> allMonsters() {
    return gateway.allMonsters();
  }
}
