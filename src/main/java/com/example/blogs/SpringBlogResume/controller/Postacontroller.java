package com.example.blogs.SpringBlogResume.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.blogs.SpringBlogResume.Secuirty.PostService;
import com.example.blogs.SpringBlogResume.dto.PostDto;

@RestController
@RequestMapping("/api/posts/")
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
public class Postacontroller {
	
	@Autowired
	 private PostService postService;
	
	@PostMapping
	public ResponseEntity<?> createPost(@RequestBody PostDto postDto) {
		 postService.createPost(postDto);
		 return new ResponseEntity<Object>(HttpStatus.OK);
	}
	
	@GetMapping("/all")
    public ResponseEntity<List<PostDto>> showAllPosts() {
        return new ResponseEntity<>(postService.showAllPosts(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<PostDto> getSinglePost(@PathVariable @RequestBody Long id) {
        return new ResponseEntity<>(postService.readSinglePost(id), HttpStatus.OK);
    }
}
