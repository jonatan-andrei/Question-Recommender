package jonatan.andrei.service;

import jonatan.andrei.domain.QuestionViewType;
import jonatan.andrei.factory.QuestionViewFactory;
import jonatan.andrei.model.Question;
import jonatan.andrei.model.QuestionView;
import jonatan.andrei.model.User;
import jonatan.andrei.repository.QuestionViewRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class QuestionViewService {

    @Inject
    QuestionViewRepository questionViewRepository;

    public void registerQuestionViews(Question question, List<User> users, QuestionViewType questionViewType) {
        List<QuestionView> questionViews = new ArrayList<>();
        List<Long> usersIds = users.stream().map(User::getUserId).collect(Collectors.toList());
        List<QuestionView> existingQuestionViews = questionViewRepository.findByQuestionIdAndUserIdIn(question.getPostId(), usersIds);
        for (User user : users) {
            QuestionView questionView = existingQuestionViews.stream().filter(qv -> qv.getUserId().equals(user.getUserId()))
                    .findFirst().orElseGet(() -> QuestionViewFactory.newQuestionView(question.getPostId(), user.getUserId()));
            increaseByType(questionView, questionViewType);
            questionViews.add(questionView);
        }
        questionViewRepository.saveAll(questionViews);
    }

    public void registerQuestionsViewInList(List<Long> questions, Long userId, QuestionViewType questionViewType) {
        List<QuestionView> questionViews = new ArrayList<>();
        List<QuestionView> existingQuestionViews = questionViewRepository.findByUserIdAndQuestionIdIn(userId, questions);
        for (Long questionId : questions) {
            QuestionView questionView = existingQuestionViews.stream().filter(qv -> qv.getQuestionId().equals(questionId))
                    .findFirst().orElseGet(() -> QuestionViewFactory.newQuestionView(questionId, userId));
            increaseByType(questionView, questionViewType);
            questionViews.add(questionView);
        }
        questionViewRepository.saveAll(questionViews);
    }

    private QuestionView increaseByType(QuestionView questionView, QuestionViewType questionViewType) {
        switch (questionViewType) {
            case VIEW -> questionView.setNumberOfViews(questionView.getNumberOfViews() + 1);
            case VIEW_IN_RECOMMENDED_LIST ->
                    questionView.setNumberOfRecommendationsInList(questionView.getNumberOfRecommendationsInList() + 1);
            case VIEW_IN_RECOMMENDED_EMAIL ->
                    questionView.setNumberOfRecommendationsInEmail(questionView.getNumberOfRecommendationsInEmail()
                            + 1);
            case VIEW_IN_NOTIFICATION -> questionView.setRecommendedInNotification(true);
        }

        return questionView;
    }

    public void clear() {
        questionViewRepository.deleteAll();
    }
}
