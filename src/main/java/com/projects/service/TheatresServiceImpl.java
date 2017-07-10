package com.projects.service;

import com.projects.api.response.TheatreInformationResponse;
import com.projects.entities.TheatresEntity;
import com.projects.entities.TheatresEntity.Theatre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TheatresServiceImpl implements TheatresService {
    @Autowired
    private TheatresEntity theatresEntity;

    /**
     * {@inheritDoc}
     * @param theatreId Represents the {@link Theatre#id} to retrieve the {@link Theatre}
     * @return
     */
    @Override
    public Optional<Theatre> getTheatreFromId(final Long theatreId) {
        return Optional.ofNullable(theatresEntity.getTheatres().get(theatreId));
    }

    /**
     * {@inheritDoc}
     * @param theatre
     * @return
     */
    public ResponseEntity<TheatreInformationResponse> getTheatreInformation(final Theatre theatre){
        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(new TheatreInformationResponse(theatre.getId(),
                                                                  theatre.getRows(),
                                                                  theatre.getColumns()));
    }
}
