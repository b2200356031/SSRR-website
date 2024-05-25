package com.example.demo.Entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;


    public void setProduct(Product product) {
        this.product = product;
    }

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id") // Assuming "id" is the primary key of the Product entity
    private Product product;

    private Long authorId;
    private String authorName;
    private String authorSurname;

    private String content;
    private Integer rating;
    private Integer likes;
    private Integer dislikes;
    private List<Long> LikedBy;
    private List<Long> dislikedBy;

    public Review() {
        this.likes = 0;
        this.dislikes = 0;
        this.dislikedBy = new ArrayList<Long>();
        this.LikedBy = new ArrayList<Long>();;
    }


    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getDislikes() {
        return dislikes;
    }

    public void setDislikes(Integer dislikes) {
        this.dislikes = dislikes;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getAuthorId() {return authorId;}

    public void setAuthorId(Long authorId) {this.authorId = authorId;}

    public String getAuthorName() {return authorName;}

    public void setAuthorName(String authorName) {this.authorName = authorName;}

    public String getAuthorSurname() {return authorSurname;}

    public void setAuthorSurname(String authorSurname) {this.authorSurname = authorSurname;}

    public Product getProduct() {return product;}

    public Long getReviewId() {return reviewId;}

    public void setReviewId(Long reviewId) {this.reviewId = reviewId;}

    public List<Long> getLikedBy() {return LikedBy;}

    public void setLikedBy(List<Long> likedBy) {LikedBy = likedBy;}

    public List<Long> getDislikedBy() {return dislikedBy;}

    public void setDislikedBy(List<Long> dislikedBy) {this.dislikedBy = dislikedBy;}
}
