package com.tokioFilme.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity(name = "reviews")
public class Review {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;
  @Column
  private String title;
  @Column(name = "text_review", columnDefinition = "TEXT")
  private String textReview;
  @Column(columnDefinition = "TIMESTAMP")
  private LocalDate date;

  @ManyToOne()
  @JoinColumn(name = "film_id", referencedColumnName = "id")
  private Film film;
  @ManyToOne()
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Review review = (Review) o;

    if (title != null ? !title.equals(review.title) : review.title != null) return false;
    if (textReview != null ? !textReview.equals(review.textReview) : review.textReview != null) return false;
    if (date != null ? !date.equals(review.date) : review.date != null) return false;
    if (film != null ? !film.getTitle().equals(review.film.getTitle()) : review.film != null) return false;
    return user != null ? user.getUsername().equals(review.user.getUsername()) : review.user == null;
  }

  @Override
  public int hashCode() {
    return this.getClass().hashCode();
  }

  @Override
  public String toString() {
    return film.getTitle() + ":" + title + ":" + date;
  }
}
