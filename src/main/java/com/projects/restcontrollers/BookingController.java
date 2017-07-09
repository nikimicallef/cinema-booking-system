package com.projects.restcontrollers;

import com.projects.api.request.NewBookingsRequest.NewBooking;
import com.projects.entities.BookingsEntity.Booking;
import com.projects.api.request.NewBookingsRequest;
import com.projects.api.response.BookingIdsResponse;
import com.projects.entities.BookingsEntity;
import com.projects.service.BookingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Controller for the /booking endpoint. Deals with {@link BookingsEntity} and its derivatives
 */
@RestController
@RequestMapping(path = "/booking")
public class BookingController {
    @Autowired
    private BookingsService bookingsService;

    /**
     * Creates a new {@link Booking} with the given values
     * @param newBookingsRequest Contains details for the new booking including {@link NewBookingsRequest#filmShowingId}
 *                               along with the{@link NewBooking#seatRow} and {@link NewBooking#seatColumn} for each booking.
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<BookingIdsResponse> createNewBooking(final @RequestBody @Valid NewBookingsRequest newBookingsRequest){
        return bookingsService.createNewBookings(newBookingsRequest);
    }
}
