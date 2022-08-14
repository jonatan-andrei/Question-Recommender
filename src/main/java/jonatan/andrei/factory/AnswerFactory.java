package jonatan.andrei.factory;

import jonatan.andrei.domain.PostType;
import jonatan.andrei.dto.CreatePostRequestDto;
import jonatan.andrei.dto.UpdatePostRequestDto;
import jonatan.andrei.model.Answer;

import java.time.LocalDateTime;

public class AnswerFactory {

    public static Answer createAnswer(CreatePostRequestDto createPostRequestDto, Long questionId, Long userId) {
        return Answer.builder()
                .questionId(questionId)
                .content(createPostRequestDto.getContentOrDescription())
                .bestAnswer(false)
                .integrationPostId(createPostRequestDto.getIntegrationPostId())
                .postType(PostType.ANSWER)
                .userId(userId)
                .publicationDate(createPostRequestDto.getPublicationDate())
                .updateDate(createPostRequestDto.getPublicationDate())
                .integrationDate(LocalDateTime.now())
                .hidden(false)
                .upvotes(0)
                .downvotes(0)
                .build();
    }

    public static Answer overwrite(Answer existingAnswer, UpdatePostRequestDto updatePostRequestDto) {
        existingAnswer.setContent(updatePostRequestDto.getContentOrDescription());
        existingAnswer.setUpdateDate(LocalDateTime.now());
        return existingAnswer;
    }
}
