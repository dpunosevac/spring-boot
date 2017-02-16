package org.socialfun.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.socialfun.utils.CustomJsonDateTimeDeserializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Entity
public class Comment {

	private Long id;
	private String content;
	private Date date;
	private User user;
	private Post post;
	
	Comment() {
	}
	
	public Comment(String content, Date date, User user, Post post){
		this.content = content;
		this.date = date;
		this.user = user;
		this.post = post;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDate() {
		return date;
	}

	@JsonDeserialize(using = CustomJsonDateTimeDeserializer.class)
	public void setDate(Date date) {
		this.date = date;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id")
	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}
}
