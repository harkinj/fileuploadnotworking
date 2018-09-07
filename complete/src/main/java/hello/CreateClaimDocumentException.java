package hello;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

public class CreateClaimDocumentException extends RuntimeException {
    private final HttpStatus httpStatus;

    public CreateClaimDocumentException(String message, HttpStatusCodeException cause) {
        super(message, cause);
        this.httpStatus = cause.getStatusCode();
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
