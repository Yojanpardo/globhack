/**
 * 
 */
package com.beautifuldisruption.globhack.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beautifuldisruption.globhack.model.User;
import com.beautifuldisruption.globhack.repository.UserRepository;



/**
 * @author yojan
 *
 */
@Service
@Transactional(readOnly = true)
public class UserServices {
	private final UserRepository userRepository;
	
	public UserServices(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Transactional
	public User register(User user) {
		return this.userRepository.save(user);
	}
	
	@Transactional
	public User update(User user) {
		return this.userRepository.save(user);
	}
	
	public User findByUserId(String userId) {
		return this.userRepository.findByUserId(userId);
	}
	
	public List<User> findAll(){
		return this.userRepository.findAll();
	}
}
