package jonatan.andrei.proxy;

import jonatan.andrei.dto.ListNotificationRequestDto;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@RegisterRestClient(configKey = "notification-proxy")
public interface NotificationProxy {

    @POST
    @Path("/notification")
    void saveNotificationList(ListNotificationRequestDto notificationRequestDtoList);
}
