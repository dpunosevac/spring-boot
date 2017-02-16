package org.socialfun.api;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;

public interface IAbstractController<T, D extends ResourceSupport> {
	
	ResponseEntity<Resources<D>> listAll();
	ResponseEntity<D> findOne(long id);
	ResponseEntity<D> create(T input);
	ResponseEntity<D> update(final long id, T input);
	ResponseEntity<D> delete(final long id);
}
