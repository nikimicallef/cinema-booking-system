package com.projects.service;

import com.projects.api.response.BookingInformationResponse;
import com.projects.api.response.ShowingWithBookingsResponse;
import com.projects.entities.BookingsEntity.Booking;
import com.projects.entities.FilmShowingsEntity;
import com.projects.entities.FilmShowingsEntity.FilmShowing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FilmShowingsServiceImp implements FilmShowingsService {
    @Autowired
    private FilmShowingsEntity filmShowingsEntity;

    @Autowired
    private BookingsService bookingsService;

    /**
     * {@inheritDoc}
     * @param showingId Represents the {@link FilmShowing#id} to retrieve the {@link FilmShowing}
     * @return
     */
    @Override
    public Optional<FilmShowing> getFilmShowingById(final Long showingId){
        return Optional.ofNullable(filmShowingsEntity.getFilmShowings().get(showingId));
    }

    /**
     * {@inheritDoc}
     * @param filmShowing Used to get all {@link Booking} for this {@link FilmShowing}
     * @return
     */
    @Override
    public ResponseEntity<ShowingWithBookingsResponse> getShowingWithBookings(final FilmShowing filmShowing){
        final ShowingWithBookingsResponse showingWithBookingsResponse = new ShowingWithBookingsResponse(filmShowing.getId());

        final List<BookingInformationResponse> bookingsInformation
                = filmShowing.getBookingIds().stream()
                                             .map(bookingId -> bookingsService.getSeatRowAndColumnForBooking(bookingId))
                                             .collect(Collectors.toList());

        showingWithBookingsResponse.getBookings().addAll(bookingsInformation);

        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(showingWithBookingsResponse);
    }
}
