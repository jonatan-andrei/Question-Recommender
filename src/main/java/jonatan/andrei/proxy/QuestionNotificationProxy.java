package jonatan.andrei.proxy;

import jonatan.andrei.dto.ListQuestionNotificationRequestDto;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@RegisterRestClient(configKey = "question-notification-proxy")
public interface QuestionNotificationProxy {

    @POST
    @Path("/question-notification")
    void saveQuestionNotificationList(ListQuestionNotificationRequestDto notificationRequestDtoList);
}
