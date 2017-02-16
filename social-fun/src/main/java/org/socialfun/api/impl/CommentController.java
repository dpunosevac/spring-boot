package org.socialfun.api.impl;

import org.socialfun.api.ICommentController;
import org.socialfun.api.resources.CommentResource;
import org.socialfun.api.resources.CommentResourceAssembler;
import org.socialfun.domain.Comment;
import org.socialfun.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//TODO: Add logs, validation and exceptions 

@RestController
@RequestMapping("/comments")
@ExposesResourceFor(CommentResource.class)
@Transactional
public class CommentController implements ICommentController {

	private final CommentRepository commentRepository;
	private final CommentResourceAssembler commentResourceAssembler;

	@Autowired
	public CommentController(CommentRepository commentRepository, CommentResourceAssembler commentResourceAssembler) {
		this.commentRepository = commentRepository;
		this.commentResourceAssembler = commentResourceAssembler;
	}

	@Override
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Resources<CommentResource>> listAll() {
		final Iterable<Comment> comments = commentRepository.findAll();
		final Resources<CommentResource> resources = commentResourceAssembler.toEmbeddedList(comments);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		return new ResponseEntity<Resources<CommentResource>>(resources, httpHeaders, HttpStatus.OK);
	}

	@Override
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<CommentResource> findOne(@PathVariable long id) {
		final Comment comment = commentRepository.findOne(id);
		final CommentResource resource = commentResourceAssembler.toResource(comment);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		return new ResponseEntity<CommentResource>(resource, httpHeaders, HttpStatus.OK);
	}

	@Override
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<CommentResource> create(@RequestBody Comment input) {
		final Comment comment = commentRepository.save(input);
		final CommentResource resource = commentResourceAssembler.toResource(comment);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		return new ResponseEntity<CommentResource>(resource, httpHeaders, HttpStatus.CREATED);
	}

	@Override
//	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<CommentResource> update(@PathVariable long id, Comment input) {
		if (!commentRepository.exists(id)) {
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setContentType(MediaType.APPLICATION_JSON);

			return new ResponseEntity<>(httpHeaders, HttpStatus.BAD_REQUEST);
		}

		input.setId(id);

		final Comment comment = commentRepository.save(input);
		final CommentResource resource = commentResourceAssembler.toResource(comment);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		return new ResponseEntity<CommentResource>(resource, httpHeaders, HttpStatus.OK);
	}

	@Override
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<CommentResource> delete(@PathVariable long id) {
		commentRepository.delete(id);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
