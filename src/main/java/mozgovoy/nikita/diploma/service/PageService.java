package mozgovoy.nikita.diploma.service;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class PageService {
    public Integer getApiResponse(String urlString, String keyHeader, String apiKey){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().
                uri(URI.create(urlString))
                .header("accept","application/json")
                .header(keyHeader, apiKey)
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
        JSONObject json = new JSONObject(response.body());
        switch (keyHeader){
            case "X-API-KEY":{
                return (Integer) json.toMap().get("pages");
            }
//            case "Authorization": {
//                return (Integer) json.
//                }
//            }
        }
        return 1;
    }
}
