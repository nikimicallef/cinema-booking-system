package com.projects.service;

import com.projects.api.response.ShowingWithBookingsResponse;
import com.projects.entities.BookingsEntity.Booking;
import com.projects.entities.FilmShowingsEntity;
import com.projects.entities.FilmShowingsEntity.FilmShowing;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

/**
 * Service for the {@link FilmShowingsEntity}
 */
public interface FilmShowingsService {
    /**
     * Retrieves a {@link FilmShowing} given a {@link FilmShowing#id}
     * @param showingId Represents the {@link FilmShowing#id} to retrieve the {@link FilmShowing}
     * @return {@link Optional} of {@link FilmShowing}. Contents will be null if no {@link FilmShowing} exists for the given {@link FilmShowing#id}
     */
    Optional<FilmShowing> getFilmShowingById(Long showingId);

    /**
     * Retrieves all {@link Booking#seatRow} and {@link Booking#seatColumn} for each {@link Booking} related to the given {@link FilmShowing}
     * @param filmShowing Used to get all {@link Booking} for this {@link FilmShowing}
     * @return
     */
    ResponseEntity<ShowingWithBookingsResponse> getShowingWithBookings(FilmShowing filmShowing);
}
