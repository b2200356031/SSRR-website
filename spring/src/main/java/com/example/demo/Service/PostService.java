package com.example.demo.Service;


import com.example.demo.DTO.PostDto;
import com.example.demo.Entity.UserLike;
import com.example.demo.Entity.Post;
import com.example.demo.Entity.Users;
import com.example.demo.UserRepo.LikeRepo;
import com.example.demo.UserRepo.PostRepository;
import com.example.demo.mappers.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PostMapper postMapper;
    private final PostRepository postRepository;
    private final UtilService utilService;
    private final LikeRepo likeRepo;

    public PostDto addPost(PostDto postDto){
        Users user = utilService.getAuthenticatedUser();
        Post post =postMapper.map(postDto);
        post.setUser(user);
        post.setCreationDate(LocalDateTime.now());
        post=postRepository.save(post);
        user.getPosts().add(post);
        return postMapper.map(post);
    }
    public PostDto updatePost(PostDto postDto){
        Post post=postRepository.findById(postDto.getId()).orElseThrow(()->new RuntimeException("post not found"));
        post.setContent(postDto.getContent());
        post.setTitle(postDto.getTitle());
        return postMapper.map(post);
    }
    public void deletePost(Long postId){
       postRepository.deleteById(postId);
    }
    public List<PostDto> getAllPosts( ){
        List <PostDto> posts=postRepository.findAll().stream().map(postMapper::map).toList();
        return posts;
    }
    public PostDto likeOrDislike(String action, Long id) {
        Post post = postRepository.findById(id).get();
        Users user=utilService.getAuthenticatedUser();
        Optional<UserLike> existingLike = likeRepo.findByUserAndPost(user, post);
        switch (action) {
            case "like":
                if (existingLike.isPresent()) {
                    return postMapper.map(post);
                }
                UserLike userLike = new UserLike();
                userLike.setUser(user);
                userLike.setPost(post);
                likeRepo.save(userLike);
                if(post.getUserLikes()==null){
                    post.setUserLikes(new HashSet<>());
                }
                if(user.getUserLikes()==null){
                    user.setUserLikes(new HashSet<>());
                }
                post.getUserLikes().add(userLike);
                user.getUserLikes().add(userLike);
                post.setLikesCount(post.getLikesCount()!=null?post.getLikesCount()+ 1:1);
                return postMapper.map(post);
            case "dislike":
                if (existingLike.isPresent()) {
                    likeRepo.delete(existingLike.get());
                    post.getUserLikes().remove(existingLike.get());
                    user.getUserLikes().remove(existingLike.get());
                    post.setLikesCount(post.getLikesCount() != null && post.getLikesCount() > 0 ? post.getLikesCount() - 1 : 0);
                    postRepository.save(post);
                }
                return postMapper.map(post);
            default:
                return postMapper.map(post);
        }
    }
}
