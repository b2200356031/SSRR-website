package com.example.demo.UserRepo;

import com.example.demo.Entity.ForumTopic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ForumTopicRepo extends JpaRepository<ForumTopic, Long> {

    List<ForumTopic> findForumTopicByTitle(String title);

}
