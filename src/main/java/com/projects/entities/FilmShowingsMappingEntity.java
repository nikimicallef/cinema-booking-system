package com.projects.entities;

import com.projects.entities.FilmShowingsEntity.FilmShowing;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.projects.entities.FilmsEntity.Film;

import java.util.List;
import java.util.Map;

/**
 * Meta data for the easy retrieval of each film's showings.
 * This collection is populated via a YAML file and no additions should be made during runtime.
 *
 * Map Key: {@link Film#id}
 * Value: {@link List} of {@link FilmShowing#id}
 */
@Configuration
@ConfigurationProperties(prefix="default-film-showing-mapping")
public class FilmShowingsMappingEntity {
    private Map<Long, List<Long>> filmShowingMapping;

    public FilmShowingsMappingEntity() {}

    public Map<Long, List<Long>> getFilmShowingMapping() {
        return filmShowingMapping;
    }

    public void setFilmShowingMapping(final Map<Long, List<Long>> filmShowingMapping) {
        this.filmShowingMapping = filmShowingMapping;
    }
}
