package hello;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class CreateClaimDocumentService {
    private String storeEndpoint;
    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;

    @Autowired
    public CreateClaimDocumentService(
            DuncanProperties duncanProperties,
            RestTemplate restTemplate,
            ObjectMapper objectMapper) {
        this.storeEndpoint = duncanProperties.getOpencontentUrl() + "/allstate/content/createClaimDocument";
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public ResponseEntity<CreateClaimDocumentResponse> create(HttpEntity<MultiValueMap<String, Object>> requestEntity) {
        try {
            return restTemplate.postForEntity(storeEndpoint, requestEntity, CreateClaimDocumentResponse.class);
        } catch(HttpStatusCodeException e) {
            String errorMessage = getErrorMessageFromBody(e);
            throw new CreateClaimDocumentException(errorMessage, e);
        }
    }

    private String getErrorMessageFromBody(HttpStatusCodeException exception) {
        try {
            return objectMapper.readValue(exception.getResponseBodyAsByteArray(), CreateClaimDocumentErrorResponse.class).getMessage();
        } catch (IOException e) {
            return exception.getMessage();
        }
    }
}
