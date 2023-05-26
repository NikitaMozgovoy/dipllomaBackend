package mozgovoy.nikita.diploma.repository;

import mozgovoy.nikita.diploma.model.UserModel;
import mozgovoy.nikita.diploma.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepo extends JpaRepository<Review, Long> {

    void deleteReviewById(Long id);


    Review findReviewById(Long id);

//    List<Review> findReviewsByFilm_id(Long filmId);

    List<Review> findReviewsByFilmId(Long filmId);

    Page<Review> findReviewsByFilmId(Long filmId, Pageable pageable);



//    List<Review> findReviewByAuthor(UserModel user);

}
