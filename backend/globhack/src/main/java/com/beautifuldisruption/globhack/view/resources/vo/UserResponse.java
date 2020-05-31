package com.beautifuldisruption.globhack.view.resources.vo;

import java.util.Date;

import lombok.Data;

@Data
public class UserResponse {
	private Date created;
    private Date modified;
    private String userId;
    private String auth0Id;
    private String phoneNumber;
    private boolean verified;
    private boolean active;
}
