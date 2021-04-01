package com.google.baumeredv.monsterspotting.model;

import com.google.baumeredv.monsterspotting.model.entity.Source;
import org.springframework.stereotype.Component;

@Component
public interface MonsterSpottingGateway {

  Iterable<Source> allSources();

  Source addSource(Source source);
}
