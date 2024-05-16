package com.tokioFilme.repository;

import com.tokioFilme.domain.Review;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface ReviewRepository extends CrudRepository<Review, Long> {

  Set<Review> findByFilmTitle(String title);

  @Query("SELECT r FROM reviews r JOIN FETCH r.user u WHERE u.username = ?1")
  Set<Review> findByUserUsername(String username);
}
