package jonatan.andrei.service;

import jonatan.andrei.factory.UserCategoryFactory;
import jonatan.andrei.model.QuestionCategory;
import jonatan.andrei.model.User;
import jonatan.andrei.model.UserCategory;
import jonatan.andrei.repository.UserCategoryRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@ApplicationScoped
public class UserCategoryService {

    @Inject
    UserCategoryRepository userCategoryRepository;

    public void updateQuestionViews(List<User> users, List<QuestionCategory> categories) {
        List<Long> categoriesIds = categories.stream().map(QuestionCategory::getCategoryId).collect(Collectors.toList());
        for (User user : users) {
            List<UserCategory> userCategories = userCategoryRepository.findByUserIdAndCategoryIdIn(user.getUserId(), categoriesIds);
            for (Long category : categoriesIds) {
                UserCategory userCategory = findOrCreateUserCategory(userCategories, user.getUserId(), category);
                userCategory.setNumberQuestionsViewed(userCategory.getNumberQuestionsViewed() + 1);
            }
            userCategoryRepository.saveAll(userCategories);
        }
    }

    private UserCategory findOrCreateUserCategory(List<UserCategory> userCategories, Long userId, Long categoryId) {
        UserCategory userCategory = userCategories.stream().filter(
                uc -> uc.getCategoryId().equals(categoryId)).findFirst().orElse(null);
        if (isNull(userCategory)) {
            userCategory = UserCategoryFactory.newUserCategory(userId, categoryId);
            userCategories.add(userCategory);
        }
        return userCategory;
    }
}
