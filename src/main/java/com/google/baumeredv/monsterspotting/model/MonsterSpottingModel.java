package com.google.baumeredv.monsterspotting.model;

import com.google.baumeredv.monsterspotting.model.entity.Source;
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


  public Source addSource(Source source) {
    if(source == null){
      throw new IllegalArgumentException("Source must not be null");
    }
    if(source.name() == null || source.name().equals("")){
      throw new IllegalArgumentException("Name of a source cannot be empty");
    }
    return gateway.addSource(source);
  }

  public Iterable<Source> AllSources() {
    return gateway.allSources();
  }
}
