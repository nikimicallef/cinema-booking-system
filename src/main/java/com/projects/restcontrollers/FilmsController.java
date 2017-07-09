package com.projects.restcontrollers;

import com.projects.api.response.FilmShowingsWithDatesResponse;
import com.projects.api.response.FilmsResponse;
import com.projects.entities.FilmShowingsEntity;
import com.projects.entities.FilmShowingsEntity.FilmShowing;
import com.projects.entities.FilmsEntity;
import com.projects.entities.FilmsEntity.Film;
import com.projects.exceptions.MyResourceNotFoundException;
import com.projects.service.FilmShowingsService;
import com.projects.service.FilmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * Controller for /films endpoint. Deals with {@link FilmsEntity} and their derivatives
 */
@RestController
@RequestMapping("/films")
public class FilmsController {
    @Autowired
    private FilmsService filmsService;

    /**
     * Gets all {@link Film}
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<FilmsResponse> getAllFilms(){
        return filmsService.getAllFilms();
    }

    /**
     * Gets all {@link FilmShowing} for a given {@link Film#id}.
     * Throws {@link MyResourceNotFoundException} if {@link Film} for given {@link Film#id} is not found.
     * @param filmId Represents the {@link Film#id}.
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, path = "/{filmId}/showings")
    public ResponseEntity<FilmShowingsWithDatesResponse> getFutureFilmShowingsDate(final @PathVariable("filmId") Long filmId) {
        final Optional<Film> filmFromId = filmsService.getFilmFromId(filmId);

        final Film film = filmFromId.orElseThrow(() -> new MyResourceNotFoundException("Film with ID " + filmId + " not found."));

        return filmsService.getFutureShowingsWithDateForFilm(film);
    }
}
