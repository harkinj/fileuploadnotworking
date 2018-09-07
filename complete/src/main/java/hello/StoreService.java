package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Objects;

@Service
public class StoreService {
    private CreateClaimDocumentService createClaimDocumentService;

    @Autowired
    public StoreService(CreateClaimDocumentService createClaimDocumentService) {
        this.createClaimDocumentService = createClaimDocumentService;
    }

    public StoreResponse store(StoreRequest storeRequest, String clientKey, String userId , InputStream fileStream) {

        HttpEntity<MultiValueMap<String, Object>> requestEntity = convertRequest(storeRequest, clientKey, userId, fileStream);
        ResponseEntity<CreateClaimDocumentResponse> createClaimDocumentResponse = createClaimDocumentService.create(requestEntity);

        return convertResponse(createClaimDocumentResponse.getBody());
    }

    private HttpEntity<MultiValueMap<String, Object>> convertRequest(StoreRequest storeRequest, String clientKey, String userId, InputStream fileStream) {

        MultiValueMap<String, Object> body = convertRequestBody(storeRequest, fileStream);
        HttpHeaders headers = convertHttpHeaders(clientKey, userId);

        return new HttpEntity<>(body, headers);
    }

    private MultiValueMap<String, Object> convertRequestBody(StoreRequest from,  InputStream fileStream) {

        MultiValueMap<String, Object> to = new LinkedMultiValueMap<>();
        to.add("prop-createDate", new Date().getTime());

        // to.add("file", new MultipartFileResource(from.getFile()));  //TODO
        //to.add("prop-objectName", from.getFile().getOriginalFilename());

        //to.add("file", new MultipartFileResource(fileStream)); //working

        to.add("file", new MultipartInputStreamFileResource(fileStream, from.getOrigFileName()));
        to.add("prop-objectName", from.getOrigFileName());


        to.add("prop-claimNumber", from.getClaimNumber());
        to.add("prop-applicationSourceSystem", from.getSystemName());
        to.add("referenceId", from.getReferenceId());
        to.add("docTypeCode", from.getDocTypeCode());
        to.add("additionalProps", from.getAdditionalProps());

        if(!Objects.isNull(from.getParticipantId())) {
            to.add("prop-participant", from.getParticipantId());
        }

        return to;
    }

    private HttpHeaders convertHttpHeaders(String clientKey, String userId) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("CLIENT_KEY", clientKey);
        headers.set("USER_ID", userId);

        return headers;
    }

    private StoreResponse convertResponse(CreateClaimDocumentResponse from) {

        CreateClaimDocumentResponse.Detail detail = from.getAlfrescoStoreResponseDetail();

        return StoreResponse.builder()
                .alfrescoId(detail.getAlfrescoId())
                .referenceId(detail.getReferenceId())
                .storeTimestamp(detail.getStoreTimestamp())
                .build();
    }
}


class MultipartInputStreamFileResource extends InputStreamResource {

    private final String filename;

    MultipartInputStreamFileResource(InputStream inputStream, String filename) {
        super(inputStream);
        this.filename = filename;
    }

    @Override
    public String getFilename() {
        return this.filename;
    }

    @Override
    public long contentLength() throws IOException {
        return -1; // we do not want to generally read the whole stream into memory ...
    }
}
