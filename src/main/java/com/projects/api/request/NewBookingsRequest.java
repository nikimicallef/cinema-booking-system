package com.projects.api.request;

import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * {@link RequestBody} representing a new booking
 */
public class NewBookingsRequest {
    @NotNull(message = "Film Showing ID can not be null")
    private Long filmShowingId;
    @NotNull(message = "A list of new bookings must be provided")
    private List<NewBooking> newBookings;

    public Long getFilmShowingId() {
        return filmShowingId;
    }

    public void setFilmShowingId(final Long filmShowingId) {
        this.filmShowingId = filmShowingId;
    }

    public List<NewBooking> getNewBookings() {
        return newBookings;
    }

    public void setNewBookings(final List<NewBooking> newBookings) {
        this.newBookings = newBookings;
    }

    public static class NewBooking {
        @NotNull(message = "Seat row can not be null")
        private Integer seatRow;
        @NotNull(message = "Seat column can not be null")
        private Integer seatColumn;

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
    }
}
