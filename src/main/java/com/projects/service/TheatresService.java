package com.projects.service;

import com.projects.entities.TheatresEntity;
import com.projects.entities.TheatresEntity.Theatre;

import java.util.Optional;

/**
 * Service for the {@link TheatresEntity}
 */
public interface TheatresService {
    /**
     * Retrieves a {@link Theatre} given a {@link Theatre#id}
     * @param theatreId Represents the {@link Theatre#id} to retrieve the {@link Theatre}
     * @return {@link Optional} of {@link Theatre}. Contents will be null if no {@link Theatre} exists for the given {@link Theatre#id}
     */
    Optional<Theatre> getTheatreFromId(Long theatreId);
}
