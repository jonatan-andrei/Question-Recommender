package jonatan.andrei.service;

import jonatan.andrei.domain.VoteType;
import jonatan.andrei.domain.VoteTypeRequest;
import jonatan.andrei.dto.VoteRequestDto;
import jonatan.andrei.exception.RequiredDataException;
import jonatan.andrei.factory.VoteFactory;
import jonatan.andrei.model.Post;
import jonatan.andrei.model.User;
import jonatan.andrei.model.Vote;
import jonatan.andrei.repository.VoteRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Optional;

import static java.util.Objects.isNull;

@ApplicationScoped
public class VoteService {

    @Inject
    VoteRepository voteRepository;

    public Post registerVote(VoteRequestDto voteRequestDto, User user, Post post) {
        Optional<Vote> existingVote = findByUserIdAndPostId(user, post);
        if (existingVote.isPresent()) {
            removeExistingVote(existingVote.get(), post);
        }
        if (voteRequestDto.getVoteType().equals(VoteTypeRequest.REMOVED)) {
            return post;
        }
        Vote vote = voteRepository.save(VoteFactory.newVote(voteRequestDto, user.getUserId(), post.getPostId()));
        if (vote.getVoteType().equals(VoteType.UPVOTE)) {
            post.setUpvotes(post.getUpvotes() + 1);
        } else {
            post.setDownvotes(post.getDownvotes() + 1);
        }
        return post;
    }

    public Optional<Vote> findByUserIdAndPostId(User user, Post post) {
        return voteRepository.findByUserIdAndPostId(user.getUserId(), post.getPostId());
    }

    public void removeExistingVote(Vote existingVote, Post post) {
        if (existingVote.getVoteType().equals(VoteType.UPVOTE)) {
            post.setUpvotes(post.getUpvotes() - 1);
        } else {
            post.setDownvotes(post.getDownvotes() - 1);
        }
        voteRepository.delete(existingVote);
    }

    public void validateVoteRequest(VoteRequestDto voteRequestDto) {
        if (isNull(voteRequestDto.getVoteType())) {
            throw new RequiredDataException("Attribute 'voteType' is required");
        }
        if (isNull(voteRequestDto.getIntegrationPostId())) {
            throw new RequiredDataException("Attribute 'integrationPostId' is required");
        }
        if (isNull(voteRequestDto.getIntegrationUserId())) {
            throw new RequiredDataException("Attribute 'integrationUserId' is required");
        }
    }
}
