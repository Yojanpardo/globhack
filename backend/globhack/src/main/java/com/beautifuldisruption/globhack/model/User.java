/**
 * 
 */
package com.beautifuldisruption.globhack.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.factory.annotation.Value;

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
	private boolean isActive;
}
