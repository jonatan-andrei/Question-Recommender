package jonatan.andrei.factory;

import jonatan.andrei.domain.PostType;
import jonatan.andrei.dto.CreatePostRequestDto;
import jonatan.andrei.dto.UpdatePostRequestDto;
import jonatan.andrei.model.QuestionComment;

import java.time.LocalDateTime;

public class QuestionCommentFactory {

    public static QuestionComment createQuestionComment(CreatePostRequestDto createPostRequestDto, Long questionId, Long userId) {
        return QuestionComment.builder()
                .questionId(questionId)
                .content(createPostRequestDto.getContentOrDescription())
                .integrationPostId(createPostRequestDto.getIntegrationPostId())
                .postType(PostType.QUESTION_COMMENT)
                .userId(userId)
                .publicationDate(createPostRequestDto.getPublicationDate())
                .updateDate(createPostRequestDto.getPublicationDate())
                .integrationDate(LocalDateTime.now())
                .hidden(false)
                .upvotes(0)
                .downvotes(0)
                .build();
    }

    public static QuestionComment overwrite(QuestionComment existingQuestionComment, UpdatePostRequestDto updatePostRequestDto) {
        existingQuestionComment.setContent(updatePostRequestDto.getContentOrDescription());
        existingQuestionComment.setUpdateDate(LocalDateTime.now());
        return existingQuestionComment;
    }
}
