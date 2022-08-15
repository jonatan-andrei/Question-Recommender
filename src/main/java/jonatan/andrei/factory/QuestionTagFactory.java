package jonatan.andrei.factory;

import jonatan.andrei.model.Question;
import jonatan.andrei.model.QuestionTag;
import jonatan.andrei.model.Tag;

public class QuestionTagFactory {

    public static QuestionTag newQuestionTag(Question question, Tag tag) {
        return QuestionTag.builder()
                .questionId(question.getPostId())
                .tagId(tag.getTagId())
                .build();
    }
}
