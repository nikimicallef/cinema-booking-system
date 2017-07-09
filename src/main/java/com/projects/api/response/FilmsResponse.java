package com.projects.api.response;

import com.projects.entities.FilmsEntity.Film;

import java.util.List;

/**
 * Shows all the {@link Film} available
 */
public class FilmsResponse {
    private List<Film> films;

    public FilmsResponse(final List<Film> films) {
        this.films = films;
    }

    public List<Film> getFilms() {
        return films;
    }
}
