package com.github.jakewarthongithub.data.api.params;

public enum Sort {
  CREATED("created"),
  UPDATED("updated"),
  PUSHED("pushed"),
  FULLNAME("full_name");

  private final String value;

  Sort(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return value;
  }
}
