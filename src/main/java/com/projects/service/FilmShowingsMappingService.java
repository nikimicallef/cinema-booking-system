package com.projects.service;

import com.projects.entities.FilmShowingsEntity.FilmShowing;
import com.projects.entities.FilmShowingsMappingEntity;
import com.projects.entities.FilmsEntity.Film;

import java.util.List;
import java.util.Optional;

/**
 * Service for the {@link FilmShowingsMappingEntity}
 */
public interface FilmShowingsMappingService {
    /**
     * Returns the {@link FilmShowing#id} for a specific {@link Film#id}
     * @param filmId Represents the {@link Film#id} to retrieve the {@link FilmShowing}
     * @return {@link Optional} of {@link List}. Contents will be null if no {@link Film} has no {@link FilmShowing}
     */
    Optional<List<Long>> getIdsOfShowingsForFilmId(Long filmId);
}
