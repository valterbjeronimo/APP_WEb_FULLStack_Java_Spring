package com.tokioFilme.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

import static javax.persistence.CascadeType.*;

@Data
@Entity(name = "films")
@Table(indexes = {
  @Index(name = "title_index", columnList = "title"),
  @Index(name = "year_index", columnList = "year"),
  @Index(name = "uri_index", columnList = "uri")})
public class Film implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;
  @Column(nullable = false)
  private String title;
  @Column
  private String uri;
  @Column(nullable = false)
  private int year;
  @Column
  private int duration;
  @Column
  private String synopsis;
  @Column
  private String poster;
  @Column
  private Boolean migrate;
  @Column(name = "date_migrate", columnDefinition = "TIMESTAMP")
  private LocalDate dateMigrate;
  @Column(name = "avg_score")
  private int avgScore;

  @ManyToOne(cascade = {MERGE})
  @JoinColumn(name = "user_id")
  private User user;
  @OneToMany(mappedBy = "film", cascade = {REMOVE, MERGE}, fetch = FetchType.EAGER, orphanRemoval = true)
  private List<Score> scores = new ArrayList<>();
  @OneToMany(mappedBy = "film", cascade = {REMOVE, MERGE}, fetch = FetchType.EAGER, orphanRemoval = true)
  private Set<Review> reviews = new HashSet<>();
  @ManyToOne
  @JoinColumn(name = "director_id")
  private Person filmDirector;
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "film_actors", joinColumns = @JoinColumn(name = "film_id"),
    inverseJoinColumns = @JoinColumn(name = "actor_id"))
  private Set<Person> filmActors = new HashSet<>();
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "film_composers", joinColumns = @JoinColumn(name = "film_id"),
    inverseJoinColumns = @JoinColumn(name = "musician_id"))
  private Set<Person> filmComposers = new HashSet<>();
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "film_screenwriters", joinColumns = @JoinColumn(name = "film_id"),
    inverseJoinColumns = @JoinColumn(name = "screenwriter_id"))
  private Set<Person> filmScreenwriters = new HashSet<>();
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "film_cinematographers", joinColumns = @JoinColumn(name = "film_id"),
    inverseJoinColumns = @JoinColumn(name = "photographer_id"))
  private Set<Person> filmCinematographers = new HashSet<>();

  public void calculateAverageScore() {
    this.avgScore = scores.stream().map(Score::getValue).reduce(0, Integer::sum) / scores.size();
  }

  public void addScore(Score score) {
    score.setFilm(this);
    scores.add(score);
    calculateAverageScore();
  }

  public void removeScore(Score score) {
    scores.remove(score);
    calculateAverageScore();
  }

  public void addReview(Review review) {
    reviews.add(review);
  }

  public boolean removeReview(Review review) {
    return reviews.remove(review);
  }

  public void addActor(Person actor) {
    if (actor.getType() == PersonTypeEnum.ACTOR) {
      filmActors.add(actor);
    }
  }

  public boolean removeActor(Person actor) {
    return filmActors.remove(actor);
  }

  public void addMusician(Person musician) {
    if (musician.getType() == PersonTypeEnum.COMPOSER) {
      filmComposers.add(musician);
    }
  }

  public boolean removeMusician(Person musician) {
    return filmComposers.remove(musician);
  }

  public void addScreenwriter(Person screenwriter) {
    if (screenwriter.getType() == PersonTypeEnum.SCREEN_WRITER) {
      filmScreenwriters.add(screenwriter);
    }
  }

  public boolean removeScreenwriter(Person screenwriter) {
    return filmScreenwriters.remove(screenwriter);
  }

  public void addPhotographer(Person photographer) {
    if (photographer.getType() == PersonTypeEnum.CINEMATOGRAPHER) {
      filmCinematographers.add(photographer);
    }
  }

  public boolean removePhotographer(Person photographer) {
    return filmCinematographers.remove(photographer);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Film film = (Film) o;

     if (year != film.year) return false;
    if (duration != film.duration) return false;
    if (migrate != film.migrate) return false;
    if (title != null ? !title.equals(film.title) : film.title != null) return false;
    if (synopsis != null ? !synopsis.equals(film.synopsis) : film.synopsis != null) return false;
    if (poster != null ? !poster.equals(film.poster) : film.poster != null) return false;
    if (dateMigrate != null ? !dateMigrate.equals(film.dateMigrate) : film.dateMigrate != null) return false;
    if (user != null ? !user.getUsername().equals(film.user.getUsername()) : film.user != null) return false;
    if (reviews != null ? !reviews.equals(film.reviews) : film.reviews != null) return false;
    if (filmDirector != null ? !filmDirector.equals(film.filmDirector) : film.filmDirector != null) return false;
    if (filmActors != null ? !filmActors.equals(film.filmActors) : film.filmActors != null) return false;
    if (filmComposers != null ? !filmComposers.equals(film.filmComposers) : film.filmComposers != null) return false;
    if (filmScreenwriters != null ? !filmScreenwriters.equals(film.filmScreenwriters) : film.filmScreenwriters != null) return false;
    return filmCinematographers != null ? filmCinematographers.equals(film.filmCinematographers) : film.filmCinematographers == null;
  }

  @Override
  public int hashCode() {
    return this.getClass().hashCode();
  }

  @Override
  public String toString() {
    return title + " : " + year;
  }


}
