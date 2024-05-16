package com.tokioFilme.domain;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum FilmSearchCriteriaEnum {
  TITLE("Title"),
  YEAR("Year"),
  MAX_DURATION("Max Length"),
  AVG_SCORE("Average Score"),
  ACTOR("Actor"),
  DIRECTOR("Director"),
  SCREENWRITER("Screenwriter"),
  CINEMATOGRAPHER("Cinematographer"),
  COMPOSER("Composer");


  private final String displayValue;

  FilmSearchCriteriaEnum(String displayValue) {
    this.displayValue = displayValue;
  }

  public String getDisplayValue() {
    return displayValue;
  }

  public static FilmSearchCriteriaEnum fromString(String value) {
    for (FilmSearchCriteriaEnum criteria : FilmSearchCriteriaEnum.values()) {
      if (criteria.getDisplayValue().equalsIgnoreCase(value)) {
        return criteria;
      }
      else {
        try {
          if (FilmSearchCriteriaEnum.valueOf(value) == criteria) {
            return criteria;
          }
        }
        catch (Exception e) {
          log.error("Error when fetching FilmSearchCriteriaEnum", e);
        }
      }
    }
    throw new IllegalArgumentException("No matching search criteria found");
  }

}
