/**
 * 
 */
package com.beautifuldisruption.globhack.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yojan
 *
 */
@Data
@Entity
@Table(name="users")
@EqualsAndHashCode(callSuper=false)
public class User extends Base{
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	private String userId;
	@Column(name = "auth0_id", unique = true)
	private String auth0Id;
	private String phoneNumber;
	private boolean isVerified;
	@OneToMany(mappedBy = "user")
	private Set<Post> posts;
	
	
	public User() {
		super();
	}
}
