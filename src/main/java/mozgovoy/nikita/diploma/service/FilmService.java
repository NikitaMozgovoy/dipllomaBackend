package mozgovoy.nikita.diploma.service;

import mozgovoy.nikita.diploma.dto.FilmDTO;
import mozgovoy.nikita.diploma.dto.FilmsListDTO;
import mozgovoy.nikita.diploma.dto.LocalReviewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONObject;

@Service
public class FilmService {

    @Value("${kinopoisk.api.key}")
    private String kinopoiskApiKey;

    private String apiUrl = "https://api.kinopoisk.dev/v1.3/movie";

    private String queryParams = "?selectFields=id&selectFields=name&selectFields=alternativeName&selectFields=year&selectFields=persons.profession&selectFields=persons.name&selectFields=countries&selectFields=poster&selectFields=movieLength&selectFields=genres&selectFields=type&selectFields=shortDescription&selectFields=rating";
    private LocalReviewsService localReviewsService;
    private ExternalReviewsService externalReviewsService;

    @Autowired
    public FilmService(LocalReviewsService localReviewsService, ExternalReviewsService externalReviewsService) {
        this.localReviewsService = localReviewsService;
        this.externalReviewsService = externalReviewsService;
    }

    private JSONObject getApiResponse(String urlString){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().
                uri(URI.create(urlString))
                .header("accept","application/json")
                .header("X-API-KEY", kinopoiskApiKey)
                .build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }

        return new JSONObject(response.body());
    }

    public List<FilmsListDTO> getSearchResults(String query, Integer limit, Integer page) {
            String urlString = apiUrl + queryParams;
            urlString += (query+ "&page=" + page + "&limit=" + limit);
        return new FilmsListDTO().fillTheData(getApiResponse(urlString));
    }

    public FilmDTO getFilmById(Long id){
        FilmDTO result = new FilmDTO().fillTheData(getApiResponse(apiUrl + "/" + id));
        result.setKinopoiskReviews(externalReviewsService.getKinopoiskReviews(result.getId()));
        if (result.getTmdbId()!=null) {
            result.setTmdbReviews(externalReviewsService.getTmdbReviews(result.getTmdbId()));
        }
        else{
            result.setTmdbReviews(null);
        }
        List<LocalReviewDTO> localReviews = localReviewsService.getFilmReviews(result.getId());
        result.setLocalReviews(localReviews);
        double sum=0;
        for(LocalReviewDTO review:localReviews){
            if(review.getRating()!=null) {
                sum += review.getRating();
            }
        }
        if (sum==0){
            result.setLocalRating(null);
        }
        else result.setLocalRating(sum/localReviews.size());
        return result;
    }

    public Integer getFilmsPagesQuantity(String query, Integer limit){
        String urlString = apiUrl + queryParams + "&limit=" + limit;
        if (query!=null){
            urlString+=query;
        }
        return (Integer) getApiResponse(urlString).toMap().get("pages");
    }
}
