package com.projects.service;

import com.projects.api.response.BookingInformationResponse;
import com.projects.api.response.ShowingWithBookingsResponse;
import com.projects.entities.BookingsEntity;
import com.projects.entities.BookingsEntity.Booking;
import com.projects.entities.FilmShowingsEntity;
import com.projects.entities.FilmShowingsEntity.FilmShowing;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FilmShowingsServiceImplUnitTests {
    @Mock
    private FilmShowingsEntity filmShowingsEntity;
    @Mock
    private BookingsService bookingsService;
    @Mock
    private BookingsEntity bookingsEntity;
    @InjectMocks
    private FilmShowingsServiceImp filmShowingsServiceImp;
    private FilmShowing dummyFilmShowing;
    private Map<Long, FilmShowing> dummyFilmShowings;
    private Booking dummyBooking;
    private BookingInformationResponse dummyBookingInformationResponse;
    private static final Long DUMMY_SHOWING_ID = 1L;
    private static final Long DUMMY_THEATRE_ID = 1L;
    private static final Long DUMMY_BOOKING_ID = 1L;
    private static final Integer DUMMY_SEAT_ROW = 1;
    private static final Integer DUMMY_SEAT_COLUMN = 1;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        dummyFilmShowing = new FilmShowing();
        dummyFilmShowing.setId(DUMMY_SHOWING_ID);
        dummyFilmShowing.setTheatreId(DUMMY_THEATRE_ID);
        dummyFilmShowing.setBookingIds(Collections.singletonList(DUMMY_BOOKING_ID));

        dummyFilmShowings = Collections.singletonMap(DUMMY_SHOWING_ID, dummyFilmShowing);

        dummyBooking = bookingsEntity.new Booking(DUMMY_BOOKING_ID, DUMMY_SHOWING_ID, DUMMY_SEAT_ROW, DUMMY_SEAT_COLUMN);

        dummyBookingInformationResponse = new BookingInformationResponse(DUMMY_SEAT_ROW, DUMMY_SEAT_COLUMN);
    }

    @Test
    public void getFilmShowingById_noShowingsForId_optionalEmpty() {
        when(filmShowingsEntity.getFilmShowings()).thenReturn(new HashMap<>());

        final Optional<FilmShowing> filmShowingById = filmShowingsServiceImp.getFilmShowingById(DUMMY_SHOWING_ID);

        assertFalse(filmShowingById.isPresent());
        verify(filmShowingsEntity).getFilmShowings();
    }

    @Test
    public void getFilmShowingById_showingForIdExists_optionalNotEmpty(){
        when(filmShowingsEntity.getFilmShowings()).thenReturn(dummyFilmShowings);

        final Optional<FilmShowing> filmShowing = filmShowingsServiceImp.getFilmShowingById(DUMMY_SHOWING_ID);

        assertTrue(filmShowing.isPresent());
        assertEquals(DUMMY_SHOWING_ID, filmShowing.get().getId());
        verify(filmShowingsEntity).getFilmShowings();
    }

    @Test
    public void getShowingWithBookings_bookingDoesNotExistForId_noBookings(){
        when(bookingsService.getBookingFromId(anyLong())).thenReturn(Optional.empty());

        final ResponseEntity<ShowingWithBookingsResponse> showingWithBookings = filmShowingsServiceImp
                                                                                    .getShowingWithBookings(dummyFilmShowing);

        assertEquals(HttpStatus.OK, showingWithBookings.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, showingWithBookings.getHeaders().getContentType());
        assertEquals(0, showingWithBookings.getBody().getBookings().size());

        verify(bookingsService).getBookingFromId(anyLong());
    }

    @Test
    public void getShowingWithBookings_bookingsExistForShowing_noBookings(){
        when(bookingsService.getBookingFromId(anyLong())).thenReturn(Optional.of(dummyBooking));
        when(bookingsService.getSeatRowAndColumnForBooking(dummyBooking)).thenReturn(dummyBookingInformationResponse);

        final ResponseEntity<ShowingWithBookingsResponse> showingWithBookings = filmShowingsServiceImp
                                                                                    .getShowingWithBookings(dummyFilmShowing);

        assertEquals(HttpStatus.OK, showingWithBookings.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, showingWithBookings.getHeaders().getContentType());
        assertEquals(1, showingWithBookings.getBody().getBookings().size());
        assertEquals(DUMMY_SEAT_ROW, showingWithBookings.getBody().getBookings().get(0).getSeatRow());
        assertEquals(DUMMY_SEAT_COLUMN, showingWithBookings.getBody().getBookings().get(0).getSeatColumn());

        verify(bookingsService).getBookingFromId(anyLong());
        verify(bookingsService).getSeatRowAndColumnForBooking(dummyBooking);
    }
}
