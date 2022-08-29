package jonatan.andrei.service;

import jonatan.andrei.dto.RecommendedListResponseDto;
import jonatan.andrei.dto.SettingsDto;
import jonatan.andrei.exception.InconsistentIntegratedDataException;
import jonatan.andrei.factory.RecommendedListFactory;
import jonatan.andrei.model.RecommendedList;
import jonatan.andrei.model.User;
import jonatan.andrei.repository.RecommendedListRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.util.Objects.isNull;

@ApplicationScoped
public class RecommendedListService {

    @Inject
    RecommendedListRepository recommendedListRepository;

    @Inject
    UserService userService;

    @Inject
    QuestionService questionService;

    @Inject
    SettingsService settingsService;

    @Inject
    RecommendedListPageService recommendedListPageService;

    // Escrever documentação
    // Fazer paginação
    // Adicionar configurações na consulta
    // Criar base para testes da query de recomendações
    // Testes do método defineRealPageNumberIgnoringExistingPages
    // Teste do método findRecommendedList com nenhum registro
    // Teste do método findRecommendedList com um registro
    // Teste do método findRecommendedList com 22 registros (duas páginas)
    // Teste do método findRecommendedList com lista não existente (exception)
    // Teste do método findRecommendedList com página já gerada
    // Teste do método findRecommendedList com pageNumber null (exception)
    // Teste do método findRecommendedList com lista já existente mas página ainda não gerada

    @Transactional
    public RecommendedListResponseDto findRecommendedList(Integer lengthQuestionListPage,
                                                          String integrationUserId,
                                                          Long recommendedListId,
                                                          Integer pageNumber) {
        SettingsDto settings = settingsService.getSettings();
        User user = userService.findByIntegrationUserId(integrationUserId);
        lengthQuestionListPage = isNull(lengthQuestionListPage) ? settings.getDefaultLengthQuestionListPage() : lengthQuestionListPage;
        RecommendedList recommendedList = isNull(recommendedListId)
                ? createRecommendedList(lengthQuestionListPage, user.getUserId())
                : findByRecommendedListId(recommendedListId);

        return recommendedListPageService.findOrCreatePage(recommendedList, pageNumber);
    }

    private RecommendedList findByRecommendedListId(Long recommendedListId) {
        return recommendedListRepository.findById(recommendedListId)
                .orElseThrow(() -> new InconsistentIntegratedDataException("Not found recommended list with id " + recommendedListId));
    }

    private RecommendedList createRecommendedList(Integer lengthQuestionListPage, Long userId) {
        Integer totalQuestions = questionService.count();
        Integer totalPages = calculateTotalNumberOfPages(totalQuestions, lengthQuestionListPage);
        return recommendedListRepository.save(RecommendedListFactory.newRecommendedList(
                lengthQuestionListPage, userId, totalPages, totalQuestions));
    }

    private Integer calculateTotalNumberOfPages(Integer totalQuestions, Integer lengthQuestionListPage) {
        if (totalQuestions.equals(0)) {
            return 1;
        }
        return new BigDecimal(totalQuestions).divide(new BigDecimal(lengthQuestionListPage)).setScale(0, RoundingMode.CEILING).intValue();
    }
}
