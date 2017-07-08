package com.projects.entities;

import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a group of {@link Booking} instances.
 */
@Configuration
public class Bookings {
    private Map<Long, Booking> bookings = Collections.synchronizedMap(new HashMap<>());

    public Bookings() {}

    public Map<Long, Booking> getBookings() {
        return bookings;
    }

    public synchronized void setBookings(final Map<Long, Booking> bookings) {
        this.bookings = bookings;
    }

    public static class Booking{
        private Integer id;
        private Long filmShowingId;
        private Integer seatRow;
        private Integer seatColumn;
        private Boolean cancelled;

        public Booking() {}

        public Booking(final Integer id, final Long filmShowingId, final Integer seatRow, final Integer seatColumn, final Boolean cancelled) {
            this.id = id;
            this.filmShowingId = filmShowingId;
            this.seatRow = seatRow;
            this.seatColumn = seatColumn;
            this.cancelled = cancelled;
        }

        public Integer getId() {
            return id;
        }

        public void setId(final Integer id) {
            this.id = id;
        }

        public Long getFilmShowingId() {
            return filmShowingId;
        }

        public void setFilmShowingId(final Long filmShowingId) {
            this.filmShowingId = filmShowingId;
        }

        public Integer getSeatRow() {
            return seatRow;
        }

        public void setSeatRow(final Integer seatRow) {
            this.seatRow = seatRow;
        }

        public Integer getSeatColumn() {
            return seatColumn;
        }

        public void setSeatColumn(final Integer seatColumn) {
            this.seatColumn = seatColumn;
        }

        public Boolean getCancelled() {
            return cancelled;
        }

        public void setCancelled(final Boolean cancelled) {
            this.cancelled = cancelled;
        }

        @Override
        public String toString() {
            return "Booking{" +
                    "id=" + id +
                    ", filmShowingId=" + filmShowingId +
                    ", seatRow=" + seatRow +
                    ", seatColumn=" + seatColumn +
                    ", cancelled=" + cancelled +
                    '}';
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            final Booking booking = (Booking) o;

            return id.equals(booking.id);
        }

        @Override
        public int hashCode() {
            return id.hashCode();
        }
    }
}
