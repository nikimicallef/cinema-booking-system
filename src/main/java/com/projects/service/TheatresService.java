package com.projects.service;

import com.projects.api.response.TheatreInformationResponse;
import com.projects.entities.TheatresEntity;
import com.projects.entities.TheatresEntity.Theatre;
import org.springframework.http.ResponseEntity;

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

    /**
     * Retrieves the {@link Theatre#rows} and {@link Theatre#columns} for a given {@link Theatre}
     * @param theatre
     * @return
     */
    ResponseEntity<TheatreInformationResponse> getTheatreInformation(Theatre theatre);
}
