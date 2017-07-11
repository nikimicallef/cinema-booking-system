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
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BookingServiceImplUnitTests {
    @Mock
    private BookingsEntity bookingsEntity;
    @Mock
    private FilmShowingsService filmShowingsService;
    @Mock
    private TheatresService theatresService;
    @InjectMocks
    private BookingsServiceImpl bookingsService;
    private Booking dummyBooking;
    private NewBookingsRequest dummyNewBookingsRequest;
    private NewBooking dummyNewBooking;
    private FilmShowing dummyFilmShowing;
    private Theatre dummyTheatre;
    private ShowingWithBookingsResponse dummyShowingWithBookingsResponse;
    private ResponseEntity<ShowingWithBookingsResponse> dummyShowingWithBookings;
    private static final Integer DUMMY_BOOKING_SEAT_ROW = 1;
    private static final Integer DUMMY_BOOKING_SEAT_COLUMN = 2;
    private static final Integer DUMMY_NEW_BOOKING_SEAT_ROW = 1;
    private static final Integer DUMMY_NEW_BOOKING_SEAT_COLUMN = 2;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        when(bookingsEntity.getBookings()).thenReturn(new HashMap<>());

        dummyBooking = bookingsEntity.new Booking(1L, 1L, DUMMY_BOOKING_SEAT_ROW, DUMMY_BOOKING_SEAT_COLUMN);

        dummyNewBooking = new NewBooking();
        dummyNewBooking.setSeatRow(DUMMY_NEW_BOOKING_SEAT_ROW);
        dummyNewBooking.setSeatColumn(DUMMY_NEW_BOOKING_SEAT_COLUMN);

        dummyNewBookingsRequest = new NewBookingsRequest();
        dummyNewBookingsRequest.setFilmShowingId(1L);
        dummyNewBookingsRequest.setNewBookings(Collections.singletonList(dummyNewBooking));

        dummyFilmShowing = new FilmShowing();
        dummyFilmShowing.setTheatreId(1L);

        dummyTheatre = new Theatre();
        dummyTheatre.setId(1L);
        dummyTheatre.setColumns(2);
        dummyTheatre.setRows(2);

        dummyShowingWithBookingsResponse = new ShowingWithBookingsResponse(1L, 1L);

        dummyShowingWithBookings = ResponseEntity.status(HttpStatus.OK).body(dummyShowingWithBookingsResponse);
    }

    @Test
    public void getBookingFromId_invalidId_optionalEmpty(){
        when(bookingsEntity.getBookings()).thenReturn(new HashMap<>());

        final Optional<Booking> bookingFromId = bookingsService.getBookingFromId(1L);

        assertFalse(bookingFromId.isPresent());
        verify(bookingsEntity).getBookings();
    }

    @Test
    public void getBookingFromId_validId_optionalNotEmpty(){
        final Map<Long, Booking> mockBookingsHashMap = new HashMap<>();
        mockBookingsHashMap.put(1L, dummyBooking);

        when(bookingsEntity.getBookings()).thenReturn(mockBookingsHashMap);

        final Optional<Booking> bookingFromId = bookingsService.getBookingFromId(1L);

        assertTrue(bookingFromId.isPresent());
        verify(bookingsEntity).getBookings();
    }

    @Test
    public void getSeatRowAndColumnForBooking_validBooking_rowAndColumnRetrievedCorrectly(){
        final BookingInformationResponse bookingInformation = bookingsService.getSeatRowAndColumnForBooking(dummyBooking);

        assertEquals(DUMMY_BOOKING_SEAT_ROW, bookingInformation.getSeatRow());
        assertEquals(DUMMY_BOOKING_SEAT_COLUMN, bookingInformation.getSeatColumn());
    }

    @Test(expected = MyResourceNotFoundException.class)
    public void createNewBookings_filmShowingDoesNotExist_myResourceNotFoundException(){
        when(filmShowingsService.getFilmShowingById(anyLong())).thenReturn(Optional.empty());

        bookingsService.createNewBookings(dummyNewBookingsRequest);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createNewBookings_theatreDoesNotExist_illegalArgumentException(){
        when(filmShowingsService.getFilmShowingById(anyLong())).thenReturn(Optional.of(dummyFilmShowing));
        when(theatresService.getTheatreFromId(anyLong())).thenReturn(Optional.empty());

        bookingsService.createNewBookings(dummyNewBookingsRequest);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createNewBookings_rowTooLargeForTheatre_illegalArgumentException(){
        dummyNewBooking = new NewBooking();
        dummyNewBooking.setSeatRow(5);
        dummyNewBooking.setSeatColumn(DUMMY_NEW_BOOKING_SEAT_COLUMN);

        dummyNewBookingsRequest.setNewBookings(Collections.singletonList(dummyNewBooking));

        when(filmShowingsService.getFilmShowingById(anyLong())).thenReturn(Optional.of(dummyFilmShowing));
        when(theatresService.getTheatreFromId(anyLong())).thenReturn(Optional.of(dummyTheatre));

        bookingsService.createNewBookings(dummyNewBookingsRequest);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createNewBookings_rowTooSmallForTheatre_illegalArgumentException(){
        dummyNewBooking = new NewBooking();
        dummyNewBooking.setSeatRow(-1);
        dummyNewBooking.setSeatColumn(DUMMY_NEW_BOOKING_SEAT_COLUMN);

        dummyNewBookingsRequest.setNewBookings(Collections.singletonList(dummyNewBooking));

        when(filmShowingsService.getFilmShowingById(anyLong())).thenReturn(Optional.of(dummyFilmShowing));
        when(theatresService.getTheatreFromId(anyLong())).thenReturn(Optional.of(dummyTheatre));

        bookingsService.createNewBookings(dummyNewBookingsRequest);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createNewBookings_columnTooLargeForTheatre_illegalArgumentException(){
        dummyNewBooking = new NewBooking();
        dummyNewBooking.setSeatRow(DUMMY_NEW_BOOKING_SEAT_ROW);
        dummyNewBooking.setSeatColumn(5);

        dummyNewBookingsRequest.setNewBookings(Collections.singletonList(dummyNewBooking));

        when(filmShowingsService.getFilmShowingById(anyLong())).thenReturn(Optional.of(dummyFilmShowing));
        when(theatresService.getTheatreFromId(anyLong())).thenReturn(Optional.of(dummyTheatre));

        bookingsService.createNewBookings(dummyNewBookingsRequest);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createNewBookings_columnTooSmallForTheatre_illegalArgumentException(){
        dummyNewBooking = new NewBooking();
        dummyNewBooking.setSeatRow(DUMMY_NEW_BOOKING_SEAT_ROW);
        dummyNewBooking.setSeatColumn(-1);

        dummyNewBookingsRequest.setNewBookings(Collections.singletonList(dummyNewBooking));

        when(filmShowingsService.getFilmShowingById(anyLong())).thenReturn(Optional.of(dummyFilmShowing));
        when(theatresService.getTheatreFromId(anyLong())).thenReturn(Optional.of(dummyTheatre));

        bookingsService.createNewBookings(dummyNewBookingsRequest);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createNewBookings_seatValidButBooked_illegalArgumentException(){
        final BookingInformationResponse dummyBookingInformationResponse = new BookingInformationResponse(DUMMY_NEW_BOOKING_SEAT_ROW,
                                                                                                          DUMMY_NEW_BOOKING_SEAT_COLUMN);
        dummyShowingWithBookingsResponse.getBookings().add(dummyBookingInformationResponse);
        dummyShowingWithBookings = ResponseEntity.status(HttpStatus.OK).body(dummyShowingWithBookingsResponse);

        when(filmShowingsService.getFilmShowingById(anyLong())).thenReturn(Optional.of(dummyFilmShowing));
        when(theatresService.getTheatreFromId(anyLong())).thenReturn(Optional.of(dummyTheatre));
        when(filmShowingsService.getShowingWithBookings(any(FilmShowing.class))).thenReturn(dummyShowingWithBookings);

        bookingsService.createNewBookings(dummyNewBookingsRequest);
    }

    @Test
    public void createNewBookings_seatValidAndNotBooked_illegalArgumentException(){
        when(filmShowingsService.getFilmShowingById(anyLong())).thenReturn(Optional.of(dummyFilmShowing));
        when(theatresService.getTheatreFromId(anyLong())).thenReturn(Optional.of(dummyTheatre));
        when(filmShowingsService.getShowingWithBookings(any(FilmShowing.class))).thenReturn(dummyShowingWithBookings);

        final ResponseEntity<BookingIdsResponse> newBookingsResponse = bookingsService.createNewBookings(dummyNewBookingsRequest);

        assertEquals(HttpStatus.OK, newBookingsResponse.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, newBookingsResponse.getHeaders().getContentType());
        assertEquals(1, newBookingsResponse.getBody().getBookingIds().size());
        assertEquals(0L, Long.parseLong(newBookingsResponse.getBody().getBookingIds().get(0).toString()));

        verify(filmShowingsService).getFilmShowingById(anyLong());
        verify(theatresService).getTheatreFromId(anyLong());
        verify(filmShowingsService).getShowingWithBookings(any(FilmShowing.class));
    }
}
