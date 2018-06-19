package io.avand.web.rest;

import io.avand.VoldemortApp;

import io.avand.domain.CandidateEntity;
import io.avand.domain.FileEntity;
import io.avand.domain.JobEntity;
import io.avand.repository.CandidateEntityRepository;
import io.avand.web.rest.errors.ExceptionTranslator;

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

import static io.avand.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.avand.domain.enumeration.CandidateState;
/**
 * Test class for the CandidateEntityResource REST controller.
 *
 * @see CandidateEntityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VoldemortApp.class)
public class CandidateEntityResourceIntTest {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_FILE_ID = 1L;
    private static final Long UPDATED_FILE_ID = 2L;

    private static final CandidateState DEFAULT_STATE = CandidateState.ACCEPTED;
    private static final CandidateState UPDATED_STATE = CandidateState.REJECTED;

    private static final String DEFAULT_CELLPHONE = "AAAAAAAAAA";
    private static final String UPDATED_CELLPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Long DEFAULT_CANDIDATE_PIPELINE = 1L;
    private static final Long UPDATED_CANDIDATE_PIPELINE = 2L;

    @Autowired
    private CandidateEntityRepository candidateEntityRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCandidateEntityMockMvc;

    private CandidateEntity candidateEntity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CandidateEntityResource candidateEntityResource = new CandidateEntityResource(candidateEntityRepository);
        this.restCandidateEntityMockMvc = MockMvcBuilders.standaloneSetup(candidateEntityResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CandidateEntity createEntity(EntityManager em) {
        CandidateEntity candidateEntity = new CandidateEntity()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .state(DEFAULT_STATE)
            .cellphone(DEFAULT_CELLPHONE)
            .email(DEFAULT_EMAIL)
            .candidatePipeline(DEFAULT_CANDIDATE_PIPELINE);
        // Add required entity
        FileEntity file = FileEntityResourceIntTest.createEntity(em);
        em.persist(file);
        em.flush();
        candidateEntity.setFile(file);
        // Add required entity
        JobEntity job = JobEntityResourceIntTest.createEntity(em);
        em.persist(job);
        em.flush();
        candidateEntity.setJob(job);
        return candidateEntity;
    }

    @Before
    public void initTest() {
        candidateEntity = createEntity(em);
    }

    @Test
    @Transactional
    public void createCandidateEntity() throws Exception {
        int databaseSizeBeforeCreate = candidateEntityRepository.findAll().size();

        // Create the CandidateEntity
        restCandidateEntityMockMvc.perform(post("/api/candidate-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candidateEntity)))
            .andExpect(status().isCreated());

        // Validate the CandidateEntity in the database
        List<CandidateEntity> candidateEntityList = candidateEntityRepository.findAll();
        assertThat(candidateEntityList).hasSize(databaseSizeBeforeCreate + 1);
        CandidateEntity testCandidateEntity = candidateEntityList.get(candidateEntityList.size() - 1);
        assertThat(testCandidateEntity.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testCandidateEntity.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testCandidateEntity.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testCandidateEntity.getCellphone()).isEqualTo(DEFAULT_CELLPHONE);
        assertThat(testCandidateEntity.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCandidateEntity.getCandidatePipeline()).isEqualTo(DEFAULT_CANDIDATE_PIPELINE);
    }

    @Test
    @Transactional
    public void createCandidateEntityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = candidateEntityRepository.findAll().size();

        // Create the CandidateEntity with an existing ID
        candidateEntity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCandidateEntityMockMvc.perform(post("/api/candidate-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candidateEntity)))
            .andExpect(status().isBadRequest());

        // Validate the CandidateEntity in the database
        List<CandidateEntity> candidateEntityList = candidateEntityRepository.findAll();
        assertThat(candidateEntityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCandidateEntities() throws Exception {
        // Initialize the database
        candidateEntityRepository.saveAndFlush(candidateEntity);

        // Get all the candidateEntityList
        restCandidateEntityMockMvc.perform(get("/api/candidate-entities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(candidateEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].fileId").value(hasItem(DEFAULT_FILE_ID.intValue())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].cellphone").value(hasItem(DEFAULT_CELLPHONE.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].candidatePipeline").value(hasItem(DEFAULT_CANDIDATE_PIPELINE.intValue())));
    }

    @Test
    @Transactional
    public void getCandidateEntity() throws Exception {
        // Initialize the database
        candidateEntityRepository.saveAndFlush(candidateEntity);

        // Get the candidateEntity
        restCandidateEntityMockMvc.perform(get("/api/candidate-entities/{id}", candidateEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(candidateEntity.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.fileId").value(DEFAULT_FILE_ID.intValue()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.cellphone").value(DEFAULT_CELLPHONE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.candidatePipeline").value(DEFAULT_CANDIDATE_PIPELINE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCandidateEntity() throws Exception {
        // Get the candidateEntity
        restCandidateEntityMockMvc.perform(get("/api/candidate-entities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCandidateEntity() throws Exception {
        // Initialize the database
        candidateEntityRepository.saveAndFlush(candidateEntity);
        int databaseSizeBeforeUpdate = candidateEntityRepository.findAll().size();

        // Update the candidateEntity
        CandidateEntity updatedCandidateEntity = candidateEntityRepository.findOne(candidateEntity.getId());
        // Disconnect from session so that the updates on updatedCandidateEntity are not directly saved in db
        em.detach(updatedCandidateEntity);
        updatedCandidateEntity
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .state(UPDATED_STATE)
            .cellphone(UPDATED_CELLPHONE)
            .email(UPDATED_EMAIL)
            .candidatePipeline(UPDATED_CANDIDATE_PIPELINE);

        restCandidateEntityMockMvc.perform(put("/api/candidate-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCandidateEntity)))
            .andExpect(status().isOk());

        // Validate the CandidateEntity in the database
        List<CandidateEntity> candidateEntityList = candidateEntityRepository.findAll();
        assertThat(candidateEntityList).hasSize(databaseSizeBeforeUpdate);
        CandidateEntity testCandidateEntity = candidateEntityList.get(candidateEntityList.size() - 1);
        assertThat(testCandidateEntity.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testCandidateEntity.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testCandidateEntity.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testCandidateEntity.getCellphone()).isEqualTo(UPDATED_CELLPHONE);
        assertThat(testCandidateEntity.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCandidateEntity.getCandidatePipeline()).isEqualTo(UPDATED_CANDIDATE_PIPELINE);
    }

    @Test
    @Transactional
    public void updateNonExistingCandidateEntity() throws Exception {
        int databaseSizeBeforeUpdate = candidateEntityRepository.findAll().size();

        // Create the CandidateEntity

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCandidateEntityMockMvc.perform(put("/api/candidate-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candidateEntity)))
            .andExpect(status().isCreated());

        // Validate the CandidateEntity in the database
        List<CandidateEntity> candidateEntityList = candidateEntityRepository.findAll();
        assertThat(candidateEntityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCandidateEntity() throws Exception {
        // Initialize the database
        candidateEntityRepository.saveAndFlush(candidateEntity);
        int databaseSizeBeforeDelete = candidateEntityRepository.findAll().size();

        // Get the candidateEntity
        restCandidateEntityMockMvc.perform(delete("/api/candidate-entities/{id}", candidateEntity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CandidateEntity> candidateEntityList = candidateEntityRepository.findAll();
        assertThat(candidateEntityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CandidateEntity.class);
        CandidateEntity candidateEntity1 = new CandidateEntity();
        candidateEntity1.setId(1L);
        CandidateEntity candidateEntity2 = new CandidateEntity();
        candidateEntity2.setId(candidateEntity1.getId());
        assertThat(candidateEntity1).isEqualTo(candidateEntity2);
        candidateEntity2.setId(2L);
        assertThat(candidateEntity1).isNotEqualTo(candidateEntity2);
        candidateEntity1.setId(null);
        assertThat(candidateEntity1).isNotEqualTo(candidateEntity2);
    }
}
