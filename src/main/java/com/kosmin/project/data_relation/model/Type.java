package com.kosmin.project.data_relation.model;

import lombok.Getter;

@Getter
public enum Type {
  CHECKING("Checking"),
  CREDIT("Credit");

  private final String value;

  Type(String value) {
    this.value = value;
  }
}
