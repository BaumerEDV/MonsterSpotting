package com.google.baumeredv.monsterspotting.model;

import com.google.baumeredv.monsterspotting.model.entity.Source;
import java.util.ArrayList;
import org.springframework.stereotype.Component;

@Component("MonsterSpottingInMemoryGateway")
public class MonsterSpottingInMemoryGateway implements MonsterSpottingGateway{

  private ArrayList<Source> sources;

  public MonsterSpottingInMemoryGateway(){
    sources = new ArrayList<>();
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
}
