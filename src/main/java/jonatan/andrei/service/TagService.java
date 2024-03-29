package jonatan.andrei.service;

import jonatan.andrei.domain.PostClassificationType;
import jonatan.andrei.domain.UserActionType;
import jonatan.andrei.domain.UserActionUpdateType;
import jonatan.andrei.dto.TagRequestDto;
import jonatan.andrei.exception.RequiredDataException;
import jonatan.andrei.factory.TagFactory;
import jonatan.andrei.model.QuestionTag;
import jonatan.andrei.model.Tag;
import jonatan.andrei.repository.TagRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@ApplicationScoped
public class TagService {

    @Inject
    TagRepository tagRepository;

    @Inject
    TotalActivitySystemService totalActivitySystemService;

    @Transactional
    public Tag saveOrUpdate(TagRequestDto tagRequestDto) {
        validateRequiredData(tagRequestDto);
        Tag existingTag = tagRepository.findByName(tagRequestDto.getName());
        if (isNull(existingTag)) {
            return tagRepository.save(TagFactory.newTag(tagRequestDto));
        } else {
            return tagRepository.save(TagFactory.overwrite(existingTag, tagRequestDto));
        }

    }

    @Transactional
    public List<Tag> saveOrUpdate(List<TagRequestDto> tags) {
        return tags.stream().map(tag -> saveOrUpdate(tag)).collect(Collectors.toList());
    }

    public List<Tag> findOrCreateTags(List<String> tagsName) {
        List<Tag> tags = tagRepository.findByNameIn(tagsName);
        tagsName.forEach(tag -> {
            Optional<Tag> foundTag = tags.stream().filter(t -> t.getName().equals(tag))
                    .findFirst();
            if (foundTag.isEmpty()) {
                tags.add(tagRepository.save(TagFactory.newTag(tag)));
            }
        });
        return tags;
    }

    public void updateNumberQuestionsByAction(List<QuestionTag> questionTags, UserActionType userActionType, UserActionUpdateType userActionUpdateType, BigDecimal value) {
        List<Long> tagsIds = questionTags.stream().map(QuestionTag::getTagId).collect(Collectors.toList());
        List<Tag> tags = tagRepository.findByTagIdIn(tagsIds);
        for (Tag tag : tags) {
            switch (userActionType) {
                case QUESTION_ASKED ->
                        tag.setNumberQuestionsAsked(tag.getNumberQuestionsAsked().add(value));
                case QUESTION_VIEWED ->
                        tag.setNumberQuestionsViewed(tag.getNumberQuestionsViewed().add(value));
                case QUESTION_ANSWERED ->
                        tag.setNumberQuestionsAnswered(tag.getNumberQuestionsAnswered().add(value));
                case QUESTION_COMMENTED ->
                        tag.setNumberQuestionsCommented(tag.getNumberQuestionsCommented().add(value));
                case QUESTION_FOLLOWED ->
                        tag.setNumberQuestionsFollowed(tag.getNumberQuestionsFollowed().add(value));
                case QUESTION_UPVOTED ->
                        tag.setNumberQuestionsUpvoted(tag.getNumberQuestionsUpvoted().add(value));
                case QUESTION_DOWNVOTED ->
                        tag.setNumberQuestionsDownvoted(tag.getNumberQuestionsDownvoted().add(value));
                case ANSWER_UPVOTED ->
                        tag.setNumberAnswersUpvoted(tag.getNumberAnswersUpvoted().add(value));
                case ANSWER_DOWNVOTED ->
                        tag.setNumberAnswersDownvoted(tag.getNumberAnswersDownvoted().add(value));
                case COMMENT_UPVOTED ->
                        tag.setNumberCommentsUpvoted(tag.getNumberCommentsUpvoted().add(value));
                case COMMENT_DOWNVOTED ->
                        tag.setNumberCommentsDownvoted(tag.getNumberCommentsDownvoted().add(value));
            }
        }
        tagRepository.saveAll(tags);
        totalActivitySystemService.updateNumberByAction(PostClassificationType.TAG, userActionType, userActionUpdateType.getValue());
    }

    private void validateRequiredData(TagRequestDto tagRequestDto) {
        if (isNull(tagRequestDto.getName())) {
            throw new RequiredDataException("Attribute 'name' is required");
        }
    }

    public void clear() {
        tagRepository.deleteAll();
    }

}
