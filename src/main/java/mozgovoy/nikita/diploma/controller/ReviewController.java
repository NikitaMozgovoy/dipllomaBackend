package mozgovoy.nikita.diploma.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
@RequestMapping("/reviews")
public class ReviewController {
//    private final ReviewService reviewService;

//    @Autowired
//    public ReviewController(ReviewService reviewService,
//                            FilmRepo filmRepo) {
//        this.reviewService = reviewService;
//        this.filmRepo = filmRepo;
//    }
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
//    @PostMapping("/add/{filmId}")
//    public ResponseEntity<Review> addReview(@RequestBody Review review, @PathVariable("filmId") Long filmId){
//        Review newReview = reviewService.addReview(review, filmId);
//        return new ResponseEntity<>(newReview, HttpStatus.CREATED);
//    }
//
//    @PutMapping("/update/{id}")
//    public ResponseEntity<Review> updateReview(@RequestBody Review review, @PathVariable("id") Long id){
//        Review updatedReview = reviewService.updateReview(review, id);
//        return new ResponseEntity<>(updatedReview, HttpStatus.OK);
//    }
//
//    @Transactional
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<?> deleteReview(@PathVariable("id") Long id){
//        reviewService.deleteReview(id);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    @GetMapping("/{filmId}")
//    public ResponseEntity<List<ReviewDTO>> getFilmReviews(@PathVariable("filmId") Long filmId){
//        List<ReviewDTO> reviews = reviewService.findReviewsByFilm(filmId).stream()
//                .map(ReviewDTO:: new)
//                .collect(Collectors.toList());
//        return new ResponseEntity<>(reviews, HttpStatus.OK);
//    }
}
