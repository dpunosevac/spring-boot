package org.socialfun.api.impl;

import org.socialfun.api.IPostController;
import org.socialfun.api.resources.PostResource;
import org.socialfun.api.resources.PostResourceAssembler;
import org.socialfun.domain.Post;
import org.socialfun.repository.PostRepository;
import org.socialfun.utils.PropertyUtil;
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

@RestController
@RequestMapping("/posts")
@ExposesResourceFor(PostResource.class)
@Transactional
public class PostController implements IPostController {
	
	private final PostRepository postRepository;
	private final PostResourceAssembler postResourceAssembler;

	@Autowired
	public PostController(PostRepository postRepository, PostResourceAssembler postResourceAssembler) {
		this.postRepository = postRepository;
		this.postResourceAssembler = postResourceAssembler;
	}

	@Override
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Resources<PostResource>> listAll() {
		final Iterable<Post> posts = postRepository.findAll();
		final Resources<PostResource> resources = postResourceAssembler.toEmbeddedList(posts);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		return new ResponseEntity<Resources<PostResource>>(resources, httpHeaders, HttpStatus.OK);
	}

	@Override
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<PostResource> findOne(@PathVariable long id) {
		final Post post = postRepository.findOne(id);
		final PostResource resource = postResourceAssembler.toResource(post);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
 		return new ResponseEntity<PostResource>(resource, httpHeaders, HttpStatus.OK);
	}

	@Override
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<PostResource> create(@RequestBody Post input) {
		input.setComments(PropertyUtil.checkList(input.getComments()));
		
		final Post post = postRepository.save(input);
		final PostResource resource = postResourceAssembler.toResource(post);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		return new ResponseEntity<PostResource>(resource, httpHeaders, HttpStatus.CREATED);
	}

	@Override
//	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<PostResource> update(@PathVariable long id, Post input) {
		if(!postRepository.exists(id)){
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setContentType(MediaType.APPLICATION_JSON);
			
			return new ResponseEntity<>(httpHeaders, HttpStatus.BAD_REQUEST);
		}
		
		input.setId(id);
		input.setComments(PropertyUtil.checkList(input.getComments()));
		
		final Post post = postRepository.save(input);
		final PostResource resource = postResourceAssembler.toResource(post);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		return new ResponseEntity<PostResource>(resource, httpHeaders, HttpStatus.OK);
	}

	@Override
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<PostResource> delete(@PathVariable long id) {
		postRepository.delete(id);
		
		return new ResponseEntity<PostResource>(HttpStatus.OK);
	}
}
