package hello;

import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreateClaimDocumentResponse {
    private Detail alfrescoStoreResponseDetail;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class Detail {
        private String alfrescoId;
        private String referenceId;
        private Date storeTimestamp;
    }
}
