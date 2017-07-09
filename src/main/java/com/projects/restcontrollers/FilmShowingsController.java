package com.projects.restcontrollers;

import com.projects.api.response.ShowingWithBookingsResponse;
import com.projects.entities.BookingsEntity.Booking;
import com.projects.entities.FilmShowingsEntity.FilmShowing;
import com.projects.exceptions.MyResourceNotFoundException;
import com.projects.service.FilmShowingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * Controller for /films endpoint. Deals with {@link FilmShowing} and their derivatives
 */
@RestController
@RequestMapping(path = "/showings")
public class FilmShowingsController {
    @Autowired
    private FilmShowingsService filmShowingsService;

    /**
     * Retrieves all {@link Booking#seatRow} and {@link Booking#seatColumn} for each {@link Booking} related to the given {@link FilmShowing#id}
     * Throws {@link MyResourceNotFoundException} if {@link FilmShowing} for given {@link FilmShowing#id} is not found.
     * @param showingId Represents the {@link FilmShowing#id}
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, path = "/{showingId}")
    public ResponseEntity<ShowingWithBookingsResponse> getShowingInformationById(final @PathVariable Long showingId){
        final Optional<FilmShowing> filmShowingOpt = filmShowingsService.getFilmShowingById(showingId);

        final FilmShowing filmShowing = filmShowingOpt.orElseThrow(() -> new MyResourceNotFoundException("Showing with ID " + showingId + " not found."));

        return filmShowingsService.getShowingWithBookings(filmShowing);
    }
}
