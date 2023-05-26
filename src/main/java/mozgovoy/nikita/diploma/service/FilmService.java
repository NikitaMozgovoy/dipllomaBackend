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

    private LocalReviewService localReviewService;
    private ExternalReviewsService externalReviewsService;

    @Autowired
    public FilmService(LocalReviewService localReviewService, ExternalReviewsService externalReviewsService) {
        this.localReviewService = localReviewService;
        this.externalReviewsService = externalReviewsService;
    }

    private JSONObject getApiResponse(String urlString) throws IOException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().
                uri(URI.create(urlString))
                .header("accept","application/json")
                .header("X-API-KEY", kinopoiskApiKey)
                .build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return new JSONObject(response.body());
    }

    public List<FilmsListDTO> findAllFilms(int limit, int page){
            String urlString = apiUrl + "?selectFields=id&selectFields=name&selectFields=alternativeName&selectFields=year&selectFields=persons.profession&selectFields=persons.name&selectFields=countries&selectFields=poster&selectFields=movieLength&selectFields=genres&selectFields=type&selectFields=description";
            urlString += ("&page=" + page + "&limit=" + limit);
        try {
            return new FilmsListDTO().fillTheData(getApiResponse(urlString));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<FilmsListDTO> getSearchResults(String query, int limit, int page) {
            String urlString = apiUrl + "?selectFields=id&selectFields=name&selectFields=alternativeName&selectFields=year&selectFields=persons.profession&selectFields=persons.name&selectFields=countries&selectFields=poster&selectFields=movieLength&selectFields=genres&selectFields=type&selectFields=description";
            urlString += (query+ "&page=" + page + "&limit=" + limit);
        try {
            return new FilmsListDTO().fillTheData(getApiResponse(urlString));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public FilmDTO getFilmById(Long id){
        try {
            FilmDTO result = new FilmDTO().fillTheData(getApiResponse(apiUrl + "/" + id));
            result.setKinopoiskReviews(externalReviewsService.getKinopoiskReviews(result.getId(), 1));
            if (result.getTmdbId()!=null) {
                result.setTmdbReviews(externalReviewsService.getTmdbReviews(result.getTmdbId(), 1));
            }
            else{
                result.setTmdbReviews(null);
            }
            result.setLocalReviews(localReviewService.findFilmReviews(result.getId(), 1));
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
