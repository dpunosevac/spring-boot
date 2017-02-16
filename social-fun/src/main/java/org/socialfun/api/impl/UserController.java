package org.socialfun.api.impl;

import org.socialfun.api.IUserController;
import org.socialfun.api.resources.UserResource;
import org.socialfun.api.resources.UserResourceAssembler;
import org.socialfun.domain.User;
import org.socialfun.repository.UserRepository;
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
@RequestMapping("/users")
@ExposesResourceFor(UserResource.class)
@Transactional
public class UserController implements IUserController {

	private final UserRepository userRepository;
	private final UserResourceAssembler userResourceAssembler;

	@Autowired
	public UserController(UserRepository userRepository, UserResourceAssembler userResourceAssembler) {
		this.userRepository = userRepository;
		this.userResourceAssembler = userResourceAssembler;
	}

	@Override
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Resources<UserResource>> listAll() {
		final Iterable<User> users = userRepository.findAll();
		final Resources<UserResource> resources = userResourceAssembler.toEmbeddedList(users);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		return new ResponseEntity<Resources<UserResource>>(resources, httpHeaders, HttpStatus.OK);
	}

	@Override
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<UserResource> findOne(@PathVariable long id) {
		final User user = userRepository.findOne(id);
		final UserResource resource = userResourceAssembler.toResource(user);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		return new ResponseEntity<UserResource>(resource, httpHeaders, HttpStatus.OK);
	}

	@Override
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<UserResource> create(@RequestBody User input) {

		input.setPosts(PropertyUtil.checkList(input.getPosts()));
		input.setComments(PropertyUtil.checkList(input.getComments()));

		final User user = userRepository.save(input);
		final UserResource resource = userResourceAssembler.toResource(user);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		return new ResponseEntity<UserResource>(resource, httpHeaders, HttpStatus.CREATED);
	}

	@Override
//	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<UserResource> update(@PathVariable long id, User input) {
		if (!userRepository.exists(id)) {
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setContentType(MediaType.APPLICATION_JSON);

			return new ResponseEntity<>(httpHeaders, HttpStatus.BAD_REQUEST);
		}

		input.setId(id);
		input.setPosts(PropertyUtil.checkList(input.getPosts()));
		input.setComments(PropertyUtil.checkList(input.getComments()));

		final User user = userRepository.save(input);
		final UserResource resource = userResourceAssembler.toResource(user);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		return new ResponseEntity<UserResource>(resource, httpHeaders, HttpStatus.OK);
	}

	@Override
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<UserResource> delete(@PathVariable final long id) {
		userRepository.delete(id);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
