package com.google.baumeredv.monsterspotting.model.entity;

public class Lighting {

  private String _description;

  public Lighting(String description) {
    _description = description;
  }

  public String description(){
    return _description;
  }

  public boolean equals(Object object){
    if (!(object instanceof Lighting)){
      return false;
    }

    Lighting other = (Lighting) object;
    return this._description.equals(other._description);
  }
}
