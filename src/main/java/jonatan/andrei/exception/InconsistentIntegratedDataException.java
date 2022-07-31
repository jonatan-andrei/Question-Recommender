package jonatan.andrei.exception;

import javax.ws.rs.BadRequestException;

public class InconsistentIntegratedDataException extends BadRequestException {

    public InconsistentIntegratedDataException(String message){
        super(message);
    }
}
