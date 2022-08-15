package jonatan.andrei.service;

import jonatan.andrei.model.Question;
import jonatan.andrei.model.QuestionTag;
import jonatan.andrei.repository.QuestionTagRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class QuestionTagService {

    @Inject
    QuestionTagRepository questionTagRepository;

    public List<QuestionTag> findByQuestionId(Long questionId) {
        return questionTagRepository.findByQuestionId(questionId);
    }

    public void save(Question question, List<String> tags) {

    }
}
