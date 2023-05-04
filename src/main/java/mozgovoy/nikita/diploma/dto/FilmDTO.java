package mozgovoy.nikita.diploma.dto;

import com.google.gson.Gson;
import lombok.Data;
import org.json.JSONObject;

import java.util.*;


@Data
public class FilmDTO {
    private Long id;
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
    private List<LocalReviewDTO> localReviews;


    public FilmDTO fillTheData (JSONObject filmData, JSONObject reviews) {
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

        result.posterUrl=String.valueOf(filmData.getJSONObject("poster").toMap().get("url"));

        for (Object object : reviews.getJSONArray("docs")) {
            result.kinopoiskReviews.add(gson.fromJson(String.valueOf(object), KinopoiskReviewDTO.class));
        }

        return result;
    }
}
