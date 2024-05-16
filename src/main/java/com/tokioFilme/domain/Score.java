package com.tokioFilme.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@Entity(name = "scores")
public class Score {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;
  @Column(nullable = false)
  @Min(value = 0)
  @Max(value = 5)
  private int value = 0;

  @ManyToOne()
  @JoinColumn(name = "film_id")
  private Film film;
  @ManyToOne()
  @JoinColumn(name = "user_id")
  private User user;


  public Score() {}

  public Score(int value) {
    this.value = value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Score score = (Score) o;

    if (value != score.value) return false;
    if (film != null ? !film.equals(score.film) : score.film != null) return false;
    return user != null ? user.equals(score.user) : score.user == null;
  }

  @Override
  public int hashCode() {
    return this.getClass().hashCode();
  }
}
