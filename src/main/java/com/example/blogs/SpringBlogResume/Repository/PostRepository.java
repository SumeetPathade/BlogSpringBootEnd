package com.example.blogs.SpringBlogResume.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.blogs.SpringBlogResume.Model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}
