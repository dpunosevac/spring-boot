package org.socialfun.api.resources;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.core.EmbeddedWrapper;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

public abstract class ResourceWithEmbeddeds extends ResourceSupport {

	// The @JsonUnwrapped annotation is required as EmbeddedWrappers are by
	// default serialized in a "_embedded" container,
	// that has to be added directly into the top level object
	@JsonUnwrapped
	private Resources<EmbeddedWrapper> embeddeds;

	public Resources<EmbeddedWrapper> getEmbeddeds() {
		return embeddeds;
	}

	public void setEmbeddeds(Resources<EmbeddedWrapper> embeddeds) {
		this.embeddeds = embeddeds;
	}
}
