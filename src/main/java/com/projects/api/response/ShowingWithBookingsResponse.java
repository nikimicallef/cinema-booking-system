package com.projects.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.projects.entities.BookingsEntity.Booking;
import com.projects.entities.FilmShowingsEntity.FilmShowing;
import com.projects.entities.TheatresEntity.Theatre;

import java.util.ArrayList;
import java.util.List;

/**
 * Shows the {@link Booking#seatRow} and {@link Booking#seatColumn} for each {@link Booking} of a {@link FilmShowing} along with the {@link Theatre#id}
 */
@JsonPropertyOrder({"showing_id", "theatre_id", "bookings"})
public class ShowingWithBookingsResponse {
    @JsonProperty("showing_id")
    private Long showingId;
    @JsonProperty("theatre_id")
    private Long theatreId;
    private List<BookingInformationResponse> bookings;

    public ShowingWithBookingsResponse(final Long showingId, final Long theatreId) {
        this.showingId = showingId;
        this.theatreId = theatreId;
        bookings = new ArrayList<>();
    }

    public Long getShowingId() {
        return showingId;
    }

    public Long getTheatreId() {
        return theatreId;
    }

    public List<BookingInformationResponse> getBookings() {
        return bookings;
    }
}
