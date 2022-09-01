package jonatan.andrei.resource;

import jonatan.andrei.dto.CreatePostRequestDto;
import jonatan.andrei.factory.QuestionFactory;
import jonatan.andrei.model.Question;
import jonatan.andrei.model.User;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

@Path("/question-recommender")
@ApplicationScoped
public class QuestionRecommenderResource {

    @DELETE
    @Path("/clear")
    public void clear() {

    }
}
