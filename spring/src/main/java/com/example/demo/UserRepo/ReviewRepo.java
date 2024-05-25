package com.example.demo.UserRepo;

import com.example.demo.Entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepo extends JpaRepository<Review, Long> {

    List<Review> findReviewByProductId(Long productId);
    void deleteReviewByProductId(Long productId);
    void deleteReviewByReviewId(Long reviewId);

}
