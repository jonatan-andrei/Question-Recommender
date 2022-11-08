package jonatan.andrei.factory;

import jonatan.andrei.domain.PostType;
import jonatan.andrei.dto.CreatePostRequestDto;
import jonatan.andrei.dto.UpdatePostRequestDto;
import jonatan.andrei.model.Question;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class QuestionFactory {

    public static Question newQuestion(CreatePostRequestDto createPostRequestDto, Long userId) {
        return Question.builder()
                .integrationPostId(createPostRequestDto.getIntegrationPostId())
                .postType(PostType.QUESTION)
                .userId(userId)
                .publicationDate(createPostRequestDto.getPublicationDate())
                .updateDate(createPostRequestDto.getPublicationDate())
                .integrationDate(LocalDateTime.now())
                .hidden(false)
                .upvotes(0)
                .downvotes(0)
                .title(createPostRequestDto.getTitle())
                .description(createPostRequestDto.getContentOrDescription())
                .followers(0)
                .url(createPostRequestDto.getUrl())
                .views(0)
                .answers(0)
                .tags(String.join(";", createPostRequestDto.getTags()))
                .numberTags(BigDecimal.valueOf(Optional.ofNullable(createPostRequestDto.getTags()).map(List::size).orElse(0)))
                .numberCategories(BigDecimal.valueOf(Optional.ofNullable(createPostRequestDto.getIntegrationCategoriesIds()).map(List::size).orElse(0)))
                .build();
    }

    public static Question overwrite(Question existingQuestion, UpdatePostRequestDto updatePostRequestDto) {
        existingQuestion.setUpdateDate(LocalDateTime.now());
        existingQuestion.setTitle(updatePostRequestDto.getTitle());
        existingQuestion.setDescription(updatePostRequestDto.getContentOrDescription());
        existingQuestion.setUrl(updatePostRequestDto.getUrl());
        existingQuestion.setTags(String.join(";", updatePostRequestDto.getTags()));
        existingQuestion.setNumberTags(BigDecimal.valueOf(Optional.ofNullable(updatePostRequestDto.getTags()).map(List::size).orElse(0)));
        existingQuestion.setNumberCategories(BigDecimal.valueOf(Optional.ofNullable(updatePostRequestDto.getIntegrationCategoriesIds()).map(List::size).orElse(0)));
        return existingQuestion;
    }
}
