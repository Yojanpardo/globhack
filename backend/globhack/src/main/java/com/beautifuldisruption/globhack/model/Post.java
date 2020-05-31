/**
 * 
 */
package com.beautifuldisruption.globhack.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "posts")
@EqualsAndHashCode(callSuper=false)
public class Post extends Base {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	private String postId;
	@ManyToOne
	@JoinColumn(name = "userId", nullable = false)
	private User user;
	private String title;
	private String content;
	@ManyToOne
	@JoinColumn(name = "fatherPostId", nullable = true)
	private Post fatherPost;
	@OneToMany(mappedBy = "fatherPost")
	private Set<Post> childrenPosts;
	
	public Post() {
		super();
	}
}
