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
public class PostVO {
	private String title;
	private String content;
	private String userId;
	private String fatherPostId;
	private String postId;
	private boolean isActive;
}
