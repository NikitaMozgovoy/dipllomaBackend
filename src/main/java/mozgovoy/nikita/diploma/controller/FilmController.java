package mozgovoy.nikita.diploma.controller;

import jakarta.transaction.Transactional;
import mozgovoy.nikita.diploma.dto.FilmDTO;
import mozgovoy.nikita.diploma.dto.ReviewDTO;
import mozgovoy.nikita.diploma.model.Film;
import mozgovoy.nikita.diploma.model.Film;
import mozgovoy.nikita.diploma.repository.FilmRepo;
import mozgovoy.nikita.diploma.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("")
    public ResponseEntity<List<FilmDTO>> getAllFilms(){
        List<FilmDTO> films = filmService.findAllFilms().stream()
                .map(FilmDTO:: new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(films, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<FilmDTO> getFilmById(@PathVariable("id") Long id){
        FilmDTO film = new FilmDTO(filmService.findFilmById(id));
        return new ResponseEntity<>(film, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Film> addFilm(@RequestBody Film user){
        Film newFilm = filmService.addFilm(user);
        return new ResponseEntity<>(newFilm, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Film> updateFilm(@RequestBody Film film, @PathVariable("id") Long id){
        Film updatedFilm = filmService.updateFilm(film, id);
        return new ResponseEntity<>(updatedFilm, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteFilm(@PathVariable("id") Long id){
        filmService.deleteFilm(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{url}")
    public ResponseEntity<FilmDTO> getFilmByUrl(@PathVariable("url") String filmUrl){
        FilmDTO film = new FilmDTO(filmService.findFilmByUrl(filmUrl));
        return new ResponseEntity<>(film, HttpStatus.OK);
    }

}
