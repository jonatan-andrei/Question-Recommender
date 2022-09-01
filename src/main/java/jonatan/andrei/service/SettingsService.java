package jonatan.andrei.service;

import jonatan.andrei.dto.SettingsDto;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SettingsService {

    @ConfigProperty(name = "integration.settings.default_length_questions_list_page")
    Integer defaultLengthQuestionListPage;

    @ConfigProperty(name = "recommendation.settings.number_of_days_a_question_is_relevant")
    Integer numberOfDaysQuestionIsRelevant;

    @ConfigProperty(name = "relevance.question_list_page.publication_date")
    Integer publicationDateRelevanceQuestionListPage;

    @ConfigProperty(name = "relevance.question_list_page.category_explicit_recommendation")
    Integer categoryExplicitRecommendationRelevanceQuestionListPage;

    @ConfigProperty(name = "relevance.question_list_page.tag_explicit_recommendation")
    Integer tagExplicitRecommendationRelevanceQuestionListPage;

    public SettingsDto getSettings() {
        return SettingsDto.builder()
                .defaultLengthQuestionListPage(defaultLengthQuestionListPage)
                .numberOfDaysQuestionIsRelevant(numberOfDaysQuestionIsRelevant)
                .publicationDateRelevanceQuestionListPage(publicationDateRelevanceQuestionListPage)
                .categoryExplicitRecommendationRelevanceQuestionListPage(categoryExplicitRecommendationRelevanceQuestionListPage)
                .tagExplicitRecommendationRelevanceQuestionListPage(tagExplicitRecommendationRelevanceQuestionListPage)
                .build();
    }

}
