package mozgovoy.nikita.diploma.dto;

import com.google.gson.Gson;
import lombok.Data;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class FilmsListDTO implements Serializable {
    private Long id;
    private String name;
    private Integer year;
    private Object countries;
    private Object poster;
    private Object genres;
    private String type;
    private String description;


    public List<FilmsListDTO> fillTheData (JSONObject json) {
        Gson gson = new Gson();
        List<FilmsListDTO> results = new ArrayList<>();
        for (Object object : json.getJSONArray("docs")) {
            results.add(gson.fromJson(String.valueOf(object), FilmsListDTO.class));
        }
        return results;
    }
}
