package com.projects.service;

import com.projects.api.response.FilmShowingsWithDatesResponse;
import com.projects.api.response.FilmsResponse;
import com.projects.entities.FilmShowingsEntity.FilmShowing;
import com.projects.entities.FilmsEntity;
import com.projects.entities.FilmsEntity.Film;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FilmServiceImplUnitTests {
    @Mock
    private FilmsEntity filmsEntity;
    @Mock
    private FilmShowingsMappingService filmShowingsMappingService;
    @Mock
    private FilmShowingsService filmShowingsService;
    @InjectMocks
    private FilmsServiceImpl filmsService;
    private Film dummyFilm;
    private Map<Long, Film> dummyFilmEntity;
    private List<Long> dummyListOfShowingIdsForFilm;
    private FilmShowing dummyFilmShowing;
    private static final Long DUMMY_FILM_ID = 1L;
    private static final Long DUMMY_SHOWING_ID = 1L;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        dummyFilm = new Film();
        dummyFilm.setId(DUMMY_FILM_ID);

        dummyFilmEntity = Collections.singletonMap(DUMMY_FILM_ID, dummyFilm);

        dummyListOfShowingIdsForFilm = Collections.singletonList(DUMMY_SHOWING_ID);

        dummyFilmShowing = new FilmShowing();
        dummyFilmShowing.setId(DUMMY_SHOWING_ID);
        dummyFilmShowing.setFilmId(DUMMY_FILM_ID);
        dummyFilmShowing.setDateTime(LocalDateTime.now().plusDays(1L));
    }

    @Test
    public void getFilmFromId_noFilmForId_optionalEmpty() {
        when(filmsEntity.getFilms()).thenReturn(new HashMap<>());

        final Optional<Film> theatre = filmsService.getFilmFromId(DUMMY_FILM_ID);

        assertFalse(theatre.isPresent());
        verify(filmsEntity).getFilms();
    }

    @Test
    public void getFilmFromId_filmExistsForId_optionalNotEmpty() {
        when(filmsEntity.getFilms()).thenReturn(dummyFilmEntity);

        final Optional<Film> theatre = filmsService.getFilmFromId(DUMMY_FILM_ID);

        assertTrue(theatre.isPresent());
        assertEquals(DUMMY_FILM_ID, theatre.get().getId());
        verify(filmsEntity).getFilms();
    }

    @Test
    public void getAllFilms_noFilms_noFilmsInBody(){
        when(filmsEntity.getFilms()).thenReturn(new HashMap<>());

        final ResponseEntity<FilmsResponse> allFilmsResponseEntity = filmsService.getAllFilms();

        assertEquals(HttpStatus.OK, allFilmsResponseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, allFilmsResponseEntity.getHeaders().getContentType());
        assertEquals(0, allFilmsResponseEntity.getBody().getFilms().size());
        verify(filmsEntity).getFilms();
    }

    @Test
    public void getAllFilms_twoFilms_filmidsInBody(){
        when(filmsEntity.getFilms()).thenReturn(dummyFilmEntity);

        final ResponseEntity<FilmsResponse> allFilmsResponseEntity = filmsService.getAllFilms();

        assertEquals(HttpStatus.OK, allFilmsResponseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, allFilmsResponseEntity.getHeaders().getContentType());
        assertEquals(1, allFilmsResponseEntity.getBody().getFilms().size());
        assertTrue(allFilmsResponseEntity.getBody().getFilms().contains(dummyFilm));
        verify(filmsEntity).getFilms();
    }

    @Test
    public void getFutureShowingsWithDateForFilm_filmIdNotFound_showingsBodyEmpty(){
        when(filmShowingsMappingService.getIdsOfShowingsForFilmId(DUMMY_FILM_ID)).thenReturn(Optional.empty());

        final ResponseEntity<FilmShowingsWithDatesResponse> futureShowingsWithDateForFilm
                                        = filmsService.getFutureShowingsWithDateForFilm(dummyFilm);

        assertEquals(HttpStatus.OK, futureShowingsWithDateForFilm.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, futureShowingsWithDateForFilm.getHeaders().getContentType());
        assertEquals(0, futureShowingsWithDateForFilm.getBody().getShowings().size());
        assertEquals(DUMMY_FILM_ID, futureShowingsWithDateForFilm.getBody().getFilmId());
        verify(filmShowingsMappingService).getIdsOfShowingsForFilmId(anyLong());
    }

    @Test
    public void getFutureShowingsWithDateForFilm_showingNotFound_showingsBodyEmpty(){
        when(filmShowingsMappingService.getIdsOfShowingsForFilmId(DUMMY_FILM_ID)).thenReturn(Optional.of(dummyListOfShowingIdsForFilm));
        when(filmShowingsService.getFilmShowingById(DUMMY_SHOWING_ID)).thenReturn(Optional.empty());

        final ResponseEntity<FilmShowingsWithDatesResponse> futureShowingsWithDateForFilm
                = filmsService.getFutureShowingsWithDateForFilm(dummyFilm);

        assertEquals(HttpStatus.OK, futureShowingsWithDateForFilm.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, futureShowingsWithDateForFilm.getHeaders().getContentType());
        assertEquals(0, futureShowingsWithDateForFilm.getBody().getShowings().size());
        assertEquals(DUMMY_FILM_ID, futureShowingsWithDateForFilm.getBody().getFilmId());
        verify(filmShowingsMappingService).getIdsOfShowingsForFilmId(anyLong());
        verify(filmShowingsService).getFilmShowingById(anyLong());
    }

    @Test
    public void getFutureShowingsWithDateForFilm_showingExistsButInThePast_showingsBodyEmpty(){
        dummyFilmShowing = new FilmShowing();
        dummyFilmShowing.setDateTime(LocalDateTime.now().minusDays(1L));

        when(filmShowingsMappingService.getIdsOfShowingsForFilmId(DUMMY_FILM_ID)).thenReturn(Optional.of(dummyListOfShowingIdsForFilm));
        when(filmShowingsService.getFilmShowingById(DUMMY_SHOWING_ID)).thenReturn(Optional.of(dummyFilmShowing));

        final ResponseEntity<FilmShowingsWithDatesResponse> futureShowingsWithDateForFilm
                = filmsService.getFutureShowingsWithDateForFilm(dummyFilm);

        assertEquals(HttpStatus.OK, futureShowingsWithDateForFilm.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, futureShowingsWithDateForFilm.getHeaders().getContentType());
        assertEquals(0, futureShowingsWithDateForFilm.getBody().getShowings().size());
        assertEquals(DUMMY_FILM_ID, futureShowingsWithDateForFilm.getBody().getFilmId());
        verify(filmShowingsMappingService).getIdsOfShowingsForFilmId(anyLong());
        verify(filmShowingsService).getFilmShowingById(anyLong());
    }

    @Test
    public void getFutureShowingsWithDateForFilm_showingExistsInTheFuture_showingsBodyEmpty(){
        when(filmShowingsMappingService.getIdsOfShowingsForFilmId(DUMMY_FILM_ID)).thenReturn(Optional.of(dummyListOfShowingIdsForFilm));
        when(filmShowingsService.getFilmShowingById(DUMMY_SHOWING_ID)).thenReturn(Optional.of(dummyFilmShowing));

        final ResponseEntity<FilmShowingsWithDatesResponse> futureShowingsWithDateForFilm
                = filmsService.getFutureShowingsWithDateForFilm(dummyFilm);

        assertEquals(HttpStatus.OK, futureShowingsWithDateForFilm.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, futureShowingsWithDateForFilm.getHeaders().getContentType());
        assertEquals(1, futureShowingsWithDateForFilm.getBody().getShowings().size());
        assertEquals(DUMMY_SHOWING_ID, futureShowingsWithDateForFilm.getBody().getShowings().get(0).getShowingId());
        assertEquals(DUMMY_FILM_ID, futureShowingsWithDateForFilm.getBody().getFilmId());
        verify(filmShowingsMappingService).getIdsOfShowingsForFilmId(anyLong());
        verify(filmShowingsService).getFilmShowingById(anyLong());
    }
}
