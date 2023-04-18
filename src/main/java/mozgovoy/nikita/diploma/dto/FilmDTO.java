package mozgovoy.nikita.diploma.dto;

import lombok.Data;
import mozgovoy.nikita.diploma.model.Film;
import mozgovoy.nikita.diploma.model.Genre;

import java.io.Serializable;
import java.util.List;

@Data
public class FilmDTO implements Serializable {
    private Long id;
    private String name;
    private Integer year;
    private String director;
    private String country;
    private String url;
    private String imageUrl;
    private List<Genre> genres;

    public FilmDTO(Film film) {
        this.id = film.getId();
        this.name = film.getName();;
        this.year = film.getYear();
        this.director = film.getDirector();
        this.country = film.getCountry();
        this.url = film.getUrl();
        this.imageUrl = film.getImageUrl();
        this.genres = film.getGenres();
    }
}
