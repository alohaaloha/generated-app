package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.PinfProApp;
import com.mycompany.myapp.domain.RTGS;
import com.mycompany.myapp.repository.RTGSRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the RTGSResource REST controller.
 *
 * @see RTGSResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PinfProApp.class)
@WebAppConfiguration
@IntegrationTest
public class RTGSResourceIntTest {

    private static final String DEFAULT_ID_PORUKE = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_ID_PORUKE = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_SWIFT_KOD_BANKE_DUZNIKA = "AAAAAAAA";
    private static final String UPDATED_SWIFT_KOD_BANKE_DUZNIKA = "BBBBBBBB";
    private static final String DEFAULT_OBRACUNSKI_RACUN_BANKE_DUZNIKA = "AAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_OBRACUNSKI_RACUN_BANKE_DUZNIKA = "BBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_SWIFT_KOD_BANKE_POVERIOCA = "AAAAAAAA";
    private static final String UPDATED_SWIFT_KOD_BANKE_POVERIOCA = "BBBBBBBB";
    private static final String DEFAULT_OBRACUNSKI_RACUN_BANKE_POVERIOCA = "AAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_OBRACUNSKI_RACUN_BANKE_POVERIOCA = "BBBBBBBBBBBBBBBBBB";

    @Inject
    private RTGSRepository rTGSRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restRTGSMockMvc;

    private RTGS rTGS;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RTGSResource rTGSResource = new RTGSResource();
        ReflectionTestUtils.setField(rTGSResource, "rTGSRepository", rTGSRepository);
        this.restRTGSMockMvc = MockMvcBuilders.standaloneSetup(rTGSResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        rTGS = new RTGS();
        rTGS.setIdPoruke(DEFAULT_ID_PORUKE);
        rTGS.setSwiftKodBankeDuznika(DEFAULT_SWIFT_KOD_BANKE_DUZNIKA);
        rTGS.setObracunskiRacunBankeDuznika(DEFAULT_OBRACUNSKI_RACUN_BANKE_DUZNIKA);
        rTGS.setSwiftKodBankePoverioca(DEFAULT_SWIFT_KOD_BANKE_POVERIOCA);
        rTGS.setObracunskiRacunBankePoverioca(DEFAULT_OBRACUNSKI_RACUN_BANKE_POVERIOCA);
    }

    @Test
    @Transactional
    public void createRTGS() throws Exception {
        int databaseSizeBeforeCreate = rTGSRepository.findAll().size();

        // Create the RTGS

        restRTGSMockMvc.perform(post("/api/r-tgs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rTGS)))
                .andExpect(status().isCreated());

        // Validate the RTGS in the database
        List<RTGS> rTGS = rTGSRepository.findAll();
        assertThat(rTGS).hasSize(databaseSizeBeforeCreate + 1);
        RTGS testRTGS = rTGS.get(rTGS.size() - 1);
        assertThat(testRTGS.getIdPoruke()).isEqualTo(DEFAULT_ID_PORUKE);
        assertThat(testRTGS.getSwiftKodBankeDuznika()).isEqualTo(DEFAULT_SWIFT_KOD_BANKE_DUZNIKA);
        assertThat(testRTGS.getObracunskiRacunBankeDuznika()).isEqualTo(DEFAULT_OBRACUNSKI_RACUN_BANKE_DUZNIKA);
        assertThat(testRTGS.getSwiftKodBankePoverioca()).isEqualTo(DEFAULT_SWIFT_KOD_BANKE_POVERIOCA);
        assertThat(testRTGS.getObracunskiRacunBankePoverioca()).isEqualTo(DEFAULT_OBRACUNSKI_RACUN_BANKE_POVERIOCA);
    }

    @Test
    @Transactional
    public void checkIdPorukeIsRequired() throws Exception {
        int databaseSizeBeforeTest = rTGSRepository.findAll().size();
        // set the field null
        rTGS.setIdPoruke(null);

        // Create the RTGS, which fails.

        restRTGSMockMvc.perform(post("/api/r-tgs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rTGS)))
                .andExpect(status().isBadRequest());

        List<RTGS> rTGS = rTGSRepository.findAll();
        assertThat(rTGS).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSwiftKodBankeDuznikaIsRequired() throws Exception {
        int databaseSizeBeforeTest = rTGSRepository.findAll().size();
        // set the field null
        rTGS.setSwiftKodBankeDuznika(null);

        // Create the RTGS, which fails.

        restRTGSMockMvc.perform(post("/api/r-tgs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rTGS)))
                .andExpect(status().isBadRequest());

        List<RTGS> rTGS = rTGSRepository.findAll();
        assertThat(rTGS).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkObracunskiRacunBankeDuznikaIsRequired() throws Exception {
        int databaseSizeBeforeTest = rTGSRepository.findAll().size();
        // set the field null
        rTGS.setObracunskiRacunBankeDuznika(null);

        // Create the RTGS, which fails.

        restRTGSMockMvc.perform(post("/api/r-tgs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rTGS)))
                .andExpect(status().isBadRequest());

        List<RTGS> rTGS = rTGSRepository.findAll();
        assertThat(rTGS).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSwiftKodBankePoveriocaIsRequired() throws Exception {
        int databaseSizeBeforeTest = rTGSRepository.findAll().size();
        // set the field null
        rTGS.setSwiftKodBankePoverioca(null);

        // Create the RTGS, which fails.

        restRTGSMockMvc.perform(post("/api/r-tgs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rTGS)))
                .andExpect(status().isBadRequest());

        List<RTGS> rTGS = rTGSRepository.findAll();
        assertThat(rTGS).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkObracunskiRacunBankePoveriocaIsRequired() throws Exception {
        int databaseSizeBeforeTest = rTGSRepository.findAll().size();
        // set the field null
        rTGS.setObracunskiRacunBankePoverioca(null);

        // Create the RTGS, which fails.

        restRTGSMockMvc.perform(post("/api/r-tgs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rTGS)))
                .andExpect(status().isBadRequest());

        List<RTGS> rTGS = rTGSRepository.findAll();
        assertThat(rTGS).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRTGS() throws Exception {
        // Initialize the database
        rTGSRepository.saveAndFlush(rTGS);

        // Get all the rTGS
        restRTGSMockMvc.perform(get("/api/r-tgs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(rTGS.getId().intValue())))
                .andExpect(jsonPath("$.[*].idPoruke").value(hasItem(DEFAULT_ID_PORUKE.toString())))
                .andExpect(jsonPath("$.[*].swiftKodBankeDuznika").value(hasItem(DEFAULT_SWIFT_KOD_BANKE_DUZNIKA.toString())))
                .andExpect(jsonPath("$.[*].obracunskiRacunBankeDuznika").value(hasItem(DEFAULT_OBRACUNSKI_RACUN_BANKE_DUZNIKA.toString())))
                .andExpect(jsonPath("$.[*].swiftKodBankePoverioca").value(hasItem(DEFAULT_SWIFT_KOD_BANKE_POVERIOCA.toString())))
                .andExpect(jsonPath("$.[*].obracunskiRacunBankePoverioca").value(hasItem(DEFAULT_OBRACUNSKI_RACUN_BANKE_POVERIOCA.toString())));
    }

    @Test
    @Transactional
    public void getRTGS() throws Exception {
        // Initialize the database
        rTGSRepository.saveAndFlush(rTGS);

        // Get the rTGS
        restRTGSMockMvc.perform(get("/api/r-tgs/{id}", rTGS.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(rTGS.getId().intValue()))
            .andExpect(jsonPath("$.idPoruke").value(DEFAULT_ID_PORUKE.toString()))
            .andExpect(jsonPath("$.swiftKodBankeDuznika").value(DEFAULT_SWIFT_KOD_BANKE_DUZNIKA.toString()))
            .andExpect(jsonPath("$.obracunskiRacunBankeDuznika").value(DEFAULT_OBRACUNSKI_RACUN_BANKE_DUZNIKA.toString()))
            .andExpect(jsonPath("$.swiftKodBankePoverioca").value(DEFAULT_SWIFT_KOD_BANKE_POVERIOCA.toString()))
            .andExpect(jsonPath("$.obracunskiRacunBankePoverioca").value(DEFAULT_OBRACUNSKI_RACUN_BANKE_POVERIOCA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRTGS() throws Exception {
        // Get the rTGS
        restRTGSMockMvc.perform(get("/api/r-tgs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRTGS() throws Exception {
        // Initialize the database
        rTGSRepository.saveAndFlush(rTGS);
        int databaseSizeBeforeUpdate = rTGSRepository.findAll().size();

        // Update the rTGS
        RTGS updatedRTGS = new RTGS();
        updatedRTGS.setId(rTGS.getId());
        updatedRTGS.setIdPoruke(UPDATED_ID_PORUKE);
        updatedRTGS.setSwiftKodBankeDuznika(UPDATED_SWIFT_KOD_BANKE_DUZNIKA);
        updatedRTGS.setObracunskiRacunBankeDuznika(UPDATED_OBRACUNSKI_RACUN_BANKE_DUZNIKA);
        updatedRTGS.setSwiftKodBankePoverioca(UPDATED_SWIFT_KOD_BANKE_POVERIOCA);
        updatedRTGS.setObracunskiRacunBankePoverioca(UPDATED_OBRACUNSKI_RACUN_BANKE_POVERIOCA);

        restRTGSMockMvc.perform(put("/api/r-tgs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedRTGS)))
                .andExpect(status().isOk());

        // Validate the RTGS in the database
        List<RTGS> rTGS = rTGSRepository.findAll();
        assertThat(rTGS).hasSize(databaseSizeBeforeUpdate);
        RTGS testRTGS = rTGS.get(rTGS.size() - 1);
        assertThat(testRTGS.getIdPoruke()).isEqualTo(UPDATED_ID_PORUKE);
        assertThat(testRTGS.getSwiftKodBankeDuznika()).isEqualTo(UPDATED_SWIFT_KOD_BANKE_DUZNIKA);
        assertThat(testRTGS.getObracunskiRacunBankeDuznika()).isEqualTo(UPDATED_OBRACUNSKI_RACUN_BANKE_DUZNIKA);
        assertThat(testRTGS.getSwiftKodBankePoverioca()).isEqualTo(UPDATED_SWIFT_KOD_BANKE_POVERIOCA);
        assertThat(testRTGS.getObracunskiRacunBankePoverioca()).isEqualTo(UPDATED_OBRACUNSKI_RACUN_BANKE_POVERIOCA);
    }

    @Test
    @Transactional
    public void deleteRTGS() throws Exception {
        // Initialize the database
        rTGSRepository.saveAndFlush(rTGS);
        int databaseSizeBeforeDelete = rTGSRepository.findAll().size();

        // Get the rTGS
        restRTGSMockMvc.perform(delete("/api/r-tgs/{id}", rTGS.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<RTGS> rTGS = rTGSRepository.findAll();
        assertThat(rTGS).hasSize(databaseSizeBeforeDelete - 1);
    }
}
