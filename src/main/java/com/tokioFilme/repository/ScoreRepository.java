package com.tokioFilme.repository;

import com.tokioFilme.domain.Score;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface ScoreRepository extends CrudRepository<Score, Long> {

  Set<Score> findByFilmTitle(String title);

  @Query("SELECT AVG(s.value) FROM scores s WHERE s.film.title = ?1")
  double findAvgScoreByFilmTitle(String title);
}
