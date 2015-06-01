package com.github.jakewarthongithub.ui.adapters;

public enum SpinnerEnum {
  CREATED("created"),
  UPDATE("updated"),
  STAR("stars"),
  FORK("forks");


  private final String name;


  SpinnerEnum(String name) {
    this.name = name;
  }


  @Override
  public String toString() {
    return name;
  }
}
