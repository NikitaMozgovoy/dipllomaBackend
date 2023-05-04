package mozgovoy.nikita.diploma.service;

import mozgovoy.nikita.diploma.exception.ReviewNotFoundException;
import mozgovoy.nikita.diploma.model.Review;
import mozgovoy.nikita.diploma.repository.ReviewRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepo reviewRepo;

    @Autowired
    public ReviewService(ReviewRepo reviewRepo) {
        this.reviewRepo = reviewRepo;
    }

//    public Review addReview(Review review, Long id){
//        review.setFilm(filmRepo.findFilmById(id));
////        Optional<CustomUser> customUserOptional = userRepo.findCustomUserByEmail(review.getEmail());
////        if (customUserOptional.isPresent()){
////            throw new IllegalStateException("email taken");
////        }
//        return reviewRepo.save(review);
//    }
//
//    public List<Review> findAllFilmReviews(){
//        return reviewRepo.findAll();
//    }
//
//    public Review updateReview(Review review, Long id){
//        Review updatedReview = reviewRepo.findReviewById(id);
//        updatedReview.setText(review.getText());
//        return reviewRepo.save(updatedReview);
//    }
//
//    public void deleteReview(Long id){
//        reviewRepo.deleteReviewsById(id);
//    }
//
//    public Review findReviewById(Long id){
//        try{
//            return reviewRepo.findReviewById(id);
//        }
//        catch (Exception e){
//            throw new ReviewNotFoundException("Review by id " + id + "wasn't found");
//        }
//    }
//
//    public List<Review> findReviewsByFilm(Long filmId){
//        return reviewRepo.findReviewsByFilm(filmRepo.findFilmById(filmId));
//    }
}
