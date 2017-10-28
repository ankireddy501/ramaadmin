package com.rama.admin.web.rest;

import com.rama.admin.RamaadminApp;

import com.rama.admin.domain.ContentRules;
import com.rama.admin.repository.ContentRulesRepository;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.rama.admin.domain.enumeration.LifeTime;
import com.rama.admin.domain.enumeration.Validity;
/**
 * Test class for the ContentRulesResource REST controller.
 *
 * @see ContentRulesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RamaadminApp.class)
public class ContentRulesResourceIntTest {

    private static final LifeTime DEFAULT_LIFE_TIME = LifeTime.UNLIMITED;
    private static final LifeTime UPDATED_LIFE_TIME = LifeTime.UNLIMITED;

    private static final Validity DEFAULT_VALIDITY = Validity.UNLIMITED;
    private static final Validity UPDATED_VALIDITY = Validity.UNLIMITED;

    private static final Integer DEFAULT_COST = 1;
    private static final Integer UPDATED_COST = 2;

    @Autowired
    private ContentRulesRepository contentRulesRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restContentRulesMockMvc;

    private ContentRules contentRules;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContentRulesResource contentRulesResource = new ContentRulesResource(contentRulesRepository);
        this.restContentRulesMockMvc = MockMvcBuilders.standaloneSetup(contentRulesResource)
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
    public static ContentRules createEntity(EntityManager em) {
        ContentRules contentRules = new ContentRules()
            .lifeTime(DEFAULT_LIFE_TIME)
            .validity(DEFAULT_VALIDITY)
            .cost(DEFAULT_COST);
        return contentRules;
    }

    @Before
    public void initTest() {
        contentRules = createEntity(em);
    }

    @Test
    @Transactional
    public void createContentRules() throws Exception {
        int databaseSizeBeforeCreate = contentRulesRepository.findAll().size();

        // Create the ContentRules
        restContentRulesMockMvc.perform(post("/api/content-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contentRules)))
            .andExpect(status().isCreated());

        // Validate the ContentRules in the database
        List<ContentRules> contentRulesList = contentRulesRepository.findAll();
        assertThat(contentRulesList).hasSize(databaseSizeBeforeCreate + 1);
        ContentRules testContentRules = contentRulesList.get(contentRulesList.size() - 1);
        assertThat(testContentRules.getLifeTime()).isEqualTo(DEFAULT_LIFE_TIME);
        assertThat(testContentRules.getValidity()).isEqualTo(DEFAULT_VALIDITY);
        assertThat(testContentRules.getCost()).isEqualTo(DEFAULT_COST);
    }

    @Test
    @Transactional
    public void createContentRulesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contentRulesRepository.findAll().size();

        // Create the ContentRules with an existing ID
        contentRules.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContentRulesMockMvc.perform(post("/api/content-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contentRules)))
            .andExpect(status().isBadRequest());

        // Validate the ContentRules in the database
        List<ContentRules> contentRulesList = contentRulesRepository.findAll();
        assertThat(contentRulesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllContentRules() throws Exception {
        // Initialize the database
        contentRulesRepository.saveAndFlush(contentRules);

        // Get all the contentRulesList
        restContentRulesMockMvc.perform(get("/api/content-rules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contentRules.getId().intValue())))
            .andExpect(jsonPath("$.[*].lifeTime").value(hasItem(DEFAULT_LIFE_TIME.toString())))
            .andExpect(jsonPath("$.[*].validity").value(hasItem(DEFAULT_VALIDITY.toString())))
            .andExpect(jsonPath("$.[*].cost").value(hasItem(DEFAULT_COST)));
    }

    @Test
    @Transactional
    public void getContentRules() throws Exception {
        // Initialize the database
        contentRulesRepository.saveAndFlush(contentRules);

        // Get the contentRules
        restContentRulesMockMvc.perform(get("/api/content-rules/{id}", contentRules.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contentRules.getId().intValue()))
            .andExpect(jsonPath("$.lifeTime").value(DEFAULT_LIFE_TIME.toString()))
            .andExpect(jsonPath("$.validity").value(DEFAULT_VALIDITY.toString()))
            .andExpect(jsonPath("$.cost").value(DEFAULT_COST));
    }

    @Test
    @Transactional
    public void getNonExistingContentRules() throws Exception {
        // Get the contentRules
        restContentRulesMockMvc.perform(get("/api/content-rules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContentRules() throws Exception {
        // Initialize the database
        contentRulesRepository.saveAndFlush(contentRules);
        int databaseSizeBeforeUpdate = contentRulesRepository.findAll().size();

        // Update the contentRules
        ContentRules updatedContentRules = contentRulesRepository.findOne(contentRules.getId());
        updatedContentRules
            .lifeTime(UPDATED_LIFE_TIME)
            .validity(UPDATED_VALIDITY)
            .cost(UPDATED_COST);

        restContentRulesMockMvc.perform(put("/api/content-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedContentRules)))
            .andExpect(status().isOk());

        // Validate the ContentRules in the database
        List<ContentRules> contentRulesList = contentRulesRepository.findAll();
        assertThat(contentRulesList).hasSize(databaseSizeBeforeUpdate);
        ContentRules testContentRules = contentRulesList.get(contentRulesList.size() - 1);
        assertThat(testContentRules.getLifeTime()).isEqualTo(UPDATED_LIFE_TIME);
        assertThat(testContentRules.getValidity()).isEqualTo(UPDATED_VALIDITY);
        assertThat(testContentRules.getCost()).isEqualTo(UPDATED_COST);
    }

    @Test
    @Transactional
    public void updateNonExistingContentRules() throws Exception {
        int databaseSizeBeforeUpdate = contentRulesRepository.findAll().size();

        // Create the ContentRules

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restContentRulesMockMvc.perform(put("/api/content-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contentRules)))
            .andExpect(status().isCreated());

        // Validate the ContentRules in the database
        List<ContentRules> contentRulesList = contentRulesRepository.findAll();
        assertThat(contentRulesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteContentRules() throws Exception {
        // Initialize the database
        contentRulesRepository.saveAndFlush(contentRules);
        int databaseSizeBeforeDelete = contentRulesRepository.findAll().size();

        // Get the contentRules
        restContentRulesMockMvc.perform(delete("/api/content-rules/{id}", contentRules.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ContentRules> contentRulesList = contentRulesRepository.findAll();
        assertThat(contentRulesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContentRules.class);
        ContentRules contentRules1 = new ContentRules();
        contentRules1.setId(1L);
        ContentRules contentRules2 = new ContentRules();
        contentRules2.setId(contentRules1.getId());
        assertThat(contentRules1).isEqualTo(contentRules2);
        contentRules2.setId(2L);
        assertThat(contentRules1).isNotEqualTo(contentRules2);
        contentRules1.setId(null);
        assertThat(contentRules1).isNotEqualTo(contentRules2);
    }
}
