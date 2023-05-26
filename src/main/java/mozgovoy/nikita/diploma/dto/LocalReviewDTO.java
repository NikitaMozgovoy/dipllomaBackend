package mozgovoy.nikita.diploma.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import mozgovoy.nikita.diploma.model.Review;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
public class LocalReviewDTO implements Serializable {
    private Long id;
    private Long filmId;
    private ReviewAuthorDTO author;
    private String text;
    private Integer rating;

    public LocalReviewDTO(Review review) {
        this.text=review.getText();
        this.id=review.getId();
        this.filmId= review.getFilmId();
        this.author=new ReviewAuthorDTO(review.getAuthor());
        this.rating = review.getRating();
    }

    static public List<LocalReviewDTO> getDTOArray(List<Review> reviews){
        List<LocalReviewDTO> results = new ArrayList<>();
        for (Review item : reviews) {
            results.add(new LocalReviewDTO(item));
        }
        return results;
    }
}
