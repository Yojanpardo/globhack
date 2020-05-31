/**
 * 
 */
package com.beautifuldisruption.globhack.model;

import java.util.Date;

import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

/**
 * @author yojan
 *
 */
@Data
@MappedSuperclass
public abstract class Base {
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	@Temporal(TemporalType.TIMESTAMP)
	private Date modified;
	private boolean isActive;
}
