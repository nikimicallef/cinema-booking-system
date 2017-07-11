package com.projects.service;

import com.projects.api.response.TheatreInformationResponse;
import com.projects.entities.TheatresEntity;
import com.projects.entities.TheatresEntity.Theatre;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TheatreServiceImplUnitTests {
    @Mock
    private TheatresEntity theatresEntity;
    @InjectMocks
    private TheatresServiceImpl theatresService;
    private Theatre dummyTheatre;
    private Map<Long, Theatre> dummyTheatresEntity;
    private static final Long DUMMY_THEATRE_ID = 1L;
    private static final Integer DUMMY_THEATRE_ROWS = 1;
    private static final Integer DUMMY_THEATRE_COLUMNS = 1;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        dummyTheatre = new Theatre();
        dummyTheatre.setId(DUMMY_THEATRE_ID);
        dummyTheatre.setRows(DUMMY_THEATRE_ROWS);
        dummyTheatre.setColumns(DUMMY_THEATRE_COLUMNS);

        dummyTheatresEntity = Collections.singletonMap(DUMMY_THEATRE_ID, dummyTheatre);
    }

    @Test
    public void getTheatreFromId_noTheatreForId_optionalEmpty() {
        when(theatresEntity.getTheatres()).thenReturn(new HashMap<>());

        final Optional<Theatre> theatre = theatresService.getTheatreFromId(DUMMY_THEATRE_ID);

        assertFalse(theatre.isPresent());
        verify(theatresEntity).getTheatres();
    }

    @Test
    public void getTheatreFromId_theatreForIdExists_optionalNotEmpty(){
        when(theatresEntity.getTheatres()).thenReturn(dummyTheatresEntity);

        final Optional<Theatre> theatre = theatresService.getTheatreFromId(DUMMY_THEATRE_ID);

        assertTrue(theatre.isPresent());
        assertEquals(DUMMY_THEATRE_ID, theatre.get().getId());
        assertEquals(DUMMY_THEATRE_ROWS, theatre.get().getRows());
        assertEquals(DUMMY_THEATRE_COLUMNS, theatre.get().getColumns());
        verify(theatresEntity).getTheatres();
    }

    @Test
    public void getTheatreInformation_idRowsAndColumnsOk_correctResponseEntity(){
        final ResponseEntity<TheatreInformationResponse> theatreInformation = theatresService.getTheatreInformation(dummyTheatre);

        assertEquals(HttpStatus.OK, theatreInformation.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, theatreInformation.getHeaders().getContentType());
        assertEquals(DUMMY_THEATRE_ID, theatreInformation.getBody().getTheatreId());
        assertEquals(DUMMY_THEATRE_ROWS, theatreInformation.getBody().getRows());
        assertEquals(DUMMY_THEATRE_COLUMNS, theatreInformation.getBody().getColumns());
    }
}
