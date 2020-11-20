package pl.ps.creditapp.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.ps.creditapp.core.bik.BikApi;
import pl.ps.creditapp.core.bik.ErrorResponse;
import pl.ps.creditapp.core.bik.ScoringRequest;
import pl.ps.creditapp.core.bik.ScoringResponse;
import pl.ps.creditapp.core.scoring.BikScoringCalculator;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static pl.ps.creditapp.core.Constants.BIK_API_ENDPOINT_URL;

public class BikApiAdapter implements BikApi {
    private static final Logger log = LoggerFactory.getLogger(BikApiAdapter.class);

    private static final HttpClient client = HttpClient.newBuilder().build();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private String token;

    {
        token = System.getenv("API_TOKEN");
        if (token == null) {
            throw new IllegalStateException("Missing env variable");
        }
    }

    @Override
    public ScoringResponse getScoring(ScoringRequest request) {
        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(new URI(BIK_API_ENDPOINT_URL))
                    .header("x-token", token)
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(request)))
                    .build();
            log.info(httpRequest.toString());
            HttpResponse<String> response = client.send(httpRequest,
                    HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                ErrorResponse errorResponse = objectMapper.readValue(response.body(), ErrorResponse.class);
                throw new IllegalStateException(errorResponse.getError());
            }
            return objectMapper.readValue(response.body(), ScoringResponse.class);

        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("Unexpected error during communication with BIK API");
        }
    }
}
