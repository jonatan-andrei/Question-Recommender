package jonatan.andrei.exception;

import jonatan.andrei.dto.ExceptionMessageDto;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@Slf4j
public class CustomBadRequestExceptionHandler implements ExceptionMapper<BadRequestException> {

    @Override
    public Response toResponse(BadRequestException exception) {
        log.error("Error in the application: ", exception);
        return Response.status(Response.Status.BAD_REQUEST).
                entity(ExceptionMessageDto.builder()
                        .errorMessage(exception.getMessage())
                        .build()).build();

    }
}