package jonatan.andrei.service;

import jonatan.andrei.domain.RecommendationChannelType;
import jonatan.andrei.domain.RecommendationSettingsType;
import jonatan.andrei.dto.RecommendationSettingsRequestDto;

import javax.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class SettingsModelService {

    public List<RecommendationSettingsRequestDto> findSettings(Integer settings){
        return switch (settings) {
            case 1 -> settingsModel1();
            case 2 -> settingsModel2();
            case 3 -> settingsModel3();
            case 4 -> settingsModel4();
            case 5 -> settingsModel5();
            default -> null;
        };
    }

    private List<RecommendationSettingsRequestDto> settingsModel1() { // Default
        List<RecommendationSettingsRequestDto> recommendationSettings = new ArrayList<>();
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.NUMBER_OF_DAYS_QUESTION_IS_RECENT, BigDecimal.valueOf(7), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.NUMBER_OF_DAYS_QUESTION_IS_RELEVANT, BigDecimal.valueOf(365), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_PUBLICATION_DATE_RECENT, BigDecimal.valueOf(100), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_PUBLICATION_DATE_RELEVANT, BigDecimal.valueOf(100), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_UPDATE_DATE_RECENT, BigDecimal.valueOf(0), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_HAS_ANSWER, BigDecimal.valueOf(-30), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_PER_ANSWER, BigDecimal.valueOf(-10), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_HAS_BEST_ANSWER, BigDecimal.valueOf(-50), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_NUMBER_VIEWS, BigDecimal.valueOf(0.0001), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_NUMBER_FOLLOWERS, BigDecimal.valueOf(1), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_USER_ALREADY_ANSWERED, BigDecimal.valueOf(-50), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_USER_ALREADY_COMMENTED, BigDecimal.valueOf(-20), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.MINIMUM_OF_ACTIVITIES_TO_CONSIDER_MAXIMUM_SCORE, BigDecimal.valueOf(10), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_ASKED_IN_TAG, BigDecimal.valueOf(50), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_ANSWERED_IN_TAG, BigDecimal.valueOf(300), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_COMMENTED_IN_TAG, BigDecimal.valueOf(100), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_FOLLOWED_IN_TAG, BigDecimal.valueOf(50), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.MAXIMUM_NUMBER_OF_PAGES_WITH_RECOMMENDED_QUESTIONS, BigDecimal.valueOf(20), RecommendationChannelType.RECOMMENDED_LIST));
        return recommendationSettings;
    }

    private List<RecommendationSettingsRequestDto> settingsModel2() { // recent questions
        List<RecommendationSettingsRequestDto> recommendationSettings = new ArrayList<>();
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.NUMBER_OF_DAYS_QUESTION_IS_RECENT, BigDecimal.valueOf(3), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.NUMBER_OF_DAYS_QUESTION_IS_RELEVANT, BigDecimal.valueOf(10), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_PUBLICATION_DATE_RECENT, BigDecimal.valueOf(200), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_PUBLICATION_DATE_RELEVANT, BigDecimal.valueOf(200), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_UPDATE_DATE_RECENT, BigDecimal.valueOf(0), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_HAS_ANSWER, BigDecimal.valueOf(-30), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_PER_ANSWER, BigDecimal.valueOf(-10), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_HAS_BEST_ANSWER, BigDecimal.valueOf(-50), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_NUMBER_VIEWS, BigDecimal.valueOf(0.0001), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_NUMBER_FOLLOWERS, BigDecimal.valueOf(1), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_USER_ALREADY_ANSWERED, BigDecimal.valueOf(-50), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_USER_ALREADY_COMMENTED, BigDecimal.valueOf(-20), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.MINIMUM_OF_ACTIVITIES_TO_CONSIDER_MAXIMUM_SCORE, BigDecimal.valueOf(10), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_ASKED_IN_TAG, BigDecimal.valueOf(50), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_ANSWERED_IN_TAG, BigDecimal.valueOf(300), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_COMMENTED_IN_TAG, BigDecimal.valueOf(100), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_FOLLOWED_IN_TAG, BigDecimal.valueOf(50), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.MAXIMUM_NUMBER_OF_PAGES_WITH_RECOMMENDED_QUESTIONS, BigDecimal.valueOf(20), RecommendationChannelType.RECOMMENDED_LIST));
        return recommendationSettings;
    }

    private List<RecommendationSettingsRequestDto> settingsModel3() { // tag attributes
        List<RecommendationSettingsRequestDto> recommendationSettings = new ArrayList<>();
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.NUMBER_OF_DAYS_QUESTION_IS_RECENT, BigDecimal.valueOf(7), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.NUMBER_OF_DAYS_QUESTION_IS_RELEVANT, BigDecimal.valueOf(365), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_PUBLICATION_DATE_RECENT, BigDecimal.valueOf(100), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_PUBLICATION_DATE_RELEVANT, BigDecimal.valueOf(100), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_UPDATE_DATE_RECENT, BigDecimal.valueOf(0), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_HAS_ANSWER, BigDecimal.valueOf(-30), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_PER_ANSWER, BigDecimal.valueOf(-10), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_HAS_BEST_ANSWER, BigDecimal.valueOf(-50), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_NUMBER_VIEWS, BigDecimal.valueOf(0.0001), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_NUMBER_FOLLOWERS, BigDecimal.valueOf(1), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_USER_ALREADY_ANSWERED, BigDecimal.valueOf(-50), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_USER_ALREADY_COMMENTED, BigDecimal.valueOf(-20), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.MINIMUM_OF_ACTIVITIES_TO_CONSIDER_MAXIMUM_SCORE, BigDecimal.valueOf(10), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_ASKED_IN_TAG, BigDecimal.valueOf(150), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_ANSWERED_IN_TAG, BigDecimal.valueOf(500), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_COMMENTED_IN_TAG, BigDecimal.valueOf(250), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_FOLLOWED_IN_TAG, BigDecimal.valueOf(150), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.MAXIMUM_NUMBER_OF_PAGES_WITH_RECOMMENDED_QUESTIONS, BigDecimal.valueOf(20), RecommendationChannelType.RECOMMENDED_LIST));
        return recommendationSettings;
    }

    private List<RecommendationSettingsRequestDto> settingsModel4() { // question attributes
        List<RecommendationSettingsRequestDto> recommendationSettings = new ArrayList<>();
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.NUMBER_OF_DAYS_QUESTION_IS_RECENT, BigDecimal.valueOf(7), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.NUMBER_OF_DAYS_QUESTION_IS_RELEVANT, BigDecimal.valueOf(365), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_PUBLICATION_DATE_RECENT, BigDecimal.valueOf(100), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_PUBLICATION_DATE_RELEVANT, BigDecimal.valueOf(100), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_UPDATE_DATE_RECENT, BigDecimal.valueOf(0), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_HAS_ANSWER, BigDecimal.valueOf(0), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_PER_ANSWER, BigDecimal.valueOf(0), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_HAS_BEST_ANSWER, BigDecimal.valueOf(0), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_NUMBER_VIEWS, BigDecimal.valueOf(0.0003), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_NUMBER_FOLLOWERS, BigDecimal.valueOf(5), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_USER_ALREADY_ANSWERED, BigDecimal.valueOf(0), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_USER_ALREADY_COMMENTED, BigDecimal.valueOf(0), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.MINIMUM_OF_ACTIVITIES_TO_CONSIDER_MAXIMUM_SCORE, BigDecimal.valueOf(10), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_ASKED_IN_TAG, BigDecimal.valueOf(50), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_ANSWERED_IN_TAG, BigDecimal.valueOf(300), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_COMMENTED_IN_TAG, BigDecimal.valueOf(100), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_FOLLOWED_IN_TAG, BigDecimal.valueOf(50), RecommendationChannelType.RECOMMENDED_LIST));
        return recommendationSettings;
    }

    private List<RecommendationSettingsRequestDto> settingsModel5() { // maximum number of pages with recommended questions
        List<RecommendationSettingsRequestDto> recommendationSettings = new ArrayList<>();
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.NUMBER_OF_DAYS_QUESTION_IS_RECENT, BigDecimal.valueOf(7), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.NUMBER_OF_DAYS_QUESTION_IS_RELEVANT, BigDecimal.valueOf(365), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_PUBLICATION_DATE_RECENT, BigDecimal.valueOf(100), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_PUBLICATION_DATE_RELEVANT, BigDecimal.valueOf(100), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_UPDATE_DATE_RECENT, BigDecimal.valueOf(0), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_HAS_ANSWER, BigDecimal.valueOf(-30), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_PER_ANSWER, BigDecimal.valueOf(-10), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_HAS_BEST_ANSWER, BigDecimal.valueOf(-50), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_NUMBER_VIEWS, BigDecimal.valueOf(0.0001), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_NUMBER_FOLLOWERS, BigDecimal.valueOf(1), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_USER_ALREADY_ANSWERED, BigDecimal.valueOf(-50), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_USER_ALREADY_COMMENTED, BigDecimal.valueOf(-20), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.MINIMUM_OF_ACTIVITIES_TO_CONSIDER_MAXIMUM_SCORE, BigDecimal.valueOf(10), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_ASKED_IN_TAG, BigDecimal.valueOf(50), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_ANSWERED_IN_TAG, BigDecimal.valueOf(300), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_COMMENTED_IN_TAG, BigDecimal.valueOf(100), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_FOLLOWED_IN_TAG, BigDecimal.valueOf(50), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.MAXIMUM_NUMBER_OF_PAGES_WITH_RECOMMENDED_QUESTIONS, BigDecimal.valueOf(60), RecommendationChannelType.RECOMMENDED_LIST));
        return recommendationSettings;
    }

}
