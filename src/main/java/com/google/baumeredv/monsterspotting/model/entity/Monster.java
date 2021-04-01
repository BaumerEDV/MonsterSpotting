package com.google.baumeredv.monsterspotting.model.entity;

public final class Monster {

  private final String _name;
  private final Source _source;
  private final int _sourcePage;


  public Monster(String name, Source source, int sourcePage) {
    _name = name;
    _source = source;
    _sourcePage = sourcePage;
  }

  public String name(){
    return _name;
  }

  public Source source(){
    return _source;
  }

  public int sourcePage(){
    return _sourcePage;
  }

  public boolean equals(Object object){
    if (!(object instanceof Monster)){
      return false;
    }

    Monster other = (Monster) object;
    return (this.name().equals(other.name())) &&
        (this.source().equals(other.source())) &&
        (this.sourcePage() == other.sourcePage());
  }
}
