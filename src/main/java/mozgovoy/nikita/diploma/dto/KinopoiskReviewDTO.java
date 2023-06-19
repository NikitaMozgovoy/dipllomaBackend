package mozgovoy.nikita.diploma.dto;

import lombok.Data;

@Data
public class KinopoiskReviewDTO {
    private Long movieId;
    private String title;
    private String review;
    private String author;
}
