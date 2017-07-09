package com.projects.entities;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * Represents a group of {@link Film} instances.
 * This collection is populated via a YAML file and no additions should be made during runtime.
 *
 * Map key: {@link Film#id}
 * Values: {@link Film} instance
 */
@Configuration
@ConfigurationProperties(prefix="default-films")
public class FilmsEntity {
    private Map<Long, Film> films;

    public FilmsEntity() {}

    public Map<Long, Film> getFilms() {
        return films;
    }

    public void setFilms(final Map<Long, Film> films) {
        this.films = films;
    }

    public static class Film {
        //TODO: Remove the FilmShowingsMappingEntity class and add an attribute here which maps from the film to the showings
        private Long id;
        private String filmName;
        private String description;

        public Film() {}

        public Long getId() {
            return id;
        }

        public void setId(final Long id) {
            this.id = id;
        }

        public String getFilmName() {
            return filmName;
        }

        public void setFilmName(final String filmName) {
            this.filmName = filmName;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(final String description) {
            this.description = description;
        }

        @Override
        public String toString() {
            return "Film{" +
                    "id=" + id +
                    ", filmName='" + filmName + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            final Film film = (Film) o;

            return id.equals(film.id);
        }

        @Override
        public int hashCode() {
            return id.hashCode();
        }
    }
}
