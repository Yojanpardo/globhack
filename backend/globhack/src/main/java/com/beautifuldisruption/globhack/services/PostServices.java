/**
 * 
 */
package com.beautifuldisruption.globhack.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beautifuldisruption.globhack.model.Post;
import com.beautifuldisruption.globhack.repository.PostRepository;

/**
 * @author yojan
 *
 */
@Service
@Transactional(readOnly = true)
public class PostServices {
	
	@Autowired
	private PostRepository postRepository;
	
	@Transactional
	public Post create(Post post) {
		return this.postRepository.save(post);
	}
	
	@Transactional
	public Post update(Post post) {
		return this.postRepository.save(post);
	}
	
	public Post findByPostId(String postId) {
		return this.postRepository.findByPostId(postId);
	}

	public List<Post> findAll(){
		return this.postRepository.findAll();
	}

}
