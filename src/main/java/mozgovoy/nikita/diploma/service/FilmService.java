package mozgovoy.nikita.diploma.service;

import mozgovoy.nikita.diploma.exception.FilmNotFoundException;
import mozgovoy.nikita.diploma.model.Film;
import mozgovoy.nikita.diploma.repository.FilmRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmService {
    private final FilmRepo filmRepo;

    @Autowired
    public FilmService(FilmRepo filmRepo) {
        this.filmRepo = filmRepo;
    }

    public Film addFilm(Film film){
        return filmRepo.save(film);
    }

    public List<Film> findAllFilms(){
        return filmRepo.findAll();
    }

    public Film updateFilm(Film film, Long id){
        Film updatedFilm = filmRepo.findFilmById(id);
        updatedFilm.setCountry(film.getCountry());
        updatedFilm.setName(film.getName());
        updatedFilm.setDirector(film.getDirector());
        updatedFilm.setYear(film.getYear());
        updatedFilm.setGenres(film.getGenres());
        updatedFilm.setUrl(film.getUrl());
        return filmRepo.save(updatedFilm);
    }

    public void deleteFilm(Long id){
        filmRepo.deleteById(id);
    }

    public Film findFilmById(Long id){
        try{
            return filmRepo.findFilmById(id);
        }
        catch (Exception e){
            throw new FilmNotFoundException("Film by id " + id + "wasn't found");
        }
    }

    public Film findFilmByUrl(String url){
        return filmRepo.findFilmByUrl(url);
    }
}
