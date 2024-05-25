package com.example.demo.Service;

import com.example.demo.Entity.Product;
import com.example.demo.Entity.Review;
import com.example.demo.Entity.Users;
import com.example.demo.UserRepo.ReviewRepo;
import com.example.demo.UserRepo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ReviewService {
    private final ReviewRepo reviewRepo;
    private final UserRepo userRepo;

    @Autowired
    public ReviewService(ReviewRepo reviewRepo,UserRepo userRepo) {
        this.reviewRepo = reviewRepo;
        this.userRepo = userRepo;
    }



    public Review addReview(Product product, Review review, Users user){
        review.setAuthorId(user.getId());
        review.setAuthorName(user.getFirstName());
        review.setAuthorSurname(user.getLastName());
        review.setProduct(product);
        return reviewRepo.save(review);
    }


    public List<Review> findReviewsByProductId(Long productId){
        return reviewRepo.findReviewByProductId(productId);
    }

    public Review updateReview(Review review, Users user){


        Long userId = user.getUserId();
        Long reviewId = review.getReviewId();
        if (review.getLikes()> reviewRepo.findById(reviewId).get().getLikes() && !(review.getDislikedBy().contains(userId))){

            if (review.getLikedBy().contains(userId)){
                review.getLikedBy().remove(userId);
                user.setPoints(user.getPoints() - 10);
            }
            else {
                review.getLikedBy().add(userId);
                user.setPoints(user.getPoints() + 10);
            }


        }

        if (review.getDislikes()> reviewRepo.findById(reviewId).get().getDislikes() && !(review.getLikedBy().contains(userId))){

            if (review.getDislikedBy().contains(userId)){
                review.getDislikedBy().remove(userId);
                user.setPoints(user.getPoints() + 10);
            }
            else {
                review.getDislikedBy().add(userId);
                user.setPoints(user.getPoints() - 10);
            }

        }

        review.setLikes(review.getLikedBy().size());
        review.setDislikes(review.getDislikedBy().size());
        return reviewRepo.save(review);
    }

    public void deleteReview(Long id){
        reviewRepo.deleteReviewByReviewId(id);
    }

    public void addPoints(Review review) {
        Long userId = review.getAuthorId();
        Users user = userRepo.findUserById(review.getAuthorId());
        user.setPoints(user.getPoints()+10);
        userRepo.save(user);
    }



}