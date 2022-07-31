package jonatan.andrei.exception;

import javax.ws.rs.BadRequestException;

public class RequiredDataException extends BadRequestException {

    public RequiredDataException(String message) {
        super(message);
    }
}
