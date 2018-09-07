package hello;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@ToString
public class StoreErrorResponse {
    Date timestamp;
    int status;
    String error;
    String message;
    String path;

    public StoreErrorResponse(HttpStatus httpStatus, String message, String path) {
        this.timestamp = new Date();
        this.status = httpStatus.value();
        this.error = httpStatus.getReasonPhrase();
        this.message = message;
        this.path = path;
    }
}
