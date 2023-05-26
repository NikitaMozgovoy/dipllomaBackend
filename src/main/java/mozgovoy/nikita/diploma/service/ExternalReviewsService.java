package mozgovoy.nikita.diploma.service;

import com.google.gson.Gson;
import mozgovoy.nikita.diploma.dto.KinopoiskReviewDTO;
import mozgovoy.nikita.diploma.dto.TmdbReviewDTO;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ExternalReviewsService {

    @Value("${kinopoisk.api.key}")
    private String kinopoiskApiKey;

    @Value("${tmdb.api.key}")
    private String tmdbApiKey;



    public JSONObject getApiResponse(String urlString, String keyHeader, String apiKey){
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

        return new JSONObject(response.body());
    }
    public List<KinopoiskReviewDTO> getKinopoiskReviews(Long filmId, Integer page){
        List result = new ArrayList<>();
        Gson gson = new Gson();
        JSONObject response = getApiResponse("https://api.kinopoisk.dev/v1/review?limit=5&movieId=" + filmId + "&page="+page, "X-API-KEY", kinopoiskApiKey);
        for (Object object : response.getJSONArray("docs")) {
            result.add(gson.fromJson(String.valueOf(object), KinopoiskReviewDTO.class));
        }
        return result;
    }

    public List<TmdbReviewDTO> getTmdbReviews(Long filmId, Integer page){
        List result = new ArrayList<>();
        JSONObject response = getApiResponse("https://api.themoviedb.org/3/movie/" + filmId + "/reviews?page=" +page, "Authorization", tmdbApiKey);
        for (Map object: (List<Map<String, Object>>) response.toMap().get("results")){
            TmdbReviewDTO review = new TmdbReviewDTO();
            review.setFilmId(Long.parseLong(String.valueOf(response.toMap().get("id"))));
            Map<String, Object> author = (Map<String, Object>) object.get("author_details");
            review.setAuthor((String) author.get("username"));
            if(author.get("rating")!=null) {
                review.setRating(Double.valueOf(author.get("rating").toString()));
            }
            else{review.setRating(null);}
            review.setText((String) object.get("content"));
            result.add(review);
        }
        return result;
    }

}
