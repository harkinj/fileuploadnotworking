package hello;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreateClaimDocumentErrorResponse {
    private int status;
    private String message;
    private boolean error;
}
