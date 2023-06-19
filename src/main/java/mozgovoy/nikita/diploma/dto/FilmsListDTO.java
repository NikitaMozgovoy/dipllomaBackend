package mozgovoy.nikita.diploma.dto;

import com.google.gson.Gson;
import lombok.Data;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Data
public class FilmsListDTO implements Serializable {
    private Long id;
    private String name;
    private Double kinopoiskRating;
    private Double imdbRating;
    private String alternativeName;
    private Integer year;
    private String posterUrl;
    private List<Object> genresList = new ArrayList<>();
    private List<String> countriesList = new ArrayList<>();
    private String type;
    private String shortDescription;


    public List<FilmsListDTO> fillTheData (JSONObject filmData) {
        Gson gson = new Gson();
        List<FilmsListDTO> result = new ArrayList<>();
        for (Object object : filmData.getJSONArray("docs")) {
            FilmsListDTO item = gson.fromJson(String.valueOf(object), FilmsListDTO.class);
            JSONObject jsonObject = (JSONObject) object;
            Map<String, String> itemPoster = (Map<String, String>) jsonObject.toMap().get("poster");
            if (itemPoster != null){
                item.posterUrl = itemPoster.get("previewUrl");
            }
            else {
                item.posterUrl = "https://yastatic.net/s3/kinopoisk-frontend/common-static/img/projector-logo/placeholder.svg";
            }

            ArrayList<Map<String, String>> genres = (ArrayList<Map<String, String>>) jsonObject.toMap().get("genres");
            genres.forEach(x->{
                item.genresList.add((x.get("name")));
            });

            ArrayList<Map<String, String>> countries = (ArrayList<Map<String, String>>) jsonObject.toMap().get("countries");
            countries.forEach(x->{
                item.countriesList.add((x.get("name")));
            });

            if(!Objects.equals(String.valueOf(jsonObject.getJSONObject("rating").toMap().get("kp")), "null")) {
                item.kinopoiskRating = Double.valueOf(String.valueOf(jsonObject.getJSONObject("rating").toMap().get("kp")));
            }
            else item.kinopoiskRating=null;
            if(!Objects.equals(String.valueOf(jsonObject.getJSONObject("rating").toMap().get("imdb")), "null")) {
                item.imdbRating = Double.valueOf(String.valueOf(jsonObject.getJSONObject("rating").toMap().get("imdb")));
            }
            else item.imdbRating=null;
            result.add(item);
        }
        return result;
    }
}
