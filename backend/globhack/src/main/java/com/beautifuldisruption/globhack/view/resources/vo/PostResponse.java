package com.beautifuldisruption.globhack.view.resources.vo;

import java.util.Date;

import lombok.Data;

@Data
public class PostResponse {
	    private String title;
	    private String content;
	    private String userId;
	    private String fatherPostId;
	    private boolean active;
		private Date created;
		private Date modified;
}
