package com.tokioFilme.service;

import com.tokioFilme.domain.Review;

import java.util.Set;

public interface ReviewService {

  Review addReview(Review review);

  Set<Review> findByUsername(String username);
}
