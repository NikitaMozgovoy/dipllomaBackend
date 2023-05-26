package mozgovoy.nikita.diploma.controller;

import jakarta.transaction.Transactional;
import mozgovoy.nikita.diploma.dto.KinopoiskReviewDTO;
import mozgovoy.nikita.diploma.dto.LocalReviewDTO;
import mozgovoy.nikita.diploma.model.Review;
import mozgovoy.nikita.diploma.service.ExternalReviewsService;
import mozgovoy.nikita.diploma.service.LocalReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
@RequestMapping("/reviews")
public class ReviewController {
    private final LocalReviewService reviewService;
    private final ExternalReviewsService externalReviewsService;
    @Autowired
    public ReviewController(LocalReviewService reviewService, ExternalReviewsService externalReviewsService) {
        this.reviewService = reviewService;
        this.externalReviewsService = externalReviewsService;
    }
//
//    @GetMapping("")
//    public ResponseEntity<List<Review>> getAllReviews(){
//        return new ResponseEntity<>(reviewService.findAllFilmReviews(), HttpStatus.OK);
//    }
//
//    @GetMapping("/find/{id}")
//    public ResponseEntity<Review> getReviewById(@PathVariable("id") Long id){
//        Review review = reviewService.findReviewById(id);
//        return new ResponseEntity<>(review, HttpStatus.OK);
//    }
//
    @PostMapping("/{filmId}/add")
    public ResponseEntity<LocalReviewDTO> addReview(@RequestBody LocalReviewDTO review, @PathVariable("filmId") Long filmId){
        return new ResponseEntity<>(reviewService.addReview(review, filmId), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<LocalReviewDTO> updateReview(@RequestBody LocalReviewDTO review, @PathVariable("id") Long id){
        System.out.println(review);
        return new ResponseEntity<>(reviewService.updateReview(review, id), HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteReview(@PathVariable("id") Long id){
        reviewService.deleteReview(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<KinopoiskReviewDTO>> getKinopoiskReviews(@RequestParam Long filmId, @RequestParam Integer page){
        return new ResponseEntity<>(externalReviewsService.getKinopoiskReviews(filmId,page), HttpStatus.OK);
    }

}
