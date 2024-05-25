package com.example.demo.UserRepo;

import com.example.demo.Entity.Comment;
import com.example.demo.Entity.UserLike;
import com.example.demo.Entity.Post;
import com.example.demo.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepo extends JpaRepository<UserLike, Long> {
    Optional<UserLike> findByUserAndPost(Users user, Post post);
    Optional<UserLike> findByUserAndComment(Users user, Comment comment);

}