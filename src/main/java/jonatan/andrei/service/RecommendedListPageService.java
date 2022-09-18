package jonatan.andrei.service;

import jonatan.andrei.domain.RecommendationSettingsType;
import jonatan.andrei.dto.RecommendedListResponseDto;
import jonatan.andrei.exception.RequiredDataException;
import jonatan.andrei.factory.RecommendedListResponseFactory;
import jonatan.andrei.model.RecommendedList;
import jonatan.andrei.model.RecommendedListPage;
import jonatan.andrei.repository.RecommendedListPageRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@ApplicationScoped
public class RecommendedListPageService {

    @Inject
    RecommendedListPageRepository recommendedListPageRepository;

    @Inject
    RecommendedListPageQuestionService recommendedListPageQuestionService;

    public RecommendedListResponseDto findOrCreatePage(Long userId, RecommendedList recommendedList, Integer pageNumber, Integer lengthQuestionListPage, Map<RecommendationSettingsType, BigDecimal> recommendationSettings, LocalDateTime dateOfRecommendations) {
        if (isNull(pageNumber) || pageNumber < 1) {
            throw new RequiredDataException("Attribute 'pageNumber' is required and must be greater than zero");
        }

        List<RecommendedListPage> existingPages = recommendedListPageRepository.findByRecommendedListId(recommendedList.getRecommendedListId());
        RecommendedListPage recommendedListPage = filterRecommendedListPage(existingPages, pageNumber).orElse(null);
        if (nonNull(recommendedListPage)) {
            // return the existing page
            List<RecommendedListResponseDto.RecommendedQuestionResponseDto> questions = recommendedListPageQuestionService.findByRecommendedListPageId(recommendedListPage.getRecommendedListPageId());
            return RecommendedListResponseFactory.newRecommendedListResponseDto(recommendedList, questions);
        } else {
            recommendedListPage = recommendedListPageRepository.save(RecommendedListPage.builder()
                    .recommendedListId(recommendedList.getRecommendedListId())
                    .pageNumber(pageNumber)
                    .build());
        }

        Integer realPageNumber = defineRealPageNumberIgnoringExistingPages(existingPages, pageNumber);

        var questions = recommendedListPageQuestionService.newPage(userId, recommendedListPage, lengthQuestionListPage, realPageNumber, recommendationSettings, dateOfRecommendations);
        return RecommendedListResponseFactory.newRecommendedListResponseDto(recommendedList, questions);
    }

    public Integer defineRealPageNumberIgnoringExistingPages(List<RecommendedListPage> pages, Integer pageNumber) {
        Integer realPageNumber = 1;
        for (int i = 1; i < pageNumber; i++) {
            if (filterRecommendedListPage(pages, i).isEmpty()) {
                realPageNumber++;
            }

        }
        return realPageNumber;
    }

    private Optional<RecommendedListPage> filterRecommendedListPage(List<RecommendedListPage> pages, Integer pageNumber) {
        return pages.stream().filter(p -> p.getPageNumber().equals(pageNumber)).findFirst();
    }

    public void clear() {
        recommendedListPageRepository.deleteAll();
    }
}
