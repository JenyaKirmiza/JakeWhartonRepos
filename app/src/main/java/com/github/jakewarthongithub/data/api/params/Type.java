package com.github.jakewarthongithub.data.api.params;

public enum Type {
  ALL("all"),
  OWNER("owner"),
  MEMBER("member");

  private final String value;

  Type(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return value;
  }
}
