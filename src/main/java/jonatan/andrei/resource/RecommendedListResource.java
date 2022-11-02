package jonatan.andrei.resource;

import jonatan.andrei.dto.RecommendedListResponseDto;
import jonatan.andrei.service.PostService;
import jonatan.andrei.service.RecommendedListService;
import org.springframework.http.ResponseEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.time.LocalDateTime;

@Path("/recommended-list")
@ApplicationScoped
public class RecommendedListResource {

    @Inject
    RecommendedListService recommendedListService;

    @Inject
    PostService postService;

    @GET
    public ResponseEntity<RecommendedListResponseDto> findRecommendedList(@QueryParam("lengthQuestionListPage") Integer lengthQuestionListPage,
                                                                          @NotNull @QueryParam("integrationUserId") String integrationUserId,
                                                                          @QueryParam("recommendedListId") Long recommendedListId,
                                                                          @QueryParam("pageNumber") Integer pageNumber,
                                                                          @QueryParam("dateOfRecommendations") LocalDateTime dateOfRecommendations) {
        return ResponseEntity.ok(recommendedListService.findRecommendedList(lengthQuestionListPage, integrationUserId, recommendedListId, pageNumber, dateOfRecommendations));
    }

    @GET
    @Path("/question/{integrationQuestionId}")
    public ResponseEntity<RecommendedListResponseDto.RecommendedQuestionResponseDto> calculateQuestionScoreToUser(String integrationQuestionId,
                                                                                                                  @NotNull @QueryParam("integrationUserId") String integrationUserId,
                                                                                                                  @QueryParam("dateOfRecommendations") LocalDateTime dateOfRecommendations) {
        return ResponseEntity.ok(postService.calculateQuestionScoreToUser(integrationUserId, integrationQuestionId, dateOfRecommendations));
    }

}
