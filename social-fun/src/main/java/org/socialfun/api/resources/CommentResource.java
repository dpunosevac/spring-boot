package org.socialfun.api.resources;

import java.util.Date;

import org.socialfun.utils.CustomJsonDateTimeSerializer;
import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Relation(value = "comment", collectionRelation = "comments")
public class CommentResource extends ResourceWithEmbeddeds {

	private final String content;
	private final Date date;

	@JsonCreator
	public CommentResource(@JsonProperty("content") String content, @JsonProperty("date") Date date) {
		this.content = content;
		this.date = date;
	}

	public String getContent() {
		return content;
	}

	@JsonSerialize(using = CustomJsonDateTimeSerializer.class)
	public Date getDate() {
		return date;
	}
}
