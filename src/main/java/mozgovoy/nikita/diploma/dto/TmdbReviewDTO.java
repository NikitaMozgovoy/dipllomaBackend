package mozgovoy.nikita.diploma.dto;
import lombok.Data;


@Data
public class TmdbReviewDTO{
    private String id;
    private Long filmId;
    private Double rating;
    private String text;
    private String author;
}
