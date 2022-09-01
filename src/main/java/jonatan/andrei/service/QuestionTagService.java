package jonatan.andrei.service;

import jonatan.andrei.domain.UserActionType;
import jonatan.andrei.domain.UserActionUpdateType;
import jonatan.andrei.factory.QuestionTagFactory;
import jonatan.andrei.model.Question;
import jonatan.andrei.model.QuestionTag;
import jonatan.andrei.model.Tag;
import jonatan.andrei.model.User;
import jonatan.andrei.repository.QuestionTagRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class QuestionTagService {

    @Inject
    QuestionTagRepository questionTagRepository;

    @Inject
    UserTagService userTagService;

    @Inject
    TagService tagService;

    public List<QuestionTag> findByQuestionId(Long questionId) {
        return questionTagRepository.findByQuestionId(questionId);
    }

    public void save(Question question, List<String> tagsName, User user) {
        List<QuestionTag> existingQuestionTags = questionTagRepository.findByQuestionId(question.getPostId());
        List<Tag> tags = tagService.findOrCreateTags(Optional.ofNullable(tagsName).orElse(Collections.EMPTY_LIST));

        List<Long> existingQuestionTagsIds = existingQuestionTags.stream()
                .map(QuestionTag::getTagId)
                .collect(Collectors.toList());
        List<QuestionTag> questionTagsToInsert = tags.stream()
                .filter(t -> !existingQuestionTagsIds.contains(t.getTagId()))
                .map(t -> QuestionTagFactory.newQuestionTag(question, t))
                .collect(Collectors.toList());
        questionTagRepository.saveAll(questionTagsToInsert);
        incrementQuestionCountByQuestionTags(questionTagsToInsert, user);

        List<Long> tagsIds = tags.stream().map(Tag::getTagId).collect(Collectors.toList());
        List<QuestionTag> questionTagsToDelete = existingQuestionTags.stream()
                .filter(qt -> !tagsIds.contains(qt.getTagId()))
                .collect(Collectors.toList());
        questionTagRepository.deleteAll(questionTagsToDelete);
        decrementQuestionCountByQuestionTags(questionTagsToDelete, user);
    }

    private void incrementQuestionCountByQuestionTags(List<QuestionTag> questionTags, User user) {
        if (!questionTags.isEmpty()) {
            tagService.incrementQuestionCountByTagsIds(questionTags.stream()
                    .map(QuestionTag::getTagId).collect(Collectors.toList()));
            userTagService.updateNumberQuestionsByAction(user, questionTags, UserActionType.QUESTION_ASKED, UserActionUpdateType.INCREASE);
        }
    }

    private void decrementQuestionCountByQuestionTags(List<QuestionTag> questionTags, User user) {
        if (!questionTags.isEmpty()) {
            tagService.decrementQuestionCountByTagsIds(questionTags.stream()
                    .map(QuestionTag::getTagId).collect(Collectors.toList()));
            userTagService.updateNumberQuestionsByAction(user, questionTags, UserActionType.QUESTION_ASKED, UserActionUpdateType.DECREASE);
        }
    }

    public void clear() {
        questionTagRepository.deleteAll();
    }
}
