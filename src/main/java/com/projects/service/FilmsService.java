package com.projects.service;

import com.projects.api.response.FilmShowingsWithDatesResponse;
import com.projects.api.response.FilmsResponse;
import com.projects.entities.FilmShowingsEntity.FilmShowing;
import com.projects.entities.FilmsEntity;
import com.projects.entities.FilmsEntity.Film;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Service for the {@link FilmsEntity}
 */
public interface FilmsService {
    /**
     * Retrieves a {@link Film} given a {@link Film#id}
     * @param filmId Represents the {@link Film#id} to retrieve the {@link Film}
     * @return {@link Optional} of {@link Film}. Contents will be null if no {@link Film} exists for the given {@link Film#id}
     */
    Optional<Film> getFilmFromId(Long filmId);

    /**
     * Returns all {@link Film}
     * @return
     */
    ResponseEntity<FilmsResponse> getAllFilms();

    /**
     * Retrieves the {@link FilmShowing#dateTimeStr} for all {@link FilmShowing} for a given {@link Film}.
     * It will only retrieve showings which are in the future i.e. {@link FilmShowing#dateTime} is after {@link LocalDateTime#now()}
     * @param film Represents the {@link Film} to retrieve the {@link FilmShowing} from
     * @return
     */
    ResponseEntity<FilmShowingsWithDatesResponse> getFutureShowingsWithDateForFilm(Film film);
}
