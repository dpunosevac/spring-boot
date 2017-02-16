package org.socialfun.api.resources;

import org.socialfun.api.impl.UserController;
import org.socialfun.domain.Comment;
import org.socialfun.domain.Post;
import org.socialfun.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RelProvider;
import org.springframework.stereotype.Service;

@Service
public class UserResourceAssembler extends EmbeddableResourceAssemblerSupport<User, UserResource, UserController> {

	@Autowired
	private PostResourceAssembler postResourceAssembler;

	@Autowired
	private CommentResourceAssembler commentResourceAssembler;

	@Autowired
	public UserResourceAssembler(final EntityLinks entityLinks, final RelProvider relProvider) {
		super(entityLinks, relProvider, UserController.class, UserResource.class);
	}

	@Override
	public UserResource toResource(User user) {
		final UserResource resource = createResourceWithId(user.getId(), user);

		// Add posts and comments as links
		final String postsRel = relProvider.getCollectionResourceRelFor(PostResource.class);
		final String commentsRel = relProvider.getCollectionResourceRelFor(CommentResource.class);
		
		for (Post post : user.getPosts()) {
			resource.add(postResourceAssembler.linkToSingleResource(post).withRel(postsRel));
		}
		
		for (Comment comment : user.getComments()) {
			resource.add(commentResourceAssembler.linkToSingleResource(comment).withRel(commentsRel));
		}

		return resource;
	}

	@Override
	public Link linkToSingleResource(User user) {

		return entityLinks.linkToSingleResource(UserResource.class, user.getId());
	}

	@Override
	protected UserResource instantiateResource(User user) {

		return new UserResource(user.getEmail(), user.getUsername(), user.getPassword(), user.getFirstName(),
				user.getLastName(), user.getBirthdate());
	}
}
