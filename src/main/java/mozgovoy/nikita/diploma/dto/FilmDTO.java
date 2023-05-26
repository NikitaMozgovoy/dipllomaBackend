package mozgovoy.nikita.diploma.dto;

import com.google.gson.Gson;
import lombok.Data;
import mozgovoy.nikita.diploma.service.ExternalReviewsService;
import mozgovoy.nikita.diploma.service.LocalReviewService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;


@Data
public class FilmDTO {
    private Long id;
    private Long tmdbId;
    private String name;
    private Integer year;
    private List<String> director = new ArrayList<>();
    private List<String> countriesList = new ArrayList<>();
    private String posterUrl;
    private Integer movieLength;
    private List<Object> genresList = new ArrayList<>();
    private String type;
    private String description;
    private List<KinopoiskReviewDTO> kinopoiskReviews = new ArrayList<>();
    private List<TmdbReviewDTO> tmdbReviews = new ArrayList<>();
    private List<LocalReviewDTO> localReviews;


    public FilmDTO fillTheData (JSONObject filmData) {
        Gson gson = new Gson();
        FilmDTO result = gson.fromJson(String.valueOf(filmData), FilmDTO.class);
        ArrayList<Map<String, String>> persons = (ArrayList<Map<String, String>>) filmData.toMap().get("persons");
        persons.forEach(x->{
            if(x.get("profession").equals("режиссеры")){
                result.director.add(x.get("name"));
            }
        });

        ArrayList<Map<String, String>> genres = (ArrayList<Map<String, String>>) filmData.toMap().get("genres");
        genres.forEach(x->{
            result.genresList.add((x.get("name")));
        });

        ArrayList<Map<String, String>> countries = (ArrayList<Map<String, String>>) filmData.toMap().get("countries");
        countries.forEach(x->{
            result.countriesList.add((x.get("name")));
        });

        if (filmData.toMap().get("poster")!= null){
            result.posterUrl=String.valueOf(filmData.getJSONObject("poster").toMap().get("url"));
        }
        else {
            result.posterUrl = "https://yastatic.net/s3/kinopoisk-frontend/common-static/img/projector-logo/placeholder.svg";
        }
        if (result.tmdbId!=null) {
            result.tmdbId = Long.valueOf(String.valueOf(filmData.getJSONObject("externalId").toMap().get("tmdb")));
        }
        return result;
    }
}
