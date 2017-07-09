package com.projects.service;

import com.projects.entities.FilmShowingsEntity.FilmShowing;
import com.projects.entities.FilmShowingsMappingEntity;
import com.projects.entities.FilmsEntity.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmShowingsMappingServiceImpl implements FilmShowingsMappingService {
    @Autowired
    private FilmShowingsMappingEntity filmShowingsMappingEntity;

    /**
     * {@inheritDoc}
     * @param filmId Represents the {@link Film#id} to retrieve the {@link FilmShowing}
     * @return
     */
    @Override
    public List<Long> getIdsOfShowingsForFilmId(final Long filmId){
        return filmShowingsMappingEntity.getFilmShowingMapping().get(filmId);
    }
}
