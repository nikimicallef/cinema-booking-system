package com.projects.service;

import com.projects.entities.FilmShowingsMappingEntity;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FilmShowingsMappingServiceImplUnitTests {
    @Mock
    private FilmShowingsMappingEntity filmShowingsMappingEntity;
    @InjectMocks
    private FilmShowingsMappingServiceImpl filmShowingsMappingService;
    private Map<Long, List<Long>> dummyShowingIdsForMapping;
    private static final Long DUMMY_FILM_ID = 1L;
    private static final Long DUMMY_SHOWING_ID = 1L;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        dummyShowingIdsForMapping = Collections.singletonMap(DUMMY_FILM_ID, Collections.singletonList(DUMMY_SHOWING_ID));
    }

    @Test
    public void getIdsOfShowingsForFilmId_noShowingsForFilmId_optionalEmpty() {
        when(filmShowingsMappingEntity.getFilmShowingMapping()).thenReturn(new HashMap<>());

        final Optional<List<Long>> idsOfShowingsForFilmId = filmShowingsMappingService.getIdsOfShowingsForFilmId(DUMMY_FILM_ID);

        assertFalse(idsOfShowingsForFilmId.isPresent());
        verify(filmShowingsMappingEntity).getFilmShowingMapping();
    }

    @Test
    public void getIdsOfShowingsForFilmId_showingsForFilmIdExist_optionalEmpty(){
        when(filmShowingsMappingEntity.getFilmShowingMapping()).thenReturn(dummyShowingIdsForMapping);

        final Optional<List<Long>> idsOfShowingsForFilmId = filmShowingsMappingService.getIdsOfShowingsForFilmId(DUMMY_FILM_ID);

        assertTrue(idsOfShowingsForFilmId.isPresent());
        assertEquals(1, idsOfShowingsForFilmId.get().size());
        assertEquals(DUMMY_SHOWING_ID, idsOfShowingsForFilmId.get().get(0));
        verify(filmShowingsMappingEntity).getFilmShowingMapping();
    }
}
