package com.example.demo.UserRepo;


import com.example.demo.Entity.TopicComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicCommentRepo extends JpaRepository<TopicComment, Long> {
    List<TopicComment> findTopicCommentByTopicId(Long topicId);

}
