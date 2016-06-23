package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.PinfProApp;
import com.mycompany.myapp.domain.RacunPravnogLica;
import com.mycompany.myapp.repository.RacunPravnogLicaRepository;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the RacunPravnogLicaResource REST controller.
 *
 * @see RacunPravnogLicaResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PinfProApp.class)
@WebAppConfiguration
@IntegrationTest
public class RacunPravnogLicaResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final String DEFAULT_BROJ_RACUNA = "AAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_BROJ_RACUNA = "BBBBBBBBBBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATUM_OTVARANJA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_DATUM_OTVARANJA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATUM_OTVARANJA_STR = dateTimeFormatter.format(DEFAULT_DATUM_OTVARANJA);

    private static final Integer DEFAULT_VAZENJE = 1;
    private static final Integer UPDATED_VAZENJE = 2;

    @Inject
    private RacunPravnogLicaRepository racunPravnogLicaRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restRacunPravnogLicaMockMvc;

    private RacunPravnogLica racunPravnogLica;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RacunPravnogLicaResource racunPravnogLicaResource = new RacunPravnogLicaResource();
        ReflectionTestUtils.setField(racunPravnogLicaResource, "racunPravnogLicaRepository", racunPravnogLicaRepository);
        this.restRacunPravnogLicaMockMvc = MockMvcBuilders.standaloneSetup(racunPravnogLicaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        racunPravnogLica = new RacunPravnogLica();
        racunPravnogLica.setBrojRacuna(DEFAULT_BROJ_RACUNA);
        racunPravnogLica.setDatumOtvaranja(DEFAULT_DATUM_OTVARANJA);
        racunPravnogLica.setVazenje(DEFAULT_VAZENJE);
    }

    @Test
    @Transactional
    public void createRacunPravnogLica() throws Exception {
        int databaseSizeBeforeCreate = racunPravnogLicaRepository.findAll().size();

        // Create the RacunPravnogLica

        restRacunPravnogLicaMockMvc.perform(post("/api/racun-pravnog-licas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(racunPravnogLica)))
                .andExpect(status().isCreated());

        // Validate the RacunPravnogLica in the database
        List<RacunPravnogLica> racunPravnogLicas = racunPravnogLicaRepository.findAll();
        assertThat(racunPravnogLicas).hasSize(databaseSizeBeforeCreate + 1);
        RacunPravnogLica testRacunPravnogLica = racunPravnogLicas.get(racunPravnogLicas.size() - 1);
        assertThat(testRacunPravnogLica.getBrojRacuna()).isEqualTo(DEFAULT_BROJ_RACUNA);
        assertThat(testRacunPravnogLica.getDatumOtvaranja()).isEqualTo(DEFAULT_DATUM_OTVARANJA);
        assertThat(testRacunPravnogLica.getVazenje()).isEqualTo(DEFAULT_VAZENJE);
    }

    @Test
    @Transactional
    public void checkBrojRacunaIsRequired() throws Exception {
        int databaseSizeBeforeTest = racunPravnogLicaRepository.findAll().size();
        // set the field null
        racunPravnogLica.setBrojRacuna(null);

        // Create the RacunPravnogLica, which fails.

        restRacunPravnogLicaMockMvc.perform(post("/api/racun-pravnog-licas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(racunPravnogLica)))
                .andExpect(status().isBadRequest());

        List<RacunPravnogLica> racunPravnogLicas = racunPravnogLicaRepository.findAll();
        assertThat(racunPravnogLicas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDatumOtvaranjaIsRequired() throws Exception {
        int databaseSizeBeforeTest = racunPravnogLicaRepository.findAll().size();
        // set the field null
        racunPravnogLica.setDatumOtvaranja(null);

        // Create the RacunPravnogLica, which fails.

        restRacunPravnogLicaMockMvc.perform(post("/api/racun-pravnog-licas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(racunPravnogLica)))
                .andExpect(status().isBadRequest());

        List<RacunPravnogLica> racunPravnogLicas = racunPravnogLicaRepository.findAll();
        assertThat(racunPravnogLicas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVazenjeIsRequired() throws Exception {
        int databaseSizeBeforeTest = racunPravnogLicaRepository.findAll().size();
        // set the field null
        racunPravnogLica.setVazenje(null);

        // Create the RacunPravnogLica, which fails.

        restRacunPravnogLicaMockMvc.perform(post("/api/racun-pravnog-licas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(racunPravnogLica)))
                .andExpect(status().isBadRequest());

        List<RacunPravnogLica> racunPravnogLicas = racunPravnogLicaRepository.findAll();
        assertThat(racunPravnogLicas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRacunPravnogLicas() throws Exception {
        // Initialize the database
        racunPravnogLicaRepository.saveAndFlush(racunPravnogLica);

        // Get all the racunPravnogLicas
        restRacunPravnogLicaMockMvc.perform(get("/api/racun-pravnog-licas?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(racunPravnogLica.getId().intValue())))
                .andExpect(jsonPath("$.[*].brojRacuna").value(hasItem(DEFAULT_BROJ_RACUNA.toString())))
                .andExpect(jsonPath("$.[*].datumOtvaranja").value(hasItem(DEFAULT_DATUM_OTVARANJA_STR)))
                .andExpect(jsonPath("$.[*].vazenje").value(hasItem(DEFAULT_VAZENJE)));
    }

    @Test
    @Transactional
    public void getRacunPravnogLica() throws Exception {
        // Initialize the database
        racunPravnogLicaRepository.saveAndFlush(racunPravnogLica);

        // Get the racunPravnogLica
        restRacunPravnogLicaMockMvc.perform(get("/api/racun-pravnog-licas/{id}", racunPravnogLica.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(racunPravnogLica.getId().intValue()))
            .andExpect(jsonPath("$.brojRacuna").value(DEFAULT_BROJ_RACUNA.toString()))
            .andExpect(jsonPath("$.datumOtvaranja").value(DEFAULT_DATUM_OTVARANJA_STR))
            .andExpect(jsonPath("$.vazenje").value(DEFAULT_VAZENJE));
    }

    @Test
    @Transactional
    public void getNonExistingRacunPravnogLica() throws Exception {
        // Get the racunPravnogLica
        restRacunPravnogLicaMockMvc.perform(get("/api/racun-pravnog-licas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRacunPravnogLica() throws Exception {
        // Initialize the database
        racunPravnogLicaRepository.saveAndFlush(racunPravnogLica);
        int databaseSizeBeforeUpdate = racunPravnogLicaRepository.findAll().size();

        // Update the racunPravnogLica
        RacunPravnogLica updatedRacunPravnogLica = new RacunPravnogLica();
        updatedRacunPravnogLica.setId(racunPravnogLica.getId());
        updatedRacunPravnogLica.setBrojRacuna(UPDATED_BROJ_RACUNA);
        updatedRacunPravnogLica.setDatumOtvaranja(UPDATED_DATUM_OTVARANJA);
        updatedRacunPravnogLica.setVazenje(UPDATED_VAZENJE);

        restRacunPravnogLicaMockMvc.perform(put("/api/racun-pravnog-licas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedRacunPravnogLica)))
                .andExpect(status().isOk());

        // Validate the RacunPravnogLica in the database
        List<RacunPravnogLica> racunPravnogLicas = racunPravnogLicaRepository.findAll();
        assertThat(racunPravnogLicas).hasSize(databaseSizeBeforeUpdate);
        RacunPravnogLica testRacunPravnogLica = racunPravnogLicas.get(racunPravnogLicas.size() - 1);
        assertThat(testRacunPravnogLica.getBrojRacuna()).isEqualTo(UPDATED_BROJ_RACUNA);
        assertThat(testRacunPravnogLica.getDatumOtvaranja()).isEqualTo(UPDATED_DATUM_OTVARANJA);
        assertThat(testRacunPravnogLica.getVazenje()).isEqualTo(UPDATED_VAZENJE);
    }

    @Test
    @Transactional
    public void deleteRacunPravnogLica() throws Exception {
        // Initialize the database
        racunPravnogLicaRepository.saveAndFlush(racunPravnogLica);
        int databaseSizeBeforeDelete = racunPravnogLicaRepository.findAll().size();

        // Get the racunPravnogLica
        restRacunPravnogLicaMockMvc.perform(delete("/api/racun-pravnog-licas/{id}", racunPravnogLica.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<RacunPravnogLica> racunPravnogLicas = racunPravnogLicaRepository.findAll();
        assertThat(racunPravnogLicas).hasSize(databaseSizeBeforeDelete - 1);
    }
}
