/**
 * 
 */
package com.beautifuldisruption.globhack.view.resources;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beautifuldisruption.globhack.model.Post;
import com.beautifuldisruption.globhack.model.User;
import com.beautifuldisruption.globhack.services.PostServices;
import com.beautifuldisruption.globhack.services.UserServices;
import com.beautifuldisruption.globhack.view.resources.vo.PostResponse;
import com.beautifuldisruption.globhack.view.resources.vo.PostVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author yojan
 *
 */
@CrossOrigin("*")
@RestController
@RequestMapping("posts")
@Api(tags = "Post resources")
public class PostResources {
	
	@Autowired
	private PostServices postServices;
	
	@Autowired
	private UserServices userServices;
	
	@GetMapping
	@ApiOperation(
			value = "Get all posts", 
			notes = "get all posts in our platform"
	)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "all posts getted"),
			@ApiResponse(code = 404, message = "No posts found"),
			@ApiResponse(code = 500, message = "Internal server error")
	})
	public ResponseEntity<List<PostResponse>> getPosts(){
		ResponseEntity<List<PostResponse>> response;
		List<PostResponse> postsResponse;
		List<Post> posts;
		try {
			posts = this.postServices.findAll();
			postsResponse = new ArrayList<PostResponse>();
			for (Post post : posts) {
				PostResponse postResponse = new PostResponse();
				postResponse.setActive(post.isActive());
				postResponse.setContent(post.getContent());
				postResponse.setTitle(post.getTitle());
				postResponse.setUserId(post.getUser().getUserId());
				postResponse.setCreated(post.getCreated());
				postResponse.setModified(post.getModified());
				if (post.getFatherPost() != null) {
					postResponse.setFatherPostId(post.getFatherPost().getPostId());
				}
				postsResponse.add(postResponse);
			}
			response = new ResponseEntity<List<PostResponse>>(postsResponse, HttpStatus.OK);
		} catch (NullPointerException ex) {
			response = new ResponseEntity(ex, HttpStatus.NOT_FOUND);
		} catch (Exception ex) {
			response = new ResponseEntity(ex, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	@GetMapping("/{postId}")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "post getted"),
			@ApiResponse(code = 404, message = "post found"),
			@ApiResponse(code = 500, message = "Internal server error")
	})
	public ResponseEntity<PostResponse> getPost(@PathVariable("postId") String postId){
		ResponseEntity<PostResponse> response;
		try {
			Post post = this.postServices.findByPostId(postId);
			
			if (post != null) {
				PostResponse postResponse = new PostResponse();
				postResponse.setActive(post.isActive());
				postResponse.setContent(post.getContent());
				postResponse.setTitle(post.getTitle());
				postResponse.setUserId(post.getUser().getUserId());
				postResponse.setCreated(post.getCreated());
				postResponse.setModified(post.getModified());
				if (post.getFatherPost() != null) {
					postResponse.setFatherPostId(post.getFatherPost().getPostId());
				}
				response = new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
			} else {
				response = new ResponseEntity("Not found", HttpStatus.NOT_FOUND);
			}
		} catch (Exception ex) {
			response = new ResponseEntity(ex, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	
	@PostMapping
	@ApiOperation(
			value = "Register post", 
			notes = "Register a new post in our platform"
	)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Post created successfully "),
			@ApiResponse(code = 500, message = "Internal server error")
	})
	public ResponseEntity<PostResponse> publishPost(@RequestBody PostVO postVo){
		
		User user = userServices.findByUserId(postVo.getUserId());
		Post post = new Post();
		
		post.setActive(true);
		post.setCreated(new Timestamp(System.currentTimeMillis()));
		post.setModified(new Timestamp(System.currentTimeMillis()));
		post.setUser(user);
		post.setContent(postVo.getContent());
		post.setTitle(postVo.getTitle());
		
		try {
			
			if (postVo.getFatherPostId() != null && !postVo.getFatherPostId().equals("")) {
				Post fatherPost = postServices.findByPostId(postVo.getFatherPostId());
			}
			post = this.postServices.create(post);
			
			PostResponse postResponse = new PostResponse();
			postResponse.setActive(post.isActive());
			postResponse.setContent(post.getContent());
			postResponse.setTitle(post.getTitle());
			postResponse.setUserId(post.getUser().getUserId());
			postResponse.setCreated(post.getCreated());
			postResponse.setModified(post.getModified());
			if (post.getFatherPost() != null) {
				postResponse.setFatherPostId(post.getFatherPost().getPostId());
			}
			
			return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
		} catch (DataIntegrityViolationException ex) {
			return new ResponseEntity(ex.getCause(),HttpStatus.CONFLICT);
		} catch (Exception ex) {
			return new ResponseEntity(ex,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/{postId}")
	@ApiOperation(
			value = "Update a post",
			notes = "service that updates a post"
	)
	@ApiResponses(value = {
			@ApiResponse(code =200, message = "Post updated susccessfully")	
	})
	public ResponseEntity<PostResponse> updatePost(@PathVariable("postId") String postId,
			@RequestBody PostVO postVo){
		Post post = postServices.findByPostId(postId);
		ResponseEntity<PostResponse> response;
		if (post != null) {
			Post fatherPost = postServices.findByPostId(postVo.getFatherPostId());
			User user = userServices.findByUserId(postVo.getUserId());
			
			post.setModified(new Timestamp(System.currentTimeMillis()));
			post.setActive(postVo.isActive());
			post.setContent(postVo.getContent());
			post.setTitle(postVo.getTitle());
			post.setFatherPost(fatherPost);
			post.setUser(user);
			
			try {
				post = this.postServices.update(post);
				
				PostResponse postResponse = new PostResponse();
				postResponse.setActive(post.isActive());
				postResponse.setContent(post.getContent());
				postResponse.setTitle(post.getTitle());
				postResponse.setUserId(post.getUser().getUserId());
				postResponse.setCreated(post.getCreated());
				postResponse.setModified(post.getModified());
				if (post.getFatherPost() != null) {
					postResponse.setFatherPostId(post.getFatherPost().getPostId());
				}
				response = new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);				
			} catch (Exception ex) {
				response = new ResponseEntity(ex.getCause(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			response = new ResponseEntity<PostResponse>(HttpStatus.NOT_FOUND);
		}
		
		return response;
	}
}
