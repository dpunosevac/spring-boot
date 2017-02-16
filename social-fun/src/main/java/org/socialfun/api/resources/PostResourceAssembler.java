package org.socialfun.api.resources;

import org.socialfun.api.impl.PostController;
import org.socialfun.domain.Comment;
import org.socialfun.domain.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RelProvider;
import org.springframework.stereotype.Service;

@Service
public class PostResourceAssembler extends EmbeddableResourceAssemblerSupport<Post, PostResource, PostController> {

	@Autowired
	private UserResourceAssembler userResourceAssembler;

	@Autowired
	private CommentResourceAssembler commentResourceAssembler;

	@Autowired
	public PostResourceAssembler(EntityLinks entityLinks, RelProvider relProvider) {
		super(entityLinks, relProvider, PostController.class, PostResource.class);
	}

	@Override
	public PostResource toResource(Post post) {
		final PostResource resource = createResourceWithId(post.getId(), post);
		final String userRel = relProvider.getItemResourceRelFor(UserResource.class);
		final String commentsRel = relProvider.getCollectionResourceRelFor(CommentResource.class);
		
		resource.add(userResourceAssembler.linkToSingleResource(post.getUser()).withRel(userRel));
		
		for (Comment comment : post.getComments()) {
			resource.add(commentResourceAssembler.linkToSingleResource(comment).withRel(commentsRel));
		}

		return resource;
	}

	@Override
	public Link linkToSingleResource(Post post) {

		return entityLinks.linkToSingleResource(PostResource.class, post.getId());
	}

	@Override
	protected PostResource instantiateResource(Post post) {

		return new PostResource(post.getContent(), post.getDate());
	}
}
