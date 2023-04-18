package mozgovoy.nikita.diploma.dto;

import lombok.Data;
import mozgovoy.nikita.diploma.model.Review;

import java.io.Serializable;

@Data
public class ReviewDTO implements Serializable {
    private String id;
    private ReviewAuthorDTO author;
    private String text;

    public ReviewDTO(Review review) {
        this.text=review.getText();
        this.author=new ReviewAuthorDTO(review.getAuthor());
    }
}
