package com.google.baumeredv.monsterspotting.model;

import com.google.baumeredv.monsterspotting.model.entity.Source;
import com.google.baumeredv.monsterspotting.model.exceptions.DuplicateSourceException;
import com.google.baumeredv.monsterspotting.model.exceptions.ThereIsNoSuchSourceException;
import java.util.ArrayList;
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

  public Iterable<Source> AllSources() {
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
}
