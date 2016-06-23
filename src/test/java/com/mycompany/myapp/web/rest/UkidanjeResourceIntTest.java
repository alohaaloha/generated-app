package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.PinfProApp;
import com.mycompany.myapp.domain.Ukidanje;
import com.mycompany.myapp.repository.UkidanjeRepository;

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
 * Test class for the UkidanjeResource REST controller.
 *
 * @see UkidanjeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PinfProApp.class)
@WebAppConfiguration
@IntegrationTest
public class UkidanjeResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));


    private static final ZonedDateTime DEFAULT_DATUM_UKIDANJA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_DATUM_UKIDANJA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATUM_UKIDANJA_STR = dateTimeFormatter.format(DEFAULT_DATUM_UKIDANJA);
    private static final String DEFAULT_PRENOS_NA_RACUN = "AAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_PRENOS_NA_RACUN = "BBBBBBBBBBBBBBBBBBBB";

    @Inject
    private UkidanjeRepository ukidanjeRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restUkidanjeMockMvc;

    private Ukidanje ukidanje;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UkidanjeResource ukidanjeResource = new UkidanjeResource();
        ReflectionTestUtils.setField(ukidanjeResource, "ukidanjeRepository", ukidanjeRepository);
        this.restUkidanjeMockMvc = MockMvcBuilders.standaloneSetup(ukidanjeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        ukidanje = new Ukidanje();
        ukidanje.setDatumUkidanja(DEFAULT_DATUM_UKIDANJA);
        ukidanje.setPrenosNaRacun(DEFAULT_PRENOS_NA_RACUN);
    }

    @Test
    @Transactional
    public void createUkidanje() throws Exception {
        int databaseSizeBeforeCreate = ukidanjeRepository.findAll().size();

        // Create the Ukidanje

        restUkidanjeMockMvc.perform(post("/api/ukidanjes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ukidanje)))
                .andExpect(status().isCreated());

        // Validate the Ukidanje in the database
        List<Ukidanje> ukidanjes = ukidanjeRepository.findAll();
        assertThat(ukidanjes).hasSize(databaseSizeBeforeCreate + 1);
        Ukidanje testUkidanje = ukidanjes.get(ukidanjes.size() - 1);
        assertThat(testUkidanje.getDatumUkidanja()).isEqualTo(DEFAULT_DATUM_UKIDANJA);
        assertThat(testUkidanje.getPrenosNaRacun()).isEqualTo(DEFAULT_PRENOS_NA_RACUN);
    }

    @Test
    @Transactional
    public void checkDatumUkidanjaIsRequired() throws Exception {
        int databaseSizeBeforeTest = ukidanjeRepository.findAll().size();
        // set the field null
        ukidanje.setDatumUkidanja(null);

        // Create the Ukidanje, which fails.

        restUkidanjeMockMvc.perform(post("/api/ukidanjes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ukidanje)))
                .andExpect(status().isBadRequest());

        List<Ukidanje> ukidanjes = ukidanjeRepository.findAll();
        assertThat(ukidanjes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrenosNaRacunIsRequired() throws Exception {
        int databaseSizeBeforeTest = ukidanjeRepository.findAll().size();
        // set the field null
        ukidanje.setPrenosNaRacun(null);

        // Create the Ukidanje, which fails.

        restUkidanjeMockMvc.perform(post("/api/ukidanjes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ukidanje)))
                .andExpect(status().isBadRequest());

        List<Ukidanje> ukidanjes = ukidanjeRepository.findAll();
        assertThat(ukidanjes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUkidanjes() throws Exception {
        // Initialize the database
        ukidanjeRepository.saveAndFlush(ukidanje);

        // Get all the ukidanjes
        restUkidanjeMockMvc.perform(get("/api/ukidanjes?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(ukidanje.getId().intValue())))
                .andExpect(jsonPath("$.[*].datumUkidanja").value(hasItem(DEFAULT_DATUM_UKIDANJA_STR)))
                .andExpect(jsonPath("$.[*].prenosNaRacun").value(hasItem(DEFAULT_PRENOS_NA_RACUN.toString())));
    }

    @Test
    @Transactional
    public void getUkidanje() throws Exception {
        // Initialize the database
        ukidanjeRepository.saveAndFlush(ukidanje);

        // Get the ukidanje
        restUkidanjeMockMvc.perform(get("/api/ukidanjes/{id}", ukidanje.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(ukidanje.getId().intValue()))
            .andExpect(jsonPath("$.datumUkidanja").value(DEFAULT_DATUM_UKIDANJA_STR))
            .andExpect(jsonPath("$.prenosNaRacun").value(DEFAULT_PRENOS_NA_RACUN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUkidanje() throws Exception {
        // Get the ukidanje
        restUkidanjeMockMvc.perform(get("/api/ukidanjes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUkidanje() throws Exception {
        // Initialize the database
        ukidanjeRepository.saveAndFlush(ukidanje);
        int databaseSizeBeforeUpdate = ukidanjeRepository.findAll().size();

        // Update the ukidanje
        Ukidanje updatedUkidanje = new Ukidanje();
        updatedUkidanje.setId(ukidanje.getId());
        updatedUkidanje.setDatumUkidanja(UPDATED_DATUM_UKIDANJA);
        updatedUkidanje.setPrenosNaRacun(UPDATED_PRENOS_NA_RACUN);

        restUkidanjeMockMvc.perform(put("/api/ukidanjes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedUkidanje)))
                .andExpect(status().isOk());

        // Validate the Ukidanje in the database
        List<Ukidanje> ukidanjes = ukidanjeRepository.findAll();
        assertThat(ukidanjes).hasSize(databaseSizeBeforeUpdate);
        Ukidanje testUkidanje = ukidanjes.get(ukidanjes.size() - 1);
        assertThat(testUkidanje.getDatumUkidanja()).isEqualTo(UPDATED_DATUM_UKIDANJA);
        assertThat(testUkidanje.getPrenosNaRacun()).isEqualTo(UPDATED_PRENOS_NA_RACUN);
    }

    @Test
    @Transactional
    public void deleteUkidanje() throws Exception {
        // Initialize the database
        ukidanjeRepository.saveAndFlush(ukidanje);
        int databaseSizeBeforeDelete = ukidanjeRepository.findAll().size();

        // Get the ukidanje
        restUkidanjeMockMvc.perform(delete("/api/ukidanjes/{id}", ukidanje.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Ukidanje> ukidanjes = ukidanjeRepository.findAll();
        assertThat(ukidanjes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
