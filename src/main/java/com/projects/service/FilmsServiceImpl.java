package com.projects.service;

import com.projects.api.response.FilmShowingWithDateResponse;
import com.projects.api.response.FilmShowingsWithDatesResponse;
import com.projects.api.response.FilmsResponse;
import com.projects.entities.FilmShowingsEntity;
import com.projects.entities.FilmsEntity;
import com.projects.entities.FilmsEntity.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FilmsServiceImpl implements FilmsService{
    @Autowired
    private FilmsEntity filmsEntity;

    @Autowired
    private FilmShowingsMappingService filmShowingsMappingService;

    @Autowired
    private FilmShowingsService filmShowingsService;

    /**
     * {@inheritDoc}
     * @param filmId Represents the {@link Film#id} to retrieve the {@link Film}
     * @return
     */
    @Override
    public Optional<Film> getFilmFromId(final Long filmId) {
        return Optional.ofNullable(filmsEntity.getFilms().get(filmId));
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public ResponseEntity<FilmsResponse> getAllFilms() {
        final FilmsResponse filmsResponse = new FilmsResponse(new ArrayList<>(filmsEntity.getFilms().values()));

        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(filmsResponse);
    }

    /**
     * {@inheritDoc}
     * @param film Represents the {@link Film} to retrieve the {@link FilmShowingsEntity.FilmShowing} from
     * @return
     */
    @Override
    public ResponseEntity<FilmShowingsWithDatesResponse> getFutureShowingsWithDateForFilm(final Film film) {
        final FilmShowingsWithDatesResponse filmShowingsWithDatesResponse = new FilmShowingsWithDatesResponse(film.getId());

        final List<Long> filmShowingIdsForFilmId = filmShowingsMappingService.getIdsOfShowingsForFilmId(film.getId()).orElse(new ArrayList<>());

        final List<FilmShowingWithDateResponse> filmShowingsForFilmId
                    = filmShowingIdsForFilmId.stream()
                                             .map(filmShowingId -> filmShowingsService.getFilmShowingById(filmShowingId))
                                             .filter(Optional::isPresent)
                                             .map(Optional::get)
                                             .filter(filmShowing -> filmShowing.getDateTime().isAfter(LocalDateTime.now()))
                                             .map(filmShowing -> new FilmShowingWithDateResponse(filmShowing.getId(), filmShowing.getDateTimeStr()))
                                             .collect(Collectors.toList());

        filmShowingsWithDatesResponse.getShowings().addAll(filmShowingsForFilmId);

        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(filmShowingsWithDatesResponse);
    }
}
