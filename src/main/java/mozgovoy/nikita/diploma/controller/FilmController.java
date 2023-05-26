package mozgovoy.nikita.diploma.controller;

import mozgovoy.nikita.diploma.dto.FilmDTO;
import mozgovoy.nikita.diploma.dto.FilmsListDTO;
import mozgovoy.nikita.diploma.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }


    @GetMapping("/page/{page}")
    public ResponseEntity<List<FilmsListDTO>> getAllFilms(@PathVariable("page") int pageNumber, @RequestParam("limit") int limit){
        return new ResponseEntity<>(filmService.findAllFilms(limit, pageNumber), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FilmDTO> getFilmById(@PathVariable("id") Long id){
        return new ResponseEntity<>(filmService.getFilmById(id), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<FilmsListDTO>> getSearchResults(@RequestParam("query") String query, @RequestParam("page")
                                                          int page, @RequestParam("limit") int limit){
        return new ResponseEntity<>(filmService.getSearchResults(query, limit, page), HttpStatus.OK);
    }

}
