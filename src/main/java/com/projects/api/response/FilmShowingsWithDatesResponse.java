package com.projects.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.projects.entities.FilmShowingsEntity.FilmShowing;
import com.projects.entities.FilmsEntity.Film;

import java.util.ArrayList;
import java.util.List;

/**
 * Shows the {@link FilmShowing#id} and {@link FilmShowing#dateTimeStr} for each {@link Film}'s {@link FilmShowing}
 */
@JsonPropertyOrder({ "film_id", "showings" })
public class FilmShowingsWithDatesResponse {
    @JsonProperty("film_id")
    private Long filmId;
    private List<FilmShowingWithDateResponse> showings;

    public FilmShowingsWithDatesResponse(final Long filmId) {
        this.filmId = filmId;
        showings = new ArrayList<>();
    }

    public Long getFilmId() {
        return filmId;
    }

    public List<FilmShowingWithDateResponse> getShowings() {
        return showings;
    }
}
