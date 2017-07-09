package com.projects.entities;

import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a group of {@link Booking} instances.
 *
 * Map key: {@link Booking#id}
 * Value: {@link Booking} instance
 */
@Configuration
public class BookingsEntity {
    private Map<Long, Booking> bookings = Collections.synchronizedMap(new HashMap<>());

    public BookingsEntity() {}

    public Map<Long, Booking> getBookings() {
        return bookings;
    }

    public synchronized void setBookings(final Map<Long, Booking> bookings) {
        this.bookings = bookings;
    }

    public class Booking{
        private Long id;
        private Long filmShowingId;
        /**
         * This is NOT zero based. If a theatre has 15 rows the first row has ID 1 and the last row has ID 15.
         */
        private Integer seatRow;
        /**
         * This is NOT zero based. If a theatre has 15 columns the first column has ID 1 and the last column has ID 15.
         */
        private Integer seatColumn;
        private Boolean cancelled;

        public Booking() {}

        public Booking(final Long id, final Long filmShowingId, final Integer seatRow, final Integer seatColumn) {
            this.id = id;
            this.filmShowingId = filmShowingId;
            this.seatRow = seatRow;
            this.seatColumn = seatColumn;
            this.cancelled = false;
        }

        public Long getId() {
            return id;
        }

        public void setId(final Long id) {
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
