package jonatan.andrei.utils;

import jonatan.andrei.factory.QuestionTagFactory;
import jonatan.andrei.model.Question;
import jonatan.andrei.model.Tag;
import jonatan.andrei.repository.QuestionTagRepository;
import jonatan.andrei.repository.TagRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;

@ApplicationScoped
public class QuestionTagTestUtils {

    @Inject
    QuestionTagRepository questionTagRepository;

    @Inject
    TagRepository tagRepository;

    public void saveQuestionTags(Question question, List<Tag> tags) {
        BigDecimal value = BigDecimal.ONE.divide(new BigDecimal(tags.size()));
        for (Tag tag : tags) {
            tag.setNumberQuestionsAsked(tag.getNumberQuestionsAsked().add(value));
            tagRepository.save(tag);
            questionTagRepository.save(QuestionTagFactory.newQuestionTag(question, tag));
        }
    }
}
