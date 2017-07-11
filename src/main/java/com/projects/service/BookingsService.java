package com.projects.service;

import com.projects.api.request.NewBookingsRequest;
import com.projects.api.request.NewBookingsRequest.NewBooking;
import com.projects.api.response.BookingIdsResponse;
import com.projects.api.response.BookingInformationResponse;
import com.projects.entities.BookingsEntity;
import com.projects.entities.BookingsEntity.Booking;
import com.projects.entities.TheatresEntity.Theatre;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

/**
 * Service for the {@link BookingsEntity}
 */
public interface BookingsService {
    /**
     * Retrieves a {@link Booking} given a {@link Booking#id}
     * @param bookingId Represents the {@link Booking#id} to retrieve the {@link Booking}
     * @return {@link Optional} of {@link Booking}. Contents will be null if no {@link Booking} exists for the given {@link Booking#id}
     */
    Optional<Booking> getBookingFromId(Long bookingId);

    /**
     * Returns the {@link Booking#seatRow} and {@link Booking#seatColumn} for a specific {@link Booking}
     * @param booking {@link Booking#id} to extract details from
     * @return
     */
    BookingInformationResponse getSeatRowAndColumnForBooking(Booking booking);

    /**
     * Creates all necessary new bookings for a given {@link NewBookingsRequest}.
     * Checks that the provided {@link NewBookingsRequest#filmShowingId} is valid along with the provided
     *      {@link NewBooking#seatColumn} and {@link NewBooking#seatRow} are valid for the {@link Theatre}
     * @param newBookingsRequest Details of {@link Booking} to create
     * @return
     */
    ResponseEntity<BookingIdsResponse> createNewBookings(NewBookingsRequest newBookingsRequest);
}
