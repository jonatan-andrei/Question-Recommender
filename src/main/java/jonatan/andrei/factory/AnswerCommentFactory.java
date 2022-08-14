package jonatan.andrei.factory;

import jonatan.andrei.domain.PostType;
import jonatan.andrei.dto.CreatePostRequestDto;
import jonatan.andrei.dto.UpdatePostRequestDto;
import jonatan.andrei.model.AnswerComment;

import java.time.LocalDateTime;

public class AnswerCommentFactory {

    public static AnswerComment createAnswerComment(CreatePostRequestDto createPostRequestDto, Long answerId, Long userId) {
        return AnswerComment.builder()
                .answerId(answerId)
                .content(createPostRequestDto.getContentOrDescription())
                .integrationPostId(createPostRequestDto.getIntegrationPostId())
                .postType(PostType.ANSWER_COMMENT)
                .userId(userId)
                .publicationDate(createPostRequestDto.getPublicationDate())
                .updateDate(createPostRequestDto.getPublicationDate())
                .integrationDate(LocalDateTime.now())
                .hidden(false)
                .upvotes(0)
                .downvotes(0)
                .build();
    }

    public static AnswerComment overwrite(AnswerComment existingAnswerComment, UpdatePostRequestDto updatePostRequestDto) {
        existingAnswerComment.setContent(updatePostRequestDto.getContentOrDescription());
        existingAnswerComment.setUpdateDate(LocalDateTime.now());
        return existingAnswerComment;
    }
}
