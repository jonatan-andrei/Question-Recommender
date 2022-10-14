package jonatan.andrei.exception;

import io.quarkus.logging.Log;
import jonatan.andrei.dto.ExceptionMessageDto;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CustomExceptionHandler implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        if (!(exception instanceof InconsistentIntegratedDataException)) {
            Log.error("Error in the application: ", exception);
        }

        return Response.status(Response.Status.BAD_REQUEST).
                entity(ExceptionMessageDto.builder()
                        .errorMessage(exception.getMessage())
                        .build()).build();

    }
}