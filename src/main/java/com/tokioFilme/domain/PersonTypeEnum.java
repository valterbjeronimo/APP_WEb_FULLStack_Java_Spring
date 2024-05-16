package com.tokioFilme.domain;


public enum PersonTypeEnum {
  SCREEN_WRITER("Screen Writer"),
  COMPOSER("Composer"),
  CINEMATOGRAPHER("Cinematographer"),
  ACTOR("Actor"),
  DIRECTOR("Director");

  private final String displayValue;

  PersonTypeEnum(String displayValue) {
    this.displayValue = displayValue;
  }

  public String getDisplayValue() {
    return displayValue;
  }
}
