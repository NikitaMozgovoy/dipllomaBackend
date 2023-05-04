package mozgovoy.nikita.diploma.dto;

import lombok.Data;
import mozgovoy.nikita.diploma.model.Review;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class LocalReviewDTO implements Serializable {
    private String id;
    private ReviewAuthorDTO author;
    private String text;
//    private LocalDateTime dateTime;

    public LocalReviewDTO(Review review) {
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
//        this.dateTime=LocalDateTime.now();
        this.text=review.getText();
        this.author=new ReviewAuthorDTO(review.getAuthor());
    }
}
