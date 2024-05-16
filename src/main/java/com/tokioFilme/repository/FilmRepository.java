package com.tokioFilme.repository;

import com.tokioFilme.domain.Film;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface FilmRepository extends CrudRepository<Film, Long> {

  Optional<Film> findByTitleIgnoreCase(String title);
  Optional<Film> findByUri(String uri);
  Set<Film> findByYear(int year);
  Set<Film> findByDurationLessThanEqual(int duration);
  Set<Film> findByFilmDirectorNameContainsOrFilmDirectorSurnameContainsAllIgnoreCase(String name, String surname);
  Set<Film> findByFilmActorsNameContainsOrFilmActorsSurnameContainsAllIgnoreCase(String name, String surname);
  Set<Film> findByFilmCinematographersNameContainsOrFilmCinematographersSurnameContainsAllIgnoreCase(String name, String surname);
  Set<Film> findByFilmScreenwritersNameContainsOrFilmScreenwritersSurnameContainsAllIgnoreCase(String name, String surname);
  Set<Film> findByFilmComposersNameContainsOrFilmComposersSurnameContainsAllIgnoreCase(String name, String surname);
  Set<Film> findByAvgScoreGreaterThanEqual(int score);


  @Query("SELECT f FROM films f WHERE f.filmDirector.name = ?1 AND f.filmDirector.surname = ?2")
  Set<Film> findByDirectorNameAndDirectorSurname(String name, String surname);

  @Query("SELECT f FROM films f JOIN FETCH f.filmActors a WHERE a.name = ?1 AND a.surname = ?2")
  Set<Film> findByActorsNameAndActorsSurname(String name, String surname);

  Set<Film> findByTitleContainsIgnoreCase(String title);

  Set<Film> findAll();


}
