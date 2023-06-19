package mozgovoy.nikita.diploma.repository;

import mozgovoy.nikita.diploma.model.UserModel;
import mozgovoy.nikita.diploma.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepo extends JpaRepository<Review, Long> {
    void deleteReviewById(Long id);
    Review findReviewById(Long id);
    Page<Review> findReviewsByFilmId(Long filmId, Pageable pageable);
    List<Review> findAllByFilmId(Long filmId);
    Review findReviewByFilmIdAndAuthorId(Long filmId, Long authorId);
}
