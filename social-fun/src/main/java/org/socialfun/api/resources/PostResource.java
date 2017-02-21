package org.socialfun.api.resources;

import java.util.Date;

import org.socialfun.utils.CustomJsonDateTimeSerializer;
import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@Relation(value = "post", collectionRelation = "posts")
public class PostResource extends ResourceWithEmbeddeds {

	private final String content;
	private final Date date;

	@JsonCreator
	public PostResource(@JsonProperty("contnet") String content, @JsonProperty("date") Date date) {
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
