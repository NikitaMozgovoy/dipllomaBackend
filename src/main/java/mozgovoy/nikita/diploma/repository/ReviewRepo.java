package mozgovoy.nikita.diploma.repository;

import mozgovoy.nikita.diploma.model.UserModel;
import mozgovoy.nikita.diploma.model.Film;
import mozgovoy.nikita.diploma.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepo extends JpaRepository<Review, Long> {

    void deleteReviewsById(Long id);

    Review findReviewById(Long id);

    List<Review> findReviewsByFilm(Film film);

    List<Review> findReviewByAuthor(UserModel user);

}
