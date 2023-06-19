package mozgovoy.nikita.diploma.controller;

import com.google.gson.Gson;
import jakarta.transaction.Transactional;
import mozgovoy.nikita.diploma.dto.LocalReviewDTO;
import mozgovoy.nikita.diploma.repository.UserRepo;
import mozgovoy.nikita.diploma.service.ExternalReviewsService;
import mozgovoy.nikita.diploma.service.LocalReviewsService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
@RequestMapping("/reviews")
public class ReviewController {
    private final LocalReviewsService localReviewsService;
    private final ExternalReviewsService externalReviewsService;
    private final UserRepo userRepo;

    @Autowired
    public ReviewController(LocalReviewsService localReviewsService, ExternalReviewsService externalReviewsService,
                            UserRepo userRepo) {
        this.localReviewsService = localReviewsService;
        this.externalReviewsService = externalReviewsService;
        this.userRepo = userRepo;
    }

    @PostMapping("/{filmId}/add")
    public ResponseEntity<LocalReviewDTO> addReview(@RequestBody LocalReviewDTO review, @PathVariable("filmId") Long filmId){
        return new ResponseEntity<>(localReviewsService.addReview(review, filmId), HttpStatus.CREATED);
    }
    @PutMapping("/{id}/update")
    public ResponseEntity<LocalReviewDTO> updateReview(@RequestBody LocalReviewDTO review, @PathVariable("id") Long id){
        return new ResponseEntity<>(localReviewsService.updateReview(review, id), HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteReview(@PathVariable("id") Long id){
        localReviewsService.deleteReview(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/{filmId}/pages")
    public ResponseEntity<Integer> getReviewsPagesQuantity(@PathVariable("filmId")  Long filmId, @RequestParam String service){
        switch (service) {
            case ("local"): {
                return new ResponseEntity<>(localReviewsService.getReviewsPagesQuantity(filmId), HttpStatus.OK);
            }
            default:{
                return new ResponseEntity<>(externalReviewsService.getReviewsPagesQuantity(filmId, service), HttpStatus.OK);
            }
        }
    }
    @GetMapping("/{filmId}/current-user-review")
    public ResponseEntity<LocalReviewDTO> getUsersFilmReview(@PathVariable("filmId") Long filmId, @RequestParam Long userId){
        return new ResponseEntity<>(localReviewsService.getUsersFilmReview(filmId, userId), HttpStatus.OK);
    }

    @PostMapping("/translate")
    public ResponseEntity<?> translateReview(@RequestBody String reviews){
        JSONObject js = new JSONObject(reviews);
        new Gson().fromJson(String.valueOf(js.get("value")), ArrayList.class);
        this.externalReviewsService.setTranslatedReviews(new Gson().fromJson(String.valueOf(js.get("value")), ArrayList.class));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/setPages")
    public ResponseEntity<?> setReviewsPages(@RequestBody String pages){
        JSONObject js = new JSONObject(pages);
        HashMap<String, Double> map = new Gson().fromJson(String.valueOf(js), HashMap.class);
        ExternalReviewsService.pages.replace("kinopoiskPage", map.get("kinopoiskPage").intValue());
        ExternalReviewsService.pages.replace("tmdbPage", map.get("tmdbPage").intValue());
        this.localReviewsService.setPage((Integer) js.toMap().get("localPage"));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
