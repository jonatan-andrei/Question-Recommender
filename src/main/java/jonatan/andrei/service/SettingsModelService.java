package jonatan.andrei.service;

import jonatan.andrei.domain.RecommendationChannelType;
import jonatan.andrei.domain.RecommendationSettingsType;
import jonatan.andrei.domain.SettingsModelType;
import jonatan.andrei.dto.RecommendationSettingsRequestDto;

import javax.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class SettingsModelService {

    public List<RecommendationSettingsRequestDto> findSettings(SettingsModelType settings) {
        return switch (settings) {
            case A -> settingsModelA();
            case B -> settingsModelB();
            case C -> settingsModelC();
            case D -> settingsModelD();
            case E -> settingsModelE();
            case F -> settingsModelF();
            case G -> settingsModelG();
            case H -> settingsModelH();
            case I -> settingsModelI();
            case J -> settingsModelJ();
            case K -> settingsModelK();
        };
    }

    private List<RecommendationSettingsRequestDto> settingsModelA() {
        List<RecommendationSettingsRequestDto> recommendationSettings = new ArrayList<>();
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.NUMBER_OF_DAYS_QUESTION_IS_RECENT, BigDecimal.valueOf(7), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.NUMBER_OF_DAYS_QUESTION_IS_RELEVANT, BigDecimal.valueOf(365), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_PUBLICATION_DATE_RECENT, BigDecimal.valueOf(0), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_PUBLICATION_DATE_RELEVANT, BigDecimal.valueOf(0), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_HAS_ANSWER, BigDecimal.valueOf(0), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_PER_ANSWER, BigDecimal.valueOf(0), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_NUMBER_FOLLOWERS, BigDecimal.valueOf(0), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_USER_ALREADY_ANSWERED, BigDecimal.valueOf(0), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_USER_ALREADY_COMMENTED, BigDecimal.valueOf(0), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.MINIMUM_OF_ACTIVITIES_TO_CONSIDER_MAXIMUM_SCORE, BigDecimal.valueOf(20), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_ASKED_IN_TAG, BigDecimal.valueOf(0), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_ANSWERED_IN_TAG, BigDecimal.valueOf(0), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_COMMENTED_IN_TAG, BigDecimal.valueOf(0), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_FOLLOWED_IN_TAG, BigDecimal.valueOf(0), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.MAXIMUM_NUMBER_OF_PAGES_WITH_RECOMMENDED_QUESTIONS, BigDecimal.valueOf(1), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RECOMMENDATION_ALGORITHM_FOR_CATEGORY_OR_TAG, BigDecimal.valueOf(1), RecommendationChannelType.RECOMMENDED_LIST));
        return recommendationSettings;
    }

    private List<RecommendationSettingsRequestDto> settingsModelB() {
        List<RecommendationSettingsRequestDto> recommendationSettings = new ArrayList<>();
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.NUMBER_OF_DAYS_QUESTION_IS_RECENT, BigDecimal.valueOf(14), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.NUMBER_OF_DAYS_QUESTION_IS_RELEVANT, BigDecimal.valueOf(365), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_PUBLICATION_DATE_RECENT, BigDecimal.valueOf(300), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_PUBLICATION_DATE_RELEVANT, BigDecimal.valueOf(100), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_HAS_ANSWER, BigDecimal.valueOf(-200), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_PER_ANSWER, BigDecimal.valueOf(-200), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_NUMBER_FOLLOWERS, BigDecimal.valueOf(1), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_USER_ALREADY_ANSWERED, BigDecimal.valueOf(-400), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_USER_ALREADY_COMMENTED, BigDecimal.valueOf(100), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.MINIMUM_OF_ACTIVITIES_TO_CONSIDER_MAXIMUM_SCORE, BigDecimal.valueOf(20), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_ASKED_IN_TAG, BigDecimal.valueOf(100), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_ANSWERED_IN_TAG, BigDecimal.valueOf(1800), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_COMMENTED_IN_TAG, BigDecimal.valueOf(900), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_FOLLOWED_IN_TAG, BigDecimal.valueOf(100), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.MAXIMUM_NUMBER_OF_PAGES_WITH_RECOMMENDED_QUESTIONS, BigDecimal.valueOf(80), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RECOMMENDATION_ALGORITHM_FOR_CATEGORY_OR_TAG, BigDecimal.valueOf(1), RecommendationChannelType.RECOMMENDED_LIST));
        return recommendationSettings;
    }

    private List<RecommendationSettingsRequestDto> settingsModelC() {
        List<RecommendationSettingsRequestDto> recommendationSettings = new ArrayList<>();
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.NUMBER_OF_DAYS_QUESTION_IS_RECENT, BigDecimal.valueOf(21), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.NUMBER_OF_DAYS_QUESTION_IS_RELEVANT, BigDecimal.valueOf(365), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_PUBLICATION_DATE_RECENT, BigDecimal.valueOf(300), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_PUBLICATION_DATE_RELEVANT, BigDecimal.valueOf(100), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_HAS_ANSWER, BigDecimal.valueOf(-200), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_PER_ANSWER, BigDecimal.valueOf(-200), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_NUMBER_FOLLOWERS, BigDecimal.valueOf(1), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_USER_ALREADY_ANSWERED, BigDecimal.valueOf(-400), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_USER_ALREADY_COMMENTED, BigDecimal.valueOf(100), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.MINIMUM_OF_ACTIVITIES_TO_CONSIDER_MAXIMUM_SCORE, BigDecimal.valueOf(20), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_ASKED_IN_TAG, BigDecimal.valueOf(100), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_ANSWERED_IN_TAG, BigDecimal.valueOf(1800), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_COMMENTED_IN_TAG, BigDecimal.valueOf(900), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_FOLLOWED_IN_TAG, BigDecimal.valueOf(100), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.MAXIMUM_NUMBER_OF_PAGES_WITH_RECOMMENDED_QUESTIONS, BigDecimal.valueOf(80), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RECOMMENDATION_ALGORITHM_FOR_CATEGORY_OR_TAG, BigDecimal.valueOf(1), RecommendationChannelType.RECOMMENDED_LIST));
        return recommendationSettings;
    }

    private List<RecommendationSettingsRequestDto> settingsModelD() {
        List<RecommendationSettingsRequestDto> recommendationSettings = new ArrayList<>();
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.NUMBER_OF_DAYS_QUESTION_IS_RECENT, BigDecimal.valueOf(14), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.NUMBER_OF_DAYS_QUESTION_IS_RELEVANT, BigDecimal.valueOf(365), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_PUBLICATION_DATE_RECENT, BigDecimal.valueOf(300), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_PUBLICATION_DATE_RELEVANT, BigDecimal.valueOf(100), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_HAS_ANSWER, BigDecimal.valueOf(0), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_PER_ANSWER, BigDecimal.valueOf(-200), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_NUMBER_FOLLOWERS, BigDecimal.valueOf(1), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_USER_ALREADY_ANSWERED, BigDecimal.valueOf(-400), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_USER_ALREADY_COMMENTED, BigDecimal.valueOf(100), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.MINIMUM_OF_ACTIVITIES_TO_CONSIDER_MAXIMUM_SCORE, BigDecimal.valueOf(20), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_ASKED_IN_TAG, BigDecimal.valueOf(500), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_ANSWERED_IN_TAG, BigDecimal.valueOf(5000), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_COMMENTED_IN_TAG, BigDecimal.valueOf(2000), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_FOLLOWED_IN_TAG, BigDecimal.valueOf(500), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.MAXIMUM_NUMBER_OF_PAGES_WITH_RECOMMENDED_QUESTIONS, BigDecimal.valueOf(80), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RECOMMENDATION_ALGORITHM_FOR_CATEGORY_OR_TAG, BigDecimal.valueOf(1), RecommendationChannelType.RECOMMENDED_LIST));
        return recommendationSettings;
    }

    private List<RecommendationSettingsRequestDto> settingsModelE() {
        List<RecommendationSettingsRequestDto> recommendationSettings = new ArrayList<>();
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.NUMBER_OF_DAYS_QUESTION_IS_RECENT, BigDecimal.valueOf(14), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.NUMBER_OF_DAYS_QUESTION_IS_RELEVANT, BigDecimal.valueOf(365), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_PUBLICATION_DATE_RECENT, BigDecimal.valueOf(300), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_PUBLICATION_DATE_RELEVANT, BigDecimal.valueOf(100), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_HAS_ANSWER, BigDecimal.valueOf(0), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_PER_ANSWER, BigDecimal.valueOf(-200), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_NUMBER_FOLLOWERS, BigDecimal.valueOf(1), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_USER_ALREADY_ANSWERED, BigDecimal.valueOf(-400), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_USER_ALREADY_COMMENTED, BigDecimal.valueOf(100), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.MINIMUM_OF_ACTIVITIES_TO_CONSIDER_MAXIMUM_SCORE, BigDecimal.valueOf(20), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_ASKED_IN_TAG, BigDecimal.valueOf(500), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_ANSWERED_IN_TAG, BigDecimal.valueOf(10000), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_COMMENTED_IN_TAG, BigDecimal.valueOf(4000), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_FOLLOWED_IN_TAG, BigDecimal.valueOf(500), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.MAXIMUM_NUMBER_OF_PAGES_WITH_RECOMMENDED_QUESTIONS, BigDecimal.valueOf(80), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RECOMMENDATION_ALGORITHM_FOR_CATEGORY_OR_TAG, BigDecimal.valueOf(1), RecommendationChannelType.RECOMMENDED_LIST));
        return recommendationSettings;
    }

//    private List<RecommendationSettingsRequestDto> settingsModelDOriginal() {
//        List<RecommendationSettingsRequestDto> recommendationSettings = new ArrayList<>();
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.NUMBER_OF_DAYS_QUESTION_IS_RECENT, BigDecimal.valueOf(14), RecommendationChannelType.RECOMMENDED_LIST));
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.NUMBER_OF_DAYS_QUESTION_IS_RELEVANT, BigDecimal.valueOf(365), RecommendationChannelType.RECOMMENDED_LIST));
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_PUBLICATION_DATE_RECENT, BigDecimal.valueOf(300), RecommendationChannelType.RECOMMENDED_LIST));
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_PUBLICATION_DATE_RELEVANT, BigDecimal.valueOf(100), RecommendationChannelType.RECOMMENDED_LIST));
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_HAS_ANSWER, BigDecimal.valueOf(0), RecommendationChannelType.RECOMMENDED_LIST));
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_PER_ANSWER, BigDecimal.valueOf(-100), RecommendationChannelType.RECOMMENDED_LIST));
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_NUMBER_FOLLOWERS, BigDecimal.valueOf(10), RecommendationChannelType.RECOMMENDED_LIST));
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_USER_ALREADY_ANSWERED, BigDecimal.valueOf(-400), RecommendationChannelType.RECOMMENDED_LIST));
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_USER_ALREADY_COMMENTED, BigDecimal.valueOf(100), RecommendationChannelType.RECOMMENDED_LIST));
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.MINIMUM_OF_ACTIVITIES_TO_CONSIDER_MAXIMUM_SCORE, BigDecimal.valueOf(20), RecommendationChannelType.RECOMMENDED_LIST));
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_ASKED_IN_TAG, BigDecimal.valueOf(100), RecommendationChannelType.RECOMMENDED_LIST));
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_ANSWERED_IN_TAG, BigDecimal.valueOf(1800), RecommendationChannelType.RECOMMENDED_LIST));
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_COMMENTED_IN_TAG, BigDecimal.valueOf(900), RecommendationChannelType.RECOMMENDED_LIST));
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_FOLLOWED_IN_TAG, BigDecimal.valueOf(100), RecommendationChannelType.RECOMMENDED_LIST));
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.MAXIMUM_NUMBER_OF_PAGES_WITH_RECOMMENDED_QUESTIONS, BigDecimal.valueOf(80), RecommendationChannelType.RECOMMENDED_LIST));
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RECOMMENDATION_ALGORITHM_FOR_CATEGORY_OR_TAG, BigDecimal.valueOf(1), RecommendationChannelType.RECOMMENDED_LIST));
//        return recommendationSettings;
//    }

//    private List<RecommendationSettingsRequestDto> settingsModelEOriginal() {
//        List<RecommendationSettingsRequestDto> recommendationSettings = new ArrayList<>();
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.NUMBER_OF_DAYS_QUESTION_IS_RECENT, BigDecimal.valueOf(14), RecommendationChannelType.RECOMMENDED_LIST));
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.NUMBER_OF_DAYS_QUESTION_IS_RELEVANT, BigDecimal.valueOf(365), RecommendationChannelType.RECOMMENDED_LIST));
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_PUBLICATION_DATE_RECENT, BigDecimal.valueOf(300), RecommendationChannelType.RECOMMENDED_LIST));
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_PUBLICATION_DATE_RELEVANT, BigDecimal.valueOf(100), RecommendationChannelType.RECOMMENDED_LIST));
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_HAS_ANSWER, BigDecimal.valueOf(-200), RecommendationChannelType.RECOMMENDED_LIST));
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_PER_ANSWER, BigDecimal.valueOf(-200), RecommendationChannelType.RECOMMENDED_LIST));
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_NUMBER_FOLLOWERS, BigDecimal.valueOf(1), RecommendationChannelType.RECOMMENDED_LIST));
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_USER_ALREADY_ANSWERED, BigDecimal.valueOf(-400), RecommendationChannelType.RECOMMENDED_LIST));
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_USER_ALREADY_COMMENTED, BigDecimal.valueOf(100), RecommendationChannelType.RECOMMENDED_LIST));
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.MINIMUM_OF_ACTIVITIES_TO_CONSIDER_MAXIMUM_SCORE, BigDecimal.valueOf(10), RecommendationChannelType.RECOMMENDED_LIST));
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_ASKED_IN_TAG, BigDecimal.valueOf(100), RecommendationChannelType.RECOMMENDED_LIST));
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_ANSWERED_IN_TAG, BigDecimal.valueOf(1800), RecommendationChannelType.RECOMMENDED_LIST));
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_COMMENTED_IN_TAG, BigDecimal.valueOf(900), RecommendationChannelType.RECOMMENDED_LIST));
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_FOLLOWED_IN_TAG, BigDecimal.valueOf(100), RecommendationChannelType.RECOMMENDED_LIST));
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.MAXIMUM_NUMBER_OF_PAGES_WITH_RECOMMENDED_QUESTIONS, BigDecimal.valueOf(160), RecommendationChannelType.RECOMMENDED_LIST));
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RECOMMENDATION_ALGORITHM_FOR_CATEGORY_OR_TAG, BigDecimal.valueOf(1), RecommendationChannelType.RECOMMENDED_LIST));
//        return recommendationSettings;
//    }

    private List<RecommendationSettingsRequestDto> settingsModelF() {
        List<RecommendationSettingsRequestDto> recommendationSettings = new ArrayList<>();
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.NUMBER_OF_DAYS_QUESTION_IS_RECENT, BigDecimal.valueOf(14), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.NUMBER_OF_DAYS_QUESTION_IS_RELEVANT, BigDecimal.valueOf(365), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_PUBLICATION_DATE_RECENT, BigDecimal.valueOf(300), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_PUBLICATION_DATE_RELEVANT, BigDecimal.valueOf(100), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_HAS_ANSWER, BigDecimal.valueOf(-200), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_PER_ANSWER, BigDecimal.valueOf(-200), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_NUMBER_FOLLOWERS, BigDecimal.valueOf(1), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_USER_ALREADY_ANSWERED, BigDecimal.valueOf(-400), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_USER_ALREADY_COMMENTED, BigDecimal.valueOf(100), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.MINIMUM_OF_ACTIVITIES_TO_CONSIDER_MAXIMUM_SCORE, BigDecimal.valueOf(20), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_ASKED_IN_TAG, BigDecimal.valueOf(500), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_ANSWERED_IN_TAG, BigDecimal.valueOf(2500), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_COMMENTED_IN_TAG, BigDecimal.valueOf(1000), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_FOLLOWED_IN_TAG, BigDecimal.valueOf(500), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.MAXIMUM_NUMBER_OF_PAGES_WITH_RECOMMENDED_QUESTIONS, BigDecimal.valueOf(80), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RECOMMENDATION_ALGORITHM_FOR_CATEGORY_OR_TAG, BigDecimal.valueOf(1), RecommendationChannelType.RECOMMENDED_LIST));
        return recommendationSettings;
    }

    private List<RecommendationSettingsRequestDto> settingsModelG() {
        List<RecommendationSettingsRequestDto> recommendationSettings = new ArrayList<>();
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.NUMBER_OF_DAYS_QUESTION_IS_RECENT, BigDecimal.valueOf(14), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.NUMBER_OF_DAYS_QUESTION_IS_RELEVANT, BigDecimal.valueOf(365), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_PUBLICATION_DATE_RECENT, BigDecimal.valueOf(300), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_PUBLICATION_DATE_RELEVANT, BigDecimal.valueOf(100), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_HAS_ANSWER, BigDecimal.valueOf(-200), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_PER_ANSWER, BigDecimal.valueOf(-200), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_NUMBER_FOLLOWERS, BigDecimal.valueOf(1), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_USER_ALREADY_ANSWERED, BigDecimal.valueOf(-400), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_USER_ALREADY_COMMENTED, BigDecimal.valueOf(100), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.MINIMUM_OF_ACTIVITIES_TO_CONSIDER_MAXIMUM_SCORE, BigDecimal.valueOf(20), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_ASKED_IN_TAG, BigDecimal.valueOf(5), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_ANSWERED_IN_TAG, BigDecimal.valueOf(90), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_COMMENTED_IN_TAG, BigDecimal.valueOf(45), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_FOLLOWED_IN_TAG, BigDecimal.valueOf(5), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.MAXIMUM_NUMBER_OF_PAGES_WITH_RECOMMENDED_QUESTIONS, BigDecimal.valueOf(80), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RECOMMENDATION_ALGORITHM_FOR_CATEGORY_OR_TAG, BigDecimal.valueOf(2), RecommendationChannelType.RECOMMENDED_LIST));
        return recommendationSettings;
    }

    private List<RecommendationSettingsRequestDto> settingsModelH() {
        List<RecommendationSettingsRequestDto> recommendationSettings = new ArrayList<>();
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.NUMBER_OF_DAYS_QUESTION_IS_RECENT, BigDecimal.valueOf(21), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.NUMBER_OF_DAYS_QUESTION_IS_RELEVANT, BigDecimal.valueOf(365), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_PUBLICATION_DATE_RECENT, BigDecimal.valueOf(300), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_PUBLICATION_DATE_RELEVANT, BigDecimal.valueOf(100), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_HAS_ANSWER, BigDecimal.valueOf(-200), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_PER_ANSWER, BigDecimal.valueOf(-200), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_NUMBER_FOLLOWERS, BigDecimal.valueOf(1), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_USER_ALREADY_ANSWERED, BigDecimal.valueOf(-400), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_USER_ALREADY_COMMENTED, BigDecimal.valueOf(100), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.MINIMUM_OF_ACTIVITIES_TO_CONSIDER_MAXIMUM_SCORE, BigDecimal.valueOf(20), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_ASKED_IN_TAG, BigDecimal.valueOf(5), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_ANSWERED_IN_TAG, BigDecimal.valueOf(90), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_COMMENTED_IN_TAG, BigDecimal.valueOf(45), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_FOLLOWED_IN_TAG, BigDecimal.valueOf(5), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.MAXIMUM_NUMBER_OF_PAGES_WITH_RECOMMENDED_QUESTIONS, BigDecimal.valueOf(80), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RECOMMENDATION_ALGORITHM_FOR_CATEGORY_OR_TAG, BigDecimal.valueOf(2), RecommendationChannelType.RECOMMENDED_LIST));
        return recommendationSettings;
    }

    private List<RecommendationSettingsRequestDto> settingsModelI() {
        List<RecommendationSettingsRequestDto> recommendationSettings = new ArrayList<>();
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.NUMBER_OF_DAYS_QUESTION_IS_RECENT, BigDecimal.valueOf(14), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.NUMBER_OF_DAYS_QUESTION_IS_RELEVANT, BigDecimal.valueOf(365), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_PUBLICATION_DATE_RECENT, BigDecimal.valueOf(300), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_PUBLICATION_DATE_RELEVANT, BigDecimal.valueOf(100), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_HAS_ANSWER, BigDecimal.valueOf(0), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_PER_ANSWER, BigDecimal.valueOf(-200), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_NUMBER_FOLLOWERS, BigDecimal.valueOf(1), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_USER_ALREADY_ANSWERED, BigDecimal.valueOf(-400), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_USER_ALREADY_COMMENTED, BigDecimal.valueOf(100), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.MINIMUM_OF_ACTIVITIES_TO_CONSIDER_MAXIMUM_SCORE, BigDecimal.valueOf(20), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_ASKED_IN_TAG, BigDecimal.valueOf(25), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_ANSWERED_IN_TAG, BigDecimal.valueOf(250), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_COMMENTED_IN_TAG, BigDecimal.valueOf(100), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_FOLLOWED_IN_TAG, BigDecimal.valueOf(25), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.MAXIMUM_NUMBER_OF_PAGES_WITH_RECOMMENDED_QUESTIONS, BigDecimal.valueOf(80), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RECOMMENDATION_ALGORITHM_FOR_CATEGORY_OR_TAG, BigDecimal.valueOf(2), RecommendationChannelType.RECOMMENDED_LIST));
        return recommendationSettings;
    }

    private List<RecommendationSettingsRequestDto> settingsModelJ() {
        List<RecommendationSettingsRequestDto> recommendationSettings = new ArrayList<>();
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.NUMBER_OF_DAYS_QUESTION_IS_RECENT, BigDecimal.valueOf(14), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.NUMBER_OF_DAYS_QUESTION_IS_RELEVANT, BigDecimal.valueOf(365), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_PUBLICATION_DATE_RECENT, BigDecimal.valueOf(300), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_PUBLICATION_DATE_RELEVANT, BigDecimal.valueOf(100), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_HAS_ANSWER, BigDecimal.valueOf(0), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_PER_ANSWER, BigDecimal.valueOf(-200), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_NUMBER_FOLLOWERS, BigDecimal.valueOf(1), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_USER_ALREADY_ANSWERED, BigDecimal.valueOf(-400), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_USER_ALREADY_COMMENTED, BigDecimal.valueOf(100), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.MINIMUM_OF_ACTIVITIES_TO_CONSIDER_MAXIMUM_SCORE, BigDecimal.valueOf(20), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_ASKED_IN_TAG, BigDecimal.valueOf(25), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_ANSWERED_IN_TAG, BigDecimal.valueOf(500), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_COMMENTED_IN_TAG, BigDecimal.valueOf(200), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_FOLLOWED_IN_TAG, BigDecimal.valueOf(25), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.MAXIMUM_NUMBER_OF_PAGES_WITH_RECOMMENDED_QUESTIONS, BigDecimal.valueOf(80), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RECOMMENDATION_ALGORITHM_FOR_CATEGORY_OR_TAG, BigDecimal.valueOf(2), RecommendationChannelType.RECOMMENDED_LIST));
        return recommendationSettings;
    }

//    private List<RecommendationSettingsRequestDto> settingsModelIOriginal() {
//        List<RecommendationSettingsRequestDto> recommendationSettings = new ArrayList<>();
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.NUMBER_OF_DAYS_QUESTION_IS_RECENT, BigDecimal.valueOf(14), RecommendationChannelType.RECOMMENDED_LIST));
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.NUMBER_OF_DAYS_QUESTION_IS_RELEVANT, BigDecimal.valueOf(365), RecommendationChannelType.RECOMMENDED_LIST));
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_PUBLICATION_DATE_RECENT, BigDecimal.valueOf(300), RecommendationChannelType.RECOMMENDED_LIST));
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_PUBLICATION_DATE_RELEVANT, BigDecimal.valueOf(100), RecommendationChannelType.RECOMMENDED_LIST));
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_HAS_ANSWER, BigDecimal.valueOf(0), RecommendationChannelType.RECOMMENDED_LIST));
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_PER_ANSWER, BigDecimal.valueOf(-100), RecommendationChannelType.RECOMMENDED_LIST));
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_NUMBER_FOLLOWERS, BigDecimal.valueOf(10), RecommendationChannelType.RECOMMENDED_LIST));
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_USER_ALREADY_ANSWERED, BigDecimal.valueOf(-400), RecommendationChannelType.RECOMMENDED_LIST));
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_USER_ALREADY_COMMENTED, BigDecimal.valueOf(100), RecommendationChannelType.RECOMMENDED_LIST));
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.MINIMUM_OF_ACTIVITIES_TO_CONSIDER_MAXIMUM_SCORE, BigDecimal.valueOf(20), RecommendationChannelType.RECOMMENDED_LIST));
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_ASKED_IN_TAG, BigDecimal.valueOf(5), RecommendationChannelType.RECOMMENDED_LIST));
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_ANSWERED_IN_TAG, BigDecimal.valueOf(90), RecommendationChannelType.RECOMMENDED_LIST));
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_COMMENTED_IN_TAG, BigDecimal.valueOf(45), RecommendationChannelType.RECOMMENDED_LIST));
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_FOLLOWED_IN_TAG, BigDecimal.valueOf(5), RecommendationChannelType.RECOMMENDED_LIST));
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.MAXIMUM_NUMBER_OF_PAGES_WITH_RECOMMENDED_QUESTIONS, BigDecimal.valueOf(80), RecommendationChannelType.RECOMMENDED_LIST));
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RECOMMENDATION_ALGORITHM_FOR_CATEGORY_OR_TAG, BigDecimal.valueOf(2), RecommendationChannelType.RECOMMENDED_LIST));
//        return recommendationSettings;
//    }

//    private List<RecommendationSettingsRequestDto> settingsModelJOriginal() {
//        List<RecommendationSettingsRequestDto> recommendationSettings = new ArrayList<>();
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.NUMBER_OF_DAYS_QUESTION_IS_RECENT, BigDecimal.valueOf(14), RecommendationChannelType.RECOMMENDED_LIST));
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.NUMBER_OF_DAYS_QUESTION_IS_RELEVANT, BigDecimal.valueOf(365), RecommendationChannelType.RECOMMENDED_LIST));
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_PUBLICATION_DATE_RECENT, BigDecimal.valueOf(300), RecommendationChannelType.RECOMMENDED_LIST));
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_PUBLICATION_DATE_RELEVANT, BigDecimal.valueOf(100), RecommendationChannelType.RECOMMENDED_LIST));
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_HAS_ANSWER, BigDecimal.valueOf(-200), RecommendationChannelType.RECOMMENDED_LIST));
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_PER_ANSWER, BigDecimal.valueOf(-200), RecommendationChannelType.RECOMMENDED_LIST));
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_NUMBER_FOLLOWERS, BigDecimal.valueOf(1), RecommendationChannelType.RECOMMENDED_LIST));
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_USER_ALREADY_ANSWERED, BigDecimal.valueOf(-400), RecommendationChannelType.RECOMMENDED_LIST));
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_USER_ALREADY_COMMENTED, BigDecimal.valueOf(100), RecommendationChannelType.RECOMMENDED_LIST));
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.MINIMUM_OF_ACTIVITIES_TO_CONSIDER_MAXIMUM_SCORE, BigDecimal.valueOf(10), RecommendationChannelType.RECOMMENDED_LIST));
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_ASKED_IN_TAG, BigDecimal.valueOf(5), RecommendationChannelType.RECOMMENDED_LIST));
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_ANSWERED_IN_TAG, BigDecimal.valueOf(90), RecommendationChannelType.RECOMMENDED_LIST));
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_COMMENTED_IN_TAG, BigDecimal.valueOf(45), RecommendationChannelType.RECOMMENDED_LIST));
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_FOLLOWED_IN_TAG, BigDecimal.valueOf(5), RecommendationChannelType.RECOMMENDED_LIST));
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.MAXIMUM_NUMBER_OF_PAGES_WITH_RECOMMENDED_QUESTIONS, BigDecimal.valueOf(160), RecommendationChannelType.RECOMMENDED_LIST));
//        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RECOMMENDATION_ALGORITHM_FOR_CATEGORY_OR_TAG, BigDecimal.valueOf(2), RecommendationChannelType.RECOMMENDED_LIST));
//        return recommendationSettings;
//    }

    private List<RecommendationSettingsRequestDto> settingsModelK() {
        List<RecommendationSettingsRequestDto> recommendationSettings = new ArrayList<>();
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.NUMBER_OF_DAYS_QUESTION_IS_RECENT, BigDecimal.valueOf(14), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.NUMBER_OF_DAYS_QUESTION_IS_RELEVANT, BigDecimal.valueOf(365), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_PUBLICATION_DATE_RECENT, BigDecimal.valueOf(300), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_PUBLICATION_DATE_RELEVANT, BigDecimal.valueOf(100), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_HAS_ANSWER, BigDecimal.valueOf(-200), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_PER_ANSWER, BigDecimal.valueOf(-200), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTION_NUMBER_FOLLOWERS, BigDecimal.valueOf(1), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_USER_ALREADY_ANSWERED, BigDecimal.valueOf(-400), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_USER_ALREADY_COMMENTED, BigDecimal.valueOf(100), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.MINIMUM_OF_ACTIVITIES_TO_CONSIDER_MAXIMUM_SCORE, BigDecimal.valueOf(20), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_ASKED_IN_TAG, BigDecimal.valueOf(25), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_ANSWERED_IN_TAG, BigDecimal.valueOf(125), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_COMMENTED_IN_TAG, BigDecimal.valueOf(50), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_QUESTIONS_FOLLOWED_IN_TAG, BigDecimal.valueOf(25), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.MAXIMUM_NUMBER_OF_PAGES_WITH_RECOMMENDED_QUESTIONS, BigDecimal.valueOf(80), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RECOMMENDATION_ALGORITHM_FOR_CATEGORY_OR_TAG, BigDecimal.valueOf(2), RecommendationChannelType.RECOMMENDED_LIST));
        return recommendationSettings;
    }
}
