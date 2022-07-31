package jonatan.andrei.exception;

import jonatan.andrei.dto.ExceptionMessageDto;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CustomBadRequestExceptionHandler implements ExceptionMapper<BadRequestException> {

    @Override
    public Response toResponse(BadRequestException exception) {
        return Response.status(Response.Status.BAD_REQUEST).
                entity(ExceptionMessageDto.builder()
                        .errorMessage(exception.getMessage())
                        .build()).build();

    }
}