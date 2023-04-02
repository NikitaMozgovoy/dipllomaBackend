package mozgovoy.nikita.diploma.controller;

import jakarta.transaction.Transactional;
import mozgovoy.nikita.diploma.model.Film;
import mozgovoy.nikita.diploma.model.Film;
import mozgovoy.nikita.diploma.repository.FilmRepo;
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

    @GetMapping("")
    public ResponseEntity<List<Film>> getAllFilms(){
        return new ResponseEntity<>(filmService.findAllFilms(), HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Film> getFilmById(@PathVariable("id") Long id){
        Film film = filmService.findFilmById(id);
        return new ResponseEntity<>(film, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Film> addUser(@RequestBody Film user){
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
    public ResponseEntity<Film> getFilmByUrl(@PathVariable("url") String filmUrl){
        Film film = filmService.findFilmByUrl(filmUrl);
        return new ResponseEntity<>(film, HttpStatus.OK);
    }

}
