package com.rama.admin.web.rest;

import com.rama.admin.RamaadminApp;

import com.rama.admin.domain.MovieContent;
import com.rama.admin.repository.MovieContentRepository;
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
 * Test class for the MovieContentResource REST controller.
 *
 * @see MovieContentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RamaadminApp.class)
public class MovieContentResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT_PATH = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT_PATH = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATION_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATION_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private MovieContentRepository movieContentRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMovieContentMockMvc;

    private MovieContent movieContent;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MovieContentResource movieContentResource = new MovieContentResource(movieContentRepository);
        this.restMovieContentMockMvc = MockMvcBuilders.standaloneSetup(movieContentResource)
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
    public static MovieContent createEntity(EntityManager em) {
        MovieContent movieContent = new MovieContent()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .contentPath(DEFAULT_CONTENT_PATH)
            .creationTime(DEFAULT_CREATION_TIME)
            .updateDate(DEFAULT_UPDATE_DATE);
        return movieContent;
    }

    @Before
    public void initTest() {
        movieContent = createEntity(em);
    }

    @Test
    @Transactional
    public void createMovieContent() throws Exception {
        int databaseSizeBeforeCreate = movieContentRepository.findAll().size();

        // Create the MovieContent
        restMovieContentMockMvc.perform(post("/api/movie-contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movieContent)))
            .andExpect(status().isCreated());

        // Validate the MovieContent in the database
        List<MovieContent> movieContentList = movieContentRepository.findAll();
        assertThat(movieContentList).hasSize(databaseSizeBeforeCreate + 1);
        MovieContent testMovieContent = movieContentList.get(movieContentList.size() - 1);
        assertThat(testMovieContent.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMovieContent.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMovieContent.getContentPath()).isEqualTo(DEFAULT_CONTENT_PATH);
        assertThat(testMovieContent.getCreationTime()).isEqualTo(DEFAULT_CREATION_TIME);
        assertThat(testMovieContent.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void createMovieContentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = movieContentRepository.findAll().size();

        // Create the MovieContent with an existing ID
        movieContent.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMovieContentMockMvc.perform(post("/api/movie-contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movieContent)))
            .andExpect(status().isBadRequest());

        // Validate the MovieContent in the database
        List<MovieContent> movieContentList = movieContentRepository.findAll();
        assertThat(movieContentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMovieContents() throws Exception {
        // Initialize the database
        movieContentRepository.saveAndFlush(movieContent);

        // Get all the movieContentList
        restMovieContentMockMvc.perform(get("/api/movie-contents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(movieContent.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].contentPath").value(hasItem(DEFAULT_CONTENT_PATH.toString())))
            .andExpect(jsonPath("$.[*].creationTime").value(hasItem(DEFAULT_CREATION_TIME.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())));
    }

    @Test
    @Transactional
    public void getMovieContent() throws Exception {
        // Initialize the database
        movieContentRepository.saveAndFlush(movieContent);

        // Get the movieContent
        restMovieContentMockMvc.perform(get("/api/movie-contents/{id}", movieContent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(movieContent.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.contentPath").value(DEFAULT_CONTENT_PATH.toString()))
            .andExpect(jsonPath("$.creationTime").value(DEFAULT_CREATION_TIME.toString()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMovieContent() throws Exception {
        // Get the movieContent
        restMovieContentMockMvc.perform(get("/api/movie-contents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMovieContent() throws Exception {
        // Initialize the database
        movieContentRepository.saveAndFlush(movieContent);
        int databaseSizeBeforeUpdate = movieContentRepository.findAll().size();

        // Update the movieContent
        MovieContent updatedMovieContent = movieContentRepository.findOne(movieContent.getId());
        updatedMovieContent
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .contentPath(UPDATED_CONTENT_PATH)
            .creationTime(UPDATED_CREATION_TIME)
            .updateDate(UPDATED_UPDATE_DATE);

        restMovieContentMockMvc.perform(put("/api/movie-contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMovieContent)))
            .andExpect(status().isOk());

        // Validate the MovieContent in the database
        List<MovieContent> movieContentList = movieContentRepository.findAll();
        assertThat(movieContentList).hasSize(databaseSizeBeforeUpdate);
        MovieContent testMovieContent = movieContentList.get(movieContentList.size() - 1);
        assertThat(testMovieContent.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMovieContent.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMovieContent.getContentPath()).isEqualTo(UPDATED_CONTENT_PATH);
        assertThat(testMovieContent.getCreationTime()).isEqualTo(UPDATED_CREATION_TIME);
        assertThat(testMovieContent.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingMovieContent() throws Exception {
        int databaseSizeBeforeUpdate = movieContentRepository.findAll().size();

        // Create the MovieContent

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMovieContentMockMvc.perform(put("/api/movie-contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movieContent)))
            .andExpect(status().isCreated());

        // Validate the MovieContent in the database
        List<MovieContent> movieContentList = movieContentRepository.findAll();
        assertThat(movieContentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMovieContent() throws Exception {
        // Initialize the database
        movieContentRepository.saveAndFlush(movieContent);
        int databaseSizeBeforeDelete = movieContentRepository.findAll().size();

        // Get the movieContent
        restMovieContentMockMvc.perform(delete("/api/movie-contents/{id}", movieContent.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MovieContent> movieContentList = movieContentRepository.findAll();
        assertThat(movieContentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MovieContent.class);
        MovieContent movieContent1 = new MovieContent();
        movieContent1.setId(1L);
        MovieContent movieContent2 = new MovieContent();
        movieContent2.setId(movieContent1.getId());
        assertThat(movieContent1).isEqualTo(movieContent2);
        movieContent2.setId(2L);
        assertThat(movieContent1).isNotEqualTo(movieContent2);
        movieContent1.setId(null);
        assertThat(movieContent1).isNotEqualTo(movieContent2);
    }
}
