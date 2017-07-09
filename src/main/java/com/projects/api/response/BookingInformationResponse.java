package com.projects.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.projects.entities.BookingsEntity.Booking;

/**
 * Shows the {@link Booking#seatRow} and {@link Booking#seatColumn} for a {@link Booking}
 */
@JsonPropertyOrder({"seat_row", "seat_column"})
public class BookingInformationResponse {
    @JsonProperty("seat_row")
    private Integer seatRow;
    @JsonProperty("seat_column")
    private Integer seatColumn;

    public BookingInformationResponse(final Integer seatRow, final Integer seatColumn) {
        this.seatRow = seatRow;
        this.seatColumn = seatColumn;
    }

    public Integer getSeatRow() {
        return seatRow;
    }

    public Integer getSeatColumn() {
        return seatColumn;
    }
}
