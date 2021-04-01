package com.google.baumeredv.monsterspotting.model.entity;

public final class Source {

  private final String _name;

  public Source(String name){
    _name = name;
  }

  public String name(){
    return _name;
  }

  public boolean equals(Object object){
    if (!(object instanceof Source)){
      return false;
    }

    Source other = (Source) object;
    return this._name.equals(other._name);
  }
}
