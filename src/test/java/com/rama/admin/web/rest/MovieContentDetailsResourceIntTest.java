package com.rama.admin.web.rest;

import com.rama.admin.RamaadminApp;

import com.rama.admin.domain.MovieContentDetails;
import com.rama.admin.repository.MovieContentDetailsRepository;
import com.rama.admin.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MovieContentDetailsResource REST controller.
 *
 * @see MovieContentDetailsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RamaadminApp.class)
public class MovieContentDetailsResourceIntTest {

    private static final String DEFAULT_DIRECTOR = "AAAAAAAAAA";
    private static final String UPDATED_DIRECTOR = "BBBBBBBBBB";

    private static final Integer DEFAULT_DURATION = 1;
    private static final Integer UPDATED_DURATION = 2;

    private static final LocalDate DEFAULT_RELEASE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RELEASE_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private MovieContentDetailsRepository movieContentDetailsRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMovieContentDetailsMockMvc;

    private MovieContentDetails movieContentDetails;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MovieContentDetailsResource movieContentDetailsResource = new MovieContentDetailsResource(movieContentDetailsRepository);
        this.restMovieContentDetailsMockMvc = MockMvcBuilders.standaloneSetup(movieContentDetailsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MovieContentDetails createEntity(EntityManager em) {
        MovieContentDetails movieContentDetails = new MovieContentDetails()
            .director(DEFAULT_DIRECTOR)
            .duration(DEFAULT_DURATION)
            .releaseDate(DEFAULT_RELEASE_DATE);
        return movieContentDetails;
    }

    @Before
    public void initTest() {
        movieContentDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createMovieContentDetails() throws Exception {
        int databaseSizeBeforeCreate = movieContentDetailsRepository.findAll().size();

        // Create the MovieContentDetails
        restMovieContentDetailsMockMvc.perform(post("/api/movie-content-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movieContentDetails)))
            .andExpect(status().isCreated());

        // Validate the MovieContentDetails in the database
        List<MovieContentDetails> movieContentDetailsList = movieContentDetailsRepository.findAll();
        assertThat(movieContentDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        MovieContentDetails testMovieContentDetails = movieContentDetailsList.get(movieContentDetailsList.size() - 1);
        assertThat(testMovieContentDetails.getDirector()).isEqualTo(DEFAULT_DIRECTOR);
        assertThat(testMovieContentDetails.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testMovieContentDetails.getReleaseDate()).isEqualTo(DEFAULT_RELEASE_DATE);
    }

    @Test
    @Transactional
    public void createMovieContentDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = movieContentDetailsRepository.findAll().size();

        // Create the MovieContentDetails with an existing ID
        movieContentDetails.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMovieContentDetailsMockMvc.perform(post("/api/movie-content-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movieContentDetails)))
            .andExpect(status().isBadRequest());

        // Validate the MovieContentDetails in the database
        List<MovieContentDetails> movieContentDetailsList = movieContentDetailsRepository.findAll();
        assertThat(movieContentDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMovieContentDetails() throws Exception {
        // Initialize the database
        movieContentDetailsRepository.saveAndFlush(movieContentDetails);

        // Get all the movieContentDetailsList
        restMovieContentDetailsMockMvc.perform(get("/api/movie-content-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(movieContentDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].director").value(hasItem(DEFAULT_DIRECTOR.toString())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)))
            .andExpect(jsonPath("$.[*].releaseDate").value(hasItem(DEFAULT_RELEASE_DATE.toString())));
    }

    @Test
    @Transactional
    public void getMovieContentDetails() throws Exception {
        // Initialize the database
        movieContentDetailsRepository.saveAndFlush(movieContentDetails);

        // Get the movieContentDetails
        restMovieContentDetailsMockMvc.perform(get("/api/movie-content-details/{id}", movieContentDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(movieContentDetails.getId().intValue()))
            .andExpect(jsonPath("$.director").value(DEFAULT_DIRECTOR.toString()))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION))
            .andExpect(jsonPath("$.releaseDate").value(DEFAULT_RELEASE_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMovieContentDetails() throws Exception {
        // Get the movieContentDetails
        restMovieContentDetailsMockMvc.perform(get("/api/movie-content-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMovieContentDetails() throws Exception {
        // Initialize the database
        movieContentDetailsRepository.saveAndFlush(movieContentDetails);
        int databaseSizeBeforeUpdate = movieContentDetailsRepository.findAll().size();

        // Update the movieContentDetails
        MovieContentDetails updatedMovieContentDetails = movieContentDetailsRepository.findOne(movieContentDetails.getId());
        updatedMovieContentDetails
            .director(UPDATED_DIRECTOR)
            .duration(UPDATED_DURATION)
            .releaseDate(UPDATED_RELEASE_DATE);

        restMovieContentDetailsMockMvc.perform(put("/api/movie-content-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMovieContentDetails)))
            .andExpect(status().isOk());

        // Validate the MovieContentDetails in the database
        List<MovieContentDetails> movieContentDetailsList = movieContentDetailsRepository.findAll();
        assertThat(movieContentDetailsList).hasSize(databaseSizeBeforeUpdate);
        MovieContentDetails testMovieContentDetails = movieContentDetailsList.get(movieContentDetailsList.size() - 1);
        assertThat(testMovieContentDetails.getDirector()).isEqualTo(UPDATED_DIRECTOR);
        assertThat(testMovieContentDetails.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testMovieContentDetails.getReleaseDate()).isEqualTo(UPDATED_RELEASE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingMovieContentDetails() throws Exception {
        int databaseSizeBeforeUpdate = movieContentDetailsRepository.findAll().size();

        // Create the MovieContentDetails

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMovieContentDetailsMockMvc.perform(put("/api/movie-content-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movieContentDetails)))
            .andExpect(status().isCreated());

        // Validate the MovieContentDetails in the database
        List<MovieContentDetails> movieContentDetailsList = movieContentDetailsRepository.findAll();
        assertThat(movieContentDetailsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMovieContentDetails() throws Exception {
        // Initialize the database
        movieContentDetailsRepository.saveAndFlush(movieContentDetails);
        int databaseSizeBeforeDelete = movieContentDetailsRepository.findAll().size();

        // Get the movieContentDetails
        restMovieContentDetailsMockMvc.perform(delete("/api/movie-content-details/{id}", movieContentDetails.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MovieContentDetails> movieContentDetailsList = movieContentDetailsRepository.findAll();
        assertThat(movieContentDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MovieContentDetails.class);
        MovieContentDetails movieContentDetails1 = new MovieContentDetails();
        movieContentDetails1.setId(1L);
        MovieContentDetails movieContentDetails2 = new MovieContentDetails();
        movieContentDetails2.setId(movieContentDetails1.getId());
        assertThat(movieContentDetails1).isEqualTo(movieContentDetails2);
        movieContentDetails2.setId(2L);
        assertThat(movieContentDetails1).isNotEqualTo(movieContentDetails2);
        movieContentDetails1.setId(null);
        assertThat(movieContentDetails1).isNotEqualTo(movieContentDetails2);
    }
}
