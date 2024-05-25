package com.example.demo.Controllers;


import com.example.demo.Entity.Product;
import com.example.demo.Entity.Review;
import com.example.demo.Entity.Users;
import com.example.demo.Service.ProductService;
import com.example.demo.Service.ReviewService;
import com.example.demo.Service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", maxAge = 3600)
public class ReviewController {

    private final ReviewService reviewService;
    private final ProductService productService;
    private final UserService userService;

    public ReviewController(ReviewService reviewService, ProductService productService, UserService userService) {
        this.reviewService = reviewService;
        this.productService = productService;
        this.userService = userService;
    }
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Review>> getReview(@PathVariable Long productId){
        List<Review> reviews = reviewService.findReviewsByProductId(productId);
        Product product = productService.findProductById(productId);
        product.setReviewCount(reviews.size());
        Integer sum = 0;
        if (!reviews.isEmpty()){
            for (Review review : reviews) {
                sum += review.getRating();
            }

            double rating =  ((double) sum / product.getReviewCount());
            rating = Math.round(rating * 10) / 10.0;
            product.setRating(rating);
        }
        else{
            product.setRating(0.0);
        }
        productService.updateProduct(product);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @PostMapping("/product/{productId}")
    public ResponseEntity<Review> addReview(@PathVariable("productId")Long productId,@RequestBody Review review,@RequestHeader("Authorization") String token){
        Users user = userService.getCurrentUser(token.substring(7));
        Product product = productService.findProductById(productId);
        if (review.getRating() == null){
            review.setRating(5);
        }
        Review newReview = reviewService.addReview(product,review,user);
        return new ResponseEntity<>(newReview, HttpStatus.CREATED);
    }


    @PutMapping("/product/update")
    public ResponseEntity<Review> updateReview(@RequestBody Review review,@RequestHeader("Authorization") String token){
        Users user = userService.getCurrentUser(token.substring(7));
        Review updateReview = reviewService.updateReview(review,user);
        return new ResponseEntity<>(updateReview, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/product/delete/{reviewId}")
    public ResponseEntity<?> deleteProduct(@PathVariable("reviewId")Long reviewId){
        reviewService.deleteReview(reviewId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/addPoints")
    public ResponseEntity<Review> addPoints(@RequestBody Review review){
        reviewService.addPoints(review);
        return new ResponseEntity<>(review,HttpStatus.OK);
    }









}