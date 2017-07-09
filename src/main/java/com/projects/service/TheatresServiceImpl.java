package com.projects.service;

import com.projects.entities.TheatresEntity;
import com.projects.entities.TheatresEntity.Theatre;
import org.springframework.beans.factory.annotation.Autowired;
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
}
