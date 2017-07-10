package com.projects.restcontrollers;

import com.projects.api.response.TheatreInformationResponse;
import com.projects.entities.TheatresEntity.Theatre;
import com.projects.exceptions.MyResourceNotFoundException;
import com.projects.service.TheatresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Controller for /theatre endpoint. Deals with {@link Theatre} and their derivatives
 */
@RestController
@RequestMapping(path = "/theatre")
public class TheatreController {
    @Autowired
    private TheatresService theatresService;

    /**
     * Gives the {@link Theatre#rows} and {@link Theatre#columns} for a given {@link Theatre#id}
     * @param theatreId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, path = "/{theatreId}")
    public ResponseEntity<TheatreInformationResponse> getTheatreInformation(final @PathVariable Long theatreId){
        final Optional<Theatre> theatreOpt = theatresService.getTheatreFromId(theatreId);

        final Theatre theatre = theatreOpt.orElseThrow(() -> new MyResourceNotFoundException("Theatre with id " + theatreId + " not found"));

        return theatresService.getTheatreInformation(theatre);
    }
}
