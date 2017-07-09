package com.projects.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.projects.entities.BookingsEntity.Booking;
import com.projects.entities.FilmShowingsEntity.FilmShowing;

import java.util.ArrayList;
import java.util.List;

/**
 * Shows the {@link Booking#seatRow} and {@link Booking#seatColumn} for each {@link Booking} of a {@link FilmShowing}
 */
@JsonPropertyOrder({"showing_id", "bookings"})
public class ShowingWithBookingsResponse {
    @JsonProperty("showing_id")
    private Long showingId;
    private List<BookingInformationResponse> bookings;

    public ShowingWithBookingsResponse(final Long showingId) {
        this.showingId = showingId;
        bookings = new ArrayList<>();
    }

    public Long getShowingId() {
        return showingId;
    }

    public List<BookingInformationResponse> getBookings() {
        return bookings;
    }
}
