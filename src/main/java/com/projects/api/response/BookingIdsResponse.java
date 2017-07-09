package com.projects.api.response;

import com.projects.entities.BookingsEntity.Booking;

import java.util.List;

/**
 * Used to show all {@link Booking#id} of newly created {@link Booking}
 */
public class BookingIdsResponse {
    private List<Long> bookingIds;

    public BookingIdsResponse(final List<Long> bookingIds) {
        this.bookingIds = bookingIds;
    }

    public List<Long> getBookingIds() {
        return bookingIds;
    }
}
