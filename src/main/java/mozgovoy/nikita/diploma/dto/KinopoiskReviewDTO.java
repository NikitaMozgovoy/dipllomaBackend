package mozgovoy.nikita.diploma.dto;

import lombok.Data;

@Data
public class KinopoiskReviewDTO {
    private Long movieId;
    private String title;
    private String type;
    private String review;
    private String date;
    private String author;
}
