package mozgovoy.nikita.diploma.repository;

import mozgovoy.nikita.diploma.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmRepo  extends JpaRepository<Film, Long> {
    Film findFilmById(Long id);
    Film findFilmByUrl(String url);
}
