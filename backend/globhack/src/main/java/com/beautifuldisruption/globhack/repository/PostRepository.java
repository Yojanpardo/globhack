package com.beautifuldisruption.globhack.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.beautifuldisruption.globhack.model.Post;

public interface PostRepository extends JpaRepository<Post, String>{
	public Post findByPostId(String postId);
}
