package mozgovoy.nikita.diploma.dto;

import com.google.gson.Gson;
import lombok.Data;
import org.json.JSONObject;

import java.util.*;


@Data
public class FilmDTO {
    private Long id;
    private Long tmdbId;
    private String name;
    private String alternativeName;
    private Integer year;
    private Double kinopoiskRating;
    private Double imdbRating;
    private Double tmdbRating;
    private Double localRating;
    private Integer ageRating;
    private List<String> directors = new ArrayList<>();
    private List<String> producers = new ArrayList<>();
    private List<String> actors = new ArrayList<>();
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
                result.directors.add(x.get("name"));
            }
            if(x.get("profession").equals("продюсеры")){
                result.producers.add(x.get("name"));
            }
            if(x.get("profession").equals("актеры")){
                result.actors.add(x.get("name"));
            }
        });

        if (((Map<String, Map<String, String>>) (filmData.toMap().get("externalId"))).get("tmdb")!=null) {
            result.tmdbId = Long.valueOf(String.valueOf(filmData.getJSONObject("externalId").toMap().get("tmdb")));
        }

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
        else result.posterUrl = "https://yastatic.net/s3/kinopoisk-frontend/common-static/img/projector-logo/placeholder.svg";

        if(String.valueOf(filmData.getJSONObject("rating").toMap().get("kp"))!="null") {
            result.kinopoiskRating = Double.valueOf(String.valueOf(filmData.getJSONObject("rating").toMap().get("kp")));
        }
        else result.kinopoiskRating=null;

        if(String.valueOf(filmData.getJSONObject("rating").toMap().get("imdb"))!="null") {
            result.imdbRating = Double.valueOf(String.valueOf(filmData.getJSONObject("rating").toMap().get("imdb")));
        }
        else result.imdbRating=null;

        if(String.valueOf(filmData.getJSONObject("rating").toMap().get("tmdb"))!="null") {
            result.tmdbRating = Double.valueOf(String.valueOf(filmData.getJSONObject("rating").toMap().get("tmdb")));
        }
        else result.tmdbRating = null;

        return result;
    }
}

