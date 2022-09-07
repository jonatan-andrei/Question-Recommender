package jonatan.andrei.resource;

import jonatan.andrei.service.QuestionRecommenderService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;

@Path("/question-recommender")
@ApplicationScoped
public class QuestionRecommenderResource {

    @Inject
    QuestionRecommenderService questionRecommenderService;

    @DELETE
    @Path("/clear")
    public void clear() {
        questionRecommenderService.clear();
    }

    @DELETE
    @Path("/clear-recommendations")
    public void clearRecommendations() {
        questionRecommenderService.clearRecommendations();
    }
}
