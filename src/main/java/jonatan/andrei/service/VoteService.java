package jonatan.andrei.service;

import jonatan.andrei.domain.UserActionUpdateType;
import jonatan.andrei.domain.VoteType;
import jonatan.andrei.domain.VoteTypeRequest;
import jonatan.andrei.dto.VoteRequestDto;
import jonatan.andrei.exception.RequiredDataException;
import jonatan.andrei.factory.VoteFactory;
import jonatan.andrei.model.*;
import jonatan.andrei.repository.VoteRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@ApplicationScoped
public class VoteService {

    @Inject
    VoteRepository voteRepository;

    @Inject
    UserCategoryService userCategoryService;

    @Inject
    UserTagService userTagService;

    @Inject
    UserService userService;

    public Post registerVote(VoteRequestDto voteRequestDto, User user, Post post, List<QuestionCategory> questionCategories, List<QuestionTag> questionTags) {
        Optional<Vote> existingVote = findByUserIdAndPostId(user, post);
        if (existingVote.isPresent()) {
            removeExistingVote(existingVote.get(), post, user, questionCategories, questionTags);
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
        userCategoryService.updateNumberQuestionsVoted(user, post, questionCategories, UserActionUpdateType.INCREASE, vote.getVoteType().equals(VoteType.UPVOTE));
        userTagService.updateNumberQuestionsVoted(user, post, questionTags, UserActionUpdateType.INCREASE, vote.getVoteType().equals(VoteType.UPVOTE));
        userService.updateVotesByActionAndPostType(user, UserActionUpdateType.INCREASE, post.getPostType(), vote.getVoteType().equals(VoteType.UPVOTE));
        return post;
    }

    public Optional<Vote> findByUserIdAndPostId(User user, Post post) {
        return voteRepository.findByUserIdAndPostId(user.getUserId(), post.getPostId());
    }

    public void removeExistingVote(Vote existingVote, Post post, User user, List<QuestionCategory> questionCategories, List<QuestionTag> questionTags) {
        if (existingVote.getVoteType().equals(VoteType.UPVOTE)) {
            post.setUpvotes(post.getUpvotes() - 1);
        } else {
            post.setDownvotes(post.getDownvotes() - 1);
        }
        voteRepository.delete(existingVote);
        userCategoryService.updateNumberQuestionsVoted(user, post, questionCategories, UserActionUpdateType.DECREASE, existingVote.getVoteType().equals(VoteType.UPVOTE));
        userTagService.updateNumberQuestionsVoted(user, post, questionTags, UserActionUpdateType.DECREASE, existingVote.getVoteType().equals(VoteType.UPVOTE));
        userService.updateVotesByActionAndPostType(user, UserActionUpdateType.DECREASE, post.getPostType(), existingVote.getVoteType().equals(VoteType.UPVOTE));
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

    public void clear() {
        voteRepository.deleteAll();
    }
}
