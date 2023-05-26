package mozgovoy.nikita.diploma.dto;

import com.google.gson.Gson;
import lombok.Data;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class FilmsListDTO implements Serializable {
    private Long id;
    private String name;
    private Integer year;
    private Object countries;
    private String posterUrl;
    private Object genres;
    private String type;
    private String description;


    public List<FilmsListDTO> fillTheData (JSONObject json) {
        Gson gson = new Gson();
        List<FilmsListDTO> results = new ArrayList<>();
        for (Object object : json.getJSONArray("docs")) {
            FilmsListDTO item = gson.fromJson(String.valueOf(object), FilmsListDTO.class);
            JSONObject jsonObject = (JSONObject) object;
            Map<String, String> itemPoster = (Map<String, String>) jsonObject.toMap().get("poster");
            if (itemPoster != null){
                item.posterUrl = itemPoster.get("previewUrl");
            }
            else {
                item.posterUrl = "https://yastatic.net/s3/kinopoisk-frontend/common-static/img/projector-logo/placeholder.svg";
            }
            results.add(item);
        }
        return results;
    }
}
