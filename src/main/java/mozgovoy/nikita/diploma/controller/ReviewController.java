package mozgovoy.nikita.diploma.controller;

import jakarta.transaction.Transactional;
import mozgovoy.nikita.diploma.model.Film;
import mozgovoy.nikita.diploma.model.Review;
import mozgovoy.nikita.diploma.model.Review;
import mozgovoy.nikita.diploma.repository.FilmRepo;
import mozgovoy.nikita.diploma.service.FilmService;
import mozgovoy.nikita.diploma.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;
    private final FilmRepo filmRepo;

    @Autowired
    public ReviewController(ReviewService reviewService,
                            FilmRepo filmRepo) {
        this.reviewService = reviewService;
        this.filmRepo = filmRepo;
    }

    @GetMapping("")
    public ResponseEntity<List<Review>> getAllReviews(){
        return new ResponseEntity<>(reviewService.findAllFilmReviews(), HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable("id") Long id){
        Review review = reviewService.findReviewById(id);
        return new ResponseEntity<>(review, HttpStatus.OK);
    }

    @PostMapping("/add/{filmId}")
    public ResponseEntity<Review> addReview(@RequestBody Review review, @PathVariable("filmId") Long filmId){
        Review newReview = reviewService.addReview(review, filmId);
        return new ResponseEntity<>(newReview, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Review> updateReview(@RequestBody Review review, @PathVariable("id") Long id){
        Review updatedReview = reviewService.updateReview(review, id);
        return new ResponseEntity<>(updatedReview, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable("id") Long id){
        reviewService.deleteReview(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{filmId}")
    public ResponseEntity<List<Review>> getFilmReviews(@PathVariable("filmId") Long filmId){
        return new ResponseEntity<>(reviewService.findReviewsByFilm(filmId), HttpStatus.OK);
    }
}
