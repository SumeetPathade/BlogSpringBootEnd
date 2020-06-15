package com.example.blogs.SpringBlogResume.Secuirty;

import static java.util.stream.Collectors.toList;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.blogs.SpringBlogResume.Exception.PostNotFoundException;
import com.example.blogs.SpringBlogResume.Model.Post;
import com.example.blogs.SpringBlogResume.Repository.PostRepository;
import com.example.blogs.SpringBlogResume.Service.AuthService;
import com.example.blogs.SpringBlogResume.dto.PostDto;
@Service
public class PostService {
	
	@Autowired
	private AuthService authService;
	
	
	@Autowired
	private PostRepository postRepository;
	
	
	@Transactional
    public List<PostDto> showAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(this::mapFromPostToDto).collect(toList());
    }

//    @Transactional
//    public void createPost(PostDto postDto) {
//        Post post = mapFromDtoToPost(postDto);
//        postRepository.save(post);
//    }

	
	  @Transactional 
	  public PostDto readSinglePost(Long id) { 
		  Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("For id " + id));
	        return mapFromPostToDto(post);
		  }
	 
	
	  @Transactional
	    public void createPost(PostDto postDto) {
	        Post post = mapFromDtoToPost(postDto);
	        postRepository.save(post);
	    }
	  
	private PostDto mapFromPostToDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setUsername(post.getUsername());
        return postDto;
    }

    private Post mapFromDtoToPost(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        User loggedInUser = authService.getCurrentUser().orElseThrow(() -> new IllegalArgumentException("User Not Found"));
        post.setCreatedOn(Instant.now());
        post.setUsername(loggedInUser.getUsername());
        post.setUpdatedOn(Instant.now());
        return post;
    }

	
}
