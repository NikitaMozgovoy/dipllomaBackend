package mozgovoy.nikita.diploma.service;

import lombok.Setter;
import mozgovoy.nikita.diploma.dto.LocalReviewDTO;
import mozgovoy.nikita.diploma.exception.ReviewNotFoundException;
import mozgovoy.nikita.diploma.model.Review;
import mozgovoy.nikita.diploma.repository.ReviewRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Setter
public class LocalReviewsService {
    private final ReviewRepo reviewRepo;
    private final UserModelService userModelService;

    private Integer page = 1;

    @Autowired
    public LocalReviewsService(ReviewRepo reviewRepo, UserModelService userModelService) {
        this.reviewRepo = reviewRepo;
        this.userModelService = userModelService;
    }

    public LocalReviewDTO addReview(LocalReviewDTO reviewDTO, Long filmId){
        Review review = new Review();
        review.setFilmId(filmId);
        review.setText(reviewDTO.getText());
        review.setAuthor(userModelService.findUserById(reviewDTO.getAuthor().getId()));
        review.setRating(reviewDTO.getRating());
        reviewRepo.save(review);
        return new LocalReviewDTO(review);
    }


    public LocalReviewDTO updateReview(LocalReviewDTO review, Long id){
        Review updatedReview = findReviewById(id);
        updatedReview.setText(review.getText());
        updatedReview.setRating(review.getRating());
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

    public List<LocalReviewDTO> getFilmReviews(Long filmId){
        Pageable pageable = PageRequest.of(this.page-1, 5);
        Page<Review> reviews = reviewRepo.findReviewsByFilmId(filmId, pageable);
        List<Review> listOfReviews = reviews.getContent();
        List<LocalReviewDTO> content = LocalReviewDTO.getDTOArray(listOfReviews);

        return content;
    }

    public Integer getReviewsPagesQuantity(Long filmId){
        Integer num = reviewRepo.findAllByFilmId(filmId).size();
        return (int) ((num + 5 - 1 ) /5);
    }

    public LocalReviewDTO getUsersFilmReview(Long filmId, Long userId){
        Review res = reviewRepo.findReviewByFilmIdAndAuthorId(filmId, userId);
        if(res!=null) {
            return new LocalReviewDTO(res);
        }
        else{
            return null;
        }
    }
}
