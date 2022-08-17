package jonatan.andrei.factory;

import jonatan.andrei.domain.VoteType;
import jonatan.andrei.dto.VoteRequestDto;
import jonatan.andrei.model.Vote;

import java.time.LocalDateTime;
import java.util.Optional;

public class VoteFactory {

    public static Vote newVote(VoteRequestDto voteRequestDto, Long userId, Long postId) {
        return Vote.builder()
                .postId(postId)
                .userId(userId)
                .voteType(VoteType.valueOf(voteRequestDto.getVoteType().name()))
                .voteDate(Optional.ofNullable(voteRequestDto.getVoteDate()).orElse(LocalDateTime.now()))
                .build();
    }
}
