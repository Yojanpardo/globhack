/**
 * 
 */
package com.beautifuldisruption.globhack.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.beautifuldisruption.globhack.model.User;

/**
 * @author yojan
 *
 */
public interface UserRepository extends JpaRepository<User, String>{
	public User findByUserId(String userId);
}
