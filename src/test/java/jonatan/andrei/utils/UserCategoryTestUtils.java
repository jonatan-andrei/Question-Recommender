package jonatan.andrei.utils;

import jonatan.andrei.factory.UserCategoryFactory;
import jonatan.andrei.model.Category;
import jonatan.andrei.model.User;
import jonatan.andrei.model.UserCategory;
import jonatan.andrei.repository.UserCategoryRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.math.BigDecimal;

@ApplicationScoped
public class UserCategoryTestUtils {

    @Inject
    UserCategoryRepository userCategoryRepository;

    public UserCategory save(UserCategory userCategory) {
        return userCategoryRepository.save(userCategory);
    }

    public UserCategory build(User user, Category category) {
        return UserCategoryFactory.newUserCategory(user.getUserId(), category.getCategoryId());
    }

    public UserCategory save(User user, Category category){
        return userCategoryRepository.save(build(user, category));
    }

    public UserCategory saveWithQuestionViews(User user, Category category, BigDecimal questionViews) {
        UserCategory userCategory = build(user, category);
        userCategory.setNumberQuestionsViewed(questionViews);
        return save(userCategory);
    }
}
