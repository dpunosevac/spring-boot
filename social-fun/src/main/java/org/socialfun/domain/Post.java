package org.socialfun.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.socialfun.utils.CustomJsonDateTimeDeserializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Entity
public class Post {

	private Long id;
	private String content;
	private Date date;
	private User user;
	private List<Comment> comments;

	Post() {
	}

	public Post(String content, Date date, User user, List<Comment> comments) {
		this.content = content;
		this.date = date;
		this.user = user;
		this.comments = comments;
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

	@OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
}
