/**
 * 
 */
package com.beautifuldisruption.globhack.view.resources;

import java.sql.Timestamp;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.beautifuldisruption.globhack.model.User;
import com.beautifuldisruption.globhack.services.UserServices;
import com.beautifuldisruption.globhack.view.resources.vo.UserVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javassist.NotFoundException;

/**
 * @author yojan
 *
 */
@CrossOrigin("*")
@RestController
@RequestMapping("users")
@Api(tags = "User resources")
public class UserResources {
	private final UserServices userServices;
	
	public UserResources(UserServices userServices) {
		this.userServices = userServices;
	}

	@GetMapping("/{userId}")
	public ResponseEntity<User> getUser(@PathVariable("userId") String userId){
		User user = this.userServices.findByUserId(userId);
		ResponseEntity<User> response;
		try {
			if (user == null) {
				response = new ResponseEntity<User>(user,HttpStatus.OK);
			} else {
				response = new ResponseEntity("Not found", HttpStatus.NOT_FOUND);
			}
		} catch (Exception ex) {
			response = new ResponseEntity(ex, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	@GetMapping("/")
	@ApiOperation(
			value = "Get all users", 
			notes = "get all registered users in our platform"
	)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "all users getted"),
			@ApiResponse(code = 404, message = "No users found"),
			@ApiResponse(code = 500, message = "Internal server error")
	})
	public ResponseEntity<List<User>> getUsers(){
		ResponseEntity<List<User>> response;
		try {
			response = new ResponseEntity<List<User>>(this.userServices.findAll(), HttpStatus.OK);
		} catch (NullPointerException ex) {
			response = new ResponseEntity(ex, HttpStatus.NOT_FOUND);
		} catch (Exception ex) {
			response = new ResponseEntity(ex, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	@PostMapping
	@ApiOperation(
			value = "Register user", 
			notes = "Receives user data from auth0 and register it in our backend"
	)
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "User created successfully "),
			@ApiResponse(code = 409, message = "Auth0 user is already register"),
			@ApiResponse(code = 500, message = "Internal server error")
	})
	public ResponseEntity<User> registerUser(@RequestBody UserVO userVo){

		User user = new User();
		user.setAuth0Id(userVo.getAuth0Id());
		user.setCreated(new Timestamp(System.currentTimeMillis()));
		user.setModified(new Timestamp(System.currentTimeMillis()));
		user.setActive(true);
		user.setVerified(false);
		ResponseEntity<User> response;
		try {
			response = new ResponseEntity<User>(this.userServices.register(user), HttpStatus.CREATED);
		} catch (DataIntegrityViolationException ex) {
			response = new ResponseEntity(ex.getCause(),HttpStatus.CONFLICT);
		} catch (Exception ex) {
			response = new ResponseEntity(ex,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	@PutMapping("/{userId}")
	@ApiOperation(
			value = "Update an user",
			notes = "service that updates an user"
	)
	@ApiResponses(value = {
			@ApiResponse(code =200, message = "User updated susccessfully")	
	})
	public ResponseEntity<User> updateUser(@PathVariable("userId") String userId,
			@RequestBody UserVO userVo){
		User user = userServices.findByUserId(userId);
		ResponseEntity<User> response;
		if (user != null) {
			user.setModified(new Timestamp(System.currentTimeMillis()));
			user.setPhoneNumber(userVo.getPhoneNumber());
			try {
				response = new ResponseEntity<User>(this.userServices.update(user), HttpStatus.OK);				
			} catch (Exception ex) {
				response = new ResponseEntity(ex.getCause(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			response = new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		
		return response;
	}
	
}
