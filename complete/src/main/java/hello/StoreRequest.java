package hello;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StoreRequest {

    @NotNull
    private String docTypeCode;

    @NotNull
    private String origFileName;

    @NotNull
    @Size(min = 36, max = 36)
    private String referenceId;

    private String participantId;

    @NotNull
    private String claimNumber;

    @NotNull
    private String systemName;

    @NotNull
    private String brandName;

    private String additionalProps;
}
