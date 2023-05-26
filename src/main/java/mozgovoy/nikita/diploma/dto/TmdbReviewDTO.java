package mozgovoy.nikita.diploma.dto;

import com.google.gson.Gson;
import lombok.Data;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class TmdbReviewDTO{
    private Long filmId;
    private Double rating;
    private String text;
    private String author;
}
