package hello;

import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StoreResponse {
    private String alfrescoId;
    private String referenceId;
    private Date storeTimestamp;
}
