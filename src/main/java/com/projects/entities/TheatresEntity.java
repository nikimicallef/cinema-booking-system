package com.projects.entities;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * Represents a group of {@link Theatre} instances.
 * This collection is populated via a YAML file and no additions should be made during runtime.
 *
 * Map Key: {@link Theatre#id}
 * Value: {@link Theatre} instance
 */
@Configuration
@ConfigurationProperties(prefix="default-theatres")
public class TheatresEntity {
    private Map<Long, Theatre> theatres;

    public TheatresEntity() {}

    public Map<Long, Theatre> getTheatres() {
        return theatres;
    }

    public void setTheatres(final Map<Long, Theatre> theatres) {
        this.theatres = theatres;
    }

    public static class Theatre {
        private Long id;
        /**
         * Represents number of rows starting from 1, not 0.
         */
        private Integer rows;
        /**
         * Represents number of columns starting from 1, not 0.
         */
        private Integer columns;

        public Theatre() {
        }

        public Long getId() {
            return id;
        }

        public void setId(final Long id) {
            this.id = id;
        }

        public Integer getRows() {
            return rows;
        }

        public void setRows(final Integer rows) {
            this.rows = rows;
        }

        public Integer getColumns() {
            return columns;
        }

        public void setColumns(final Integer columns) {
            this.columns = columns;
        }

        @Override
        public String toString() {
            return "Theatre{" +
                    "id=" + id +
                    ", rows=" + rows +
                    ", columns=" + columns +
                    '}';
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            final Theatre theatre = (Theatre) o;

            return id.equals(theatre.id);
        }

        @Override
        public int hashCode() {
            return id.hashCode();
        }
    }
}
