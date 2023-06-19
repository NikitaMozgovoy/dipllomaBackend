package mozgovoy.nikita.diploma.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.Setter;
import mozgovoy.nikita.diploma.dto.FilmDTO;
import mozgovoy.nikita.diploma.dto.KinopoiskReviewDTO;
import mozgovoy.nikita.diploma.dto.TmdbReviewDTO;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Paths;
import java.util.*;

@Setter
@Service
public class ExternalReviewsService {

    @Value("${kinopoisk.api.key}")
    private String kinopoiskApiKey;

    @Value("${tmdb.api.key}")
    private String tmdbApiKey;

    @Value("${yandex.api.key}")
    private String yandexApiKey;

    private ArrayList<String> translatedReviews = new ArrayList<>();
    public static Map<String, Integer> pages = new HashMap<>(Map.of("kinopoiskPage",1,"tmdbPage",1));


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
    public List<KinopoiskReviewDTO> getKinopoiskReviews(Long filmId){
        List result = new ArrayList<>();
        Gson gson = new Gson();
        JSONObject response = getApiResponse("https://api.kinopoisk.dev/v1/review?limit=5&movieId=" + filmId + "&page="+this.pages.get("kinopoiskPage"), "X-API-KEY", kinopoiskApiKey);
        for (Object object : response.getJSONArray("docs")) {
            result.add(gson.fromJson(String.valueOf(object), KinopoiskReviewDTO.class));
        }
        return result;
    }

    public List<TmdbReviewDTO> getTmdbReviews(Long filmId){
        List result = new ArrayList<>();
        JSONObject response = null;
        try {
            response = getApiResponse("https://api.themoviedb.org/3/movie/" + filmId + "/reviews?page=" + this.pages.get("tmdbPage"), "Authorization", tmdbApiKey);
        }
        catch (Exception e){
            return null;
        }
        for (Map object: (List<Map<String, Object>>) response.toMap().get("results")){
            TmdbReviewDTO review = new TmdbReviewDTO();
            review.setFilmId(filmId);
            review.setId(String.valueOf(object.get("id")));
            Map<String, Object> author = (Map<String, Object>) object.get("author_details");
            review.setAuthor((String) author.get("username"));
            if(author.get("rating")!=null) {
                review.setRating(Double.valueOf(author.get("rating").toString()));
            }
            else{review.setRating(null);}
            if(this.translatedReviews.contains(review.getId())){
                review.setText(translateReview(review.getId()));
            }
            else{
                review.setText((String) object.get("content"));
            }
            result.add(review);
        }
        return result;
    }

    private String translateReview(String reviewId){
        JSONObject query = new JSONObject();
        JSONObject review = getApiResponse("https://api.themoviedb.org/3/review/" + reviewId, "Authorization", tmdbApiKey);
        query.put("texts", (String.valueOf(review.toMap().get("content"))));
        query.put("targetLanguageCode", "ru");


        StringEntity jsonEntity = new StringEntity(String.valueOf(query), ContentType.APPLICATION_JSON);
        HttpPost request = new HttpPost(URI.create("https://translate.api.cloud.yandex.net/translate/v2/translate"));
        request.setHeader("Content-Type","application/json");
        request.setHeader("Authorization", yandexApiKey);
        request.setEntity(jsonEntity);

        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            response = client.execute(request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String responseJson = "";
        try {
            responseJson = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JSONObject res = new JSONObject(responseJson);
        return String.valueOf(res.getJSONArray("translations").getJSONObject(0).toMap().get("text"));
    }

    public Integer getReviewsPagesQuantity(Long filmId, String service) {
        switch (service) {
            case ("tmdb"): {
                Long tmdbId = new FilmDTO().fillTheData(getApiResponse("https://api.kinopoisk.dev/v1.3/movie/" + filmId, "X-API-KEY", kinopoiskApiKey)).getTmdbId();
                Integer num = (Integer) getApiResponse("https://api.themoviedb.org/3/movie/" + tmdbId + "/reviews", "Authorization", tmdbApiKey).toMap().get("total_results");
                return ((int) ((num + 5 - 1)  / 5));
            }
            case ("kinopoisk"): {
                return ((int) (getApiResponse("https://api.kinopoisk.dev/v1/review?limit=5&movieId=" + filmId, "X-API-KEY", kinopoiskApiKey).toMap().get("pages")));
            }
        }
        return 1;
    }
}
