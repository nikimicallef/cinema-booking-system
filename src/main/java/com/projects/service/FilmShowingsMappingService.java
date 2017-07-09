package com.projects.service;

import com.projects.entities.FilmShowingsEntity.FilmShowing;
import com.projects.entities.FilmShowingsMappingEntity;
import com.projects.entities.FilmsEntity.Film;

import java.util.List;

/**
 * Service for the {@link FilmShowingsMappingEntity}
 */
public interface FilmShowingsMappingService {
    /**
     * Returns the {@link FilmShowing#id} for a specific {@link Film#id}
     * @param filmId Represents the {@link Film#id} to retrieve the {@link FilmShowing}
     * @return
     */
    List<Long> getIdsOfShowingsForFilmId(Long filmId);
}
