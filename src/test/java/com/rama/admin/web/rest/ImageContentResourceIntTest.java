package com.rama.admin.web.rest;

import com.rama.admin.RamaadminApp;

import com.rama.admin.domain.ImageContent;
import com.rama.admin.repository.ImageContentRepository;
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
 * Test class for the ImageContentResource REST controller.
 *
 * @see ImageContentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RamaadminApp.class)
public class ImageContentResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CAPTION = false;
    private static final Boolean UPDATED_CAPTION = true;

    private static final String DEFAULT_CONTENT_PATH = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT_PATH = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ImageContentRepository imageContentRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restImageContentMockMvc;

    private ImageContent imageContent;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ImageContentResource imageContentResource = new ImageContentResource(imageContentRepository);
        this.restImageContentMockMvc = MockMvcBuilders.standaloneSetup(imageContentResource)
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
    public static ImageContent createEntity(EntityManager em) {
        ImageContent imageContent = new ImageContent()
            .name(DEFAULT_NAME)
            .caption(DEFAULT_CAPTION)
            .contentPath(DEFAULT_CONTENT_PATH)
            .creationDate(DEFAULT_CREATION_DATE)
            .updateDate(DEFAULT_UPDATE_DATE);
        return imageContent;
    }

    @Before
    public void initTest() {
        imageContent = createEntity(em);
    }

    @Test
    @Transactional
    public void createImageContent() throws Exception {
        int databaseSizeBeforeCreate = imageContentRepository.findAll().size();

        // Create the ImageContent
        restImageContentMockMvc.perform(post("/api/image-contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imageContent)))
            .andExpect(status().isCreated());

        // Validate the ImageContent in the database
        List<ImageContent> imageContentList = imageContentRepository.findAll();
        assertThat(imageContentList).hasSize(databaseSizeBeforeCreate + 1);
        ImageContent testImageContent = imageContentList.get(imageContentList.size() - 1);
        assertThat(testImageContent.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testImageContent.isCaption()).isEqualTo(DEFAULT_CAPTION);
        assertThat(testImageContent.getContentPath()).isEqualTo(DEFAULT_CONTENT_PATH);
        assertThat(testImageContent.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testImageContent.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void createImageContentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = imageContentRepository.findAll().size();

        // Create the ImageContent with an existing ID
        imageContent.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restImageContentMockMvc.perform(post("/api/image-contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imageContent)))
            .andExpect(status().isBadRequest());

        // Validate the ImageContent in the database
        List<ImageContent> imageContentList = imageContentRepository.findAll();
        assertThat(imageContentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllImageContents() throws Exception {
        // Initialize the database
        imageContentRepository.saveAndFlush(imageContent);

        // Get all the imageContentList
        restImageContentMockMvc.perform(get("/api/image-contents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(imageContent.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].caption").value(hasItem(DEFAULT_CAPTION.booleanValue())))
            .andExpect(jsonPath("$.[*].contentPath").value(hasItem(DEFAULT_CONTENT_PATH.toString())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())));
    }

    @Test
    @Transactional
    public void getImageContent() throws Exception {
        // Initialize the database
        imageContentRepository.saveAndFlush(imageContent);

        // Get the imageContent
        restImageContentMockMvc.perform(get("/api/image-contents/{id}", imageContent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(imageContent.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.caption").value(DEFAULT_CAPTION.booleanValue()))
            .andExpect(jsonPath("$.contentPath").value(DEFAULT_CONTENT_PATH.toString()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingImageContent() throws Exception {
        // Get the imageContent
        restImageContentMockMvc.perform(get("/api/image-contents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateImageContent() throws Exception {
        // Initialize the database
        imageContentRepository.saveAndFlush(imageContent);
        int databaseSizeBeforeUpdate = imageContentRepository.findAll().size();

        // Update the imageContent
        ImageContent updatedImageContent = imageContentRepository.findOne(imageContent.getId());
        updatedImageContent
            .name(UPDATED_NAME)
            .caption(UPDATED_CAPTION)
            .contentPath(UPDATED_CONTENT_PATH)
            .creationDate(UPDATED_CREATION_DATE)
            .updateDate(UPDATED_UPDATE_DATE);

        restImageContentMockMvc.perform(put("/api/image-contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedImageContent)))
            .andExpect(status().isOk());

        // Validate the ImageContent in the database
        List<ImageContent> imageContentList = imageContentRepository.findAll();
        assertThat(imageContentList).hasSize(databaseSizeBeforeUpdate);
        ImageContent testImageContent = imageContentList.get(imageContentList.size() - 1);
        assertThat(testImageContent.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testImageContent.isCaption()).isEqualTo(UPDATED_CAPTION);
        assertThat(testImageContent.getContentPath()).isEqualTo(UPDATED_CONTENT_PATH);
        assertThat(testImageContent.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testImageContent.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingImageContent() throws Exception {
        int databaseSizeBeforeUpdate = imageContentRepository.findAll().size();

        // Create the ImageContent

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restImageContentMockMvc.perform(put("/api/image-contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imageContent)))
            .andExpect(status().isCreated());

        // Validate the ImageContent in the database
        List<ImageContent> imageContentList = imageContentRepository.findAll();
        assertThat(imageContentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteImageContent() throws Exception {
        // Initialize the database
        imageContentRepository.saveAndFlush(imageContent);
        int databaseSizeBeforeDelete = imageContentRepository.findAll().size();

        // Get the imageContent
        restImageContentMockMvc.perform(delete("/api/image-contents/{id}", imageContent.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ImageContent> imageContentList = imageContentRepository.findAll();
        assertThat(imageContentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ImageContent.class);
        ImageContent imageContent1 = new ImageContent();
        imageContent1.setId(1L);
        ImageContent imageContent2 = new ImageContent();
        imageContent2.setId(imageContent1.getId());
        assertThat(imageContent1).isEqualTo(imageContent2);
        imageContent2.setId(2L);
        assertThat(imageContent1).isNotEqualTo(imageContent2);
        imageContent1.setId(null);
        assertThat(imageContent1).isNotEqualTo(imageContent2);
    }
}
