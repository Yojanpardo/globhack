/**
 * 
 */
package com.beautifuldisruption.globhack.view.resources.vo;

import java.util.Date;

import lombok.Data;

/**
 * @author yojan
 *
 */
@Data
public class UserVO {
	private String auth0Id;
    private String phoneNumber;
    private boolean verified;
    private boolean active;
}
