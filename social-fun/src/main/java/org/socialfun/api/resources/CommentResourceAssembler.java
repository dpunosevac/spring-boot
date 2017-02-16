package org.socialfun.api.resources;


import org.socialfun.api.impl.CommentController;
import org.socialfun.domain.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RelProvider;
import org.springframework.stereotype.Service;

@Service
public class CommentResourceAssembler
		extends EmbeddableResourceAssemblerSupport<Comment, CommentResource, CommentController> {

	@Autowired
	private UserResourceAssembler userResourceAssembler;

	@Autowired
	private PostResourceAssembler postResourceAssembler;
	
	@Autowired
	public CommentResourceAssembler(EntityLinks entityLinks, RelProvider relProvider) {
		super(entityLinks, relProvider, CommentController.class, CommentResource.class);
	}

	@Override
	public CommentResource toResource(Comment comment) {
		final CommentResource resource = createResourceWithId(comment.getId(), comment);
		final String userRel = relProvider.getItemResourceRelFor(UserResource.class);
		final String postRel = relProvider.getItemResourceRelFor(PostResource.class);
		
		resource.add(userResourceAssembler.linkToSingleResource(comment.getUser()).withRel(userRel));
		resource.add(postResourceAssembler.linkToSingleResource(comment.getPost()).withRel(postRel));
		
		return resource;
	}

	@Override
	public Link linkToSingleResource(Comment comment) {

		return entityLinks.linkToSingleResource(CommentResource.class, comment.getId());
	}

	@Override
	protected CommentResource instantiateResource(Comment comment) {

		return new CommentResource(comment.getContent(), comment.getDate());
	}
}
