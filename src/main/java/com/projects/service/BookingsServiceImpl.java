package com.projects.service;

import com.projects.api.request.NewBookingsRequest;
import com.projects.api.request.NewBookingsRequest.NewBooking;
import com.projects.api.response.BookingIdsResponse;
import com.projects.api.response.BookingInformationResponse;
import com.projects.api.response.ShowingWithBookingsResponse;
import com.projects.entities.BookingsEntity;
import com.projects.entities.BookingsEntity.Booking;
import com.projects.entities.FilmShowingsEntity.FilmShowing;
import com.projects.entities.TheatresEntity.Theatre;
import com.projects.exceptions.MyResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class BookingsServiceImpl implements BookingsService {
    @Autowired
    private BookingsEntity bookingsEntity;

    @Autowired
    private FilmShowingsService filmShowingsService;

    @Autowired
    private TheatresService theatresService;

    /**
     * {@inheritDoc}
     * @param bookingId Represents the {@link Booking#id} to retrieve the {@link Booking}
     * @return
     */
    public Optional<Booking> getBookingFromId(final Long bookingId){
        return Optional.ofNullable(bookingsEntity.getBookings().get(bookingId));
    }

    /**
     * {@inheritDoc}
     * @param booking {@link Booking#id} to extract details from
     * @return
     */
    public BookingInformationResponse getSeatRowAndColumnForBooking(final Booking booking){
        return new BookingInformationResponse(booking.getSeatRow(), booking.getSeatColumn());
    }

    /**
     * {@inheritDoc}
     * @param newBookingsRequest Details of {@link Booking} to create
     * @return
     */
    public ResponseEntity<BookingIdsResponse> createNewBookings(final NewBookingsRequest newBookingsRequest){
        //TODO: Concurrency concerns for this method. If two requests enter this section at the same time then there will be a lack of data consistency.
        //To solve this implement locking (i.e. synchronized on the entity's get method) so only one request will use this section at the same time.

        final Long filmShowingId = newBookingsRequest.getFilmShowingId();

        final Optional<FilmShowing> filmShowingOpt = filmShowingsService.getFilmShowingById(filmShowingId);

        final FilmShowing filmShowing = filmShowingOpt.orElseThrow(() -> new MyResourceNotFoundException("Showing with ID " + filmShowingId + " not found."));

        //TODO: Instead of going through n seats for m bookings (complexity n*m), save the metadata in a map (key:seatRow, value: setOfBookedSeatColumns)

        //Checks if all seats fit in the theatre
        if(invalidSeats(newBookingsRequest.getNewBookings(), filmShowing)){
            throw new IllegalArgumentException("One or more of the seats is invalid for the given theatre");
        }

        //Checks if a booking already exists for that seat
        if(seatsForBookingTaken(newBookingsRequest, filmShowing)){
            throw new IllegalArgumentException("One or more of the seats is already booked");
        }

        //Creating new booking
        final List<Booking> newBookings
                = newBookingsRequest.getNewBookings().stream()
                                                     .map(bookingInfo -> createNewBooking(bookingsEntity.getBookings(),
                                                                                          filmShowingId,
                                                                                          bookingInfo))
                                                     .collect(Collectors.toList());

        final List<Long> newBookingIds = newBookings.stream().map(Booking::getId).collect(Collectors.toList());

        //Adding booking references to film showing
        filmShowing.getBookingIds().addAll(newBookingIds);

        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(new BookingIdsResponse(newBookingIds));
    }

    private Booking createNewBooking(final Map<Long, Booking> existingBookings, final Long filmShowingId, final NewBooking bookingInfo) {
        final Booking newBooking = bookingsEntity.new Booking(Long.valueOf(bookingsEntity.getBookings().size()),
                                                              filmShowingId,
                                                              bookingInfo.getSeatRow(),
                                                              bookingInfo.getSeatColumn());

        existingBookings.put(newBooking.getId(), newBooking);

        return newBooking;
    }

    private boolean invalidSeats(final List<NewBooking> newBookingRequests, final FilmShowing filmShowing) {
        final Long theatreId = filmShowing.getTheatreId();

        final Optional<Theatre> theatreOpt = theatresService.getTheatreFromId(theatreId);

        final Theatre theatre = theatreOpt.orElseThrow(() -> new IllegalArgumentException("Theatre with ID " + theatreId + " does not exist"));

        final Map<Boolean, List<NewBooking>> invalidSeats
                = newBookingRequests.stream()
                                    .collect(Collectors.partitioningBy(newBookingRequest -> newBookingRequest.getSeatColumn() < 1
                                                                                            || newBookingRequest.getSeatColumn() > theatre.getColumns()
                                                                                            || newBookingRequest.getSeatRow() < 1
                                                                                            || newBookingRequest.getSeatRow() > theatre.getRows()));

        return invalidSeats.get(true).size() > 0;
    }

    private boolean seatsForBookingTaken(final NewBookingsRequest newBookingsRequest, final FilmShowing filmShowing) {
        final ShowingWithBookingsResponse showingWithBookings = filmShowingsService.getShowingWithBookings(filmShowing).getBody();

        final Map<Boolean, Long> invalidSeatsForBooking
                = newBookingsRequest.getNewBookings().stream()
                                                     .map(newBookingRequest -> seatAlreadyTaken(showingWithBookings.getBookings(),
                                                                                                newBookingRequest.getSeatColumn(),
                                                                                                newBookingRequest.getSeatRow()))
                                                     .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return invalidSeatsForBooking.keySet().contains(true);
    }

    private boolean seatAlreadyTaken(final List<BookingInformationResponse> existingBookings, final Integer seatColumn, final Integer seatRow) {
        final Map<Boolean, List<BookingInformationResponse>> matchingBookings
                = existingBookings.stream()
                                  .collect(Collectors.partitioningBy(existingBooking -> existingBooking.getSeatColumn().equals(seatColumn)
                                                                                        && existingBooking.getSeatRow().equals(seatRow)));

        return matchingBookings.get(true).size() > 0;
    }
}
