package mozgovoy.nikita.diploma.dto;

import lombok.Data;

@Data
public class KinopoiskReviewDTO {
    private Integer movieId;
    private String title;
    private String type;
    private String review;
    private String date;
    private String author;

    public KinopoiskReviewDTO(Integer movieId, String title, String type, String review, String date, String author) {
        this.movieId = movieId;
        this.title = title;
        this.type = type;
        this.review = review;
        this.date = date;
        this.author = author;
    }
}
