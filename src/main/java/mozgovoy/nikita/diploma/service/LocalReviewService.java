package mozgovoy.nikita.diploma.service;

import mozgovoy.nikita.diploma.dto.LocalReviewDTO;
import mozgovoy.nikita.diploma.exception.ReviewNotFoundException;
import mozgovoy.nikita.diploma.model.Review;
import mozgovoy.nikita.diploma.repository.ReviewRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LocalReviewService {
    private final ReviewRepo reviewRepo;
    private final UserModelServiceImpl userModelService;

    @Autowired
    public LocalReviewService(ReviewRepo reviewRepo, UserModelServiceImpl userModelService) {
        this.reviewRepo = reviewRepo;
        this.userModelService = userModelService;
    }

    public LocalReviewDTO addReview(LocalReviewDTO reviewDTO, Long filmId){
        Review review = new Review();
        review.setFilmId(filmId);
        review.setText(reviewDTO.getText());
        review.setAuthor(userModelService.findUserById(reviewDTO.getAuthor().getId()));
        reviewRepo.save(review);
        return new LocalReviewDTO(review);
    }


    public LocalReviewDTO updateReview(LocalReviewDTO review, Long id){
        Review updatedReview = reviewRepo.findReviewById(id);
        updatedReview.setText(review.getText());
        reviewRepo.save(updatedReview);
        return new LocalReviewDTO(updatedReview);
    }

    public void deleteReview(Long id){
        reviewRepo.deleteReviewById(id);
    }

    public Review findReviewById(Long id){
        try{
            return reviewRepo.findReviewById(id);
        }
        catch (Exception e){
            throw new ReviewNotFoundException("Review by id " + id + "wasn't found");
        }
    }

    public List<LocalReviewDTO> findFilmReviews(Long filmId, Integer page){
        Pageable pageable = PageRequest.of(page-1, 5);
        Page<Review> reviews = reviewRepo.findReviewsByFilmId(filmId, pageable);
        List<Review> listOfReviews = reviews.getContent();
        List<LocalReviewDTO> content = LocalReviewDTO.getDTOArray(listOfReviews);

        return content;
    }

}
