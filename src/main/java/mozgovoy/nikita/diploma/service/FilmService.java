package mozgovoy.nikita.diploma.service;

import mozgovoy.nikita.diploma.dto.FilmDTO;
import mozgovoy.nikita.diploma.dto.FilmsListDTO;
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
    private String apiKey;

    private String apiUrl = "https://api.kinopoisk.dev/v1.3/movie";

    private JSONObject getApiResponse(String urlString) throws IOException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().
                uri(URI.create(urlString))
                .header("accept","application/json")
                .header("X-API-KEY", apiKey)
                .build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return new JSONObject(response.body());
    }

    public List<FilmsListDTO> findAllFilms(int page){
            String urlString = apiUrl + "?selectFields=id&selectFields=name&selectFields=year&selectFields=persons.profession&selectFields=persons.name&selectFields=countries&selectFields=poster&selectFields=movieLength&selectFields=genres&selectFields=type&selectFields=description&limit=10";
            urlString += ("&page=" + page);
        try {
            return new FilmsListDTO().fillTheData(getApiResponse(urlString));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<FilmsListDTO> getSearchResults(String name, int page) {
            String urlString = apiUrl + "?selectFields=id&selectFields=name&selectFields=year&selectFields=persons.profession&selectFields=persons.name&limit=10";
            urlString += ("&name=" + name + "&page=" + page);
        try {
            return new FilmsListDTO().fillTheData(getApiResponse(urlString));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public FilmDTO getFilmById(Long id){
        try {
            return new FilmDTO().fillTheData(getApiResponse(apiUrl + "/" + id), getApiResponse(("https://api.kinopoisk.dev/v1/review?page=1&limit=5&movieId=" + id)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
