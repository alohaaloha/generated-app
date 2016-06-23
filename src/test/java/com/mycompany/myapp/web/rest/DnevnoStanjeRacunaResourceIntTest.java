package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.PinfProApp;
import com.mycompany.myapp.domain.DnevnoStanjeRacuna;
import com.mycompany.myapp.repository.DnevnoStanjeRacunaRepository;

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
 * Test class for the DnevnoStanjeRacunaResource REST controller.
 *
 * @see DnevnoStanjeRacunaResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PinfProApp.class)
@WebAppConfiguration
@IntegrationTest
public class DnevnoStanjeRacunaResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));


    private static final Long DEFAULT_BROJ_IZVODA = 1L;
    private static final Long UPDATED_BROJ_IZVODA = 2L;

    private static final ZonedDateTime DEFAULT_DATUM = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_DATUM = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATUM_STR = dateTimeFormatter.format(DEFAULT_DATUM);

    private static final Double DEFAULT_PRETHODNO_STANJE = 1D;
    private static final Double UPDATED_PRETHODNO_STANJE = 2D;

    private static final Double DEFAULT_PROMET_U_KORIST = 1D;
    private static final Double UPDATED_PROMET_U_KORIST = 2D;

    private static final Double DEFAULT_PROMET_NA_TERET = 1D;
    private static final Double UPDATED_PROMET_NA_TERET = 2D;

    private static final Double DEFAULT_NOVO_STANJE = 1D;
    private static final Double UPDATED_NOVO_STANJE = 2D;

    @Inject
    private DnevnoStanjeRacunaRepository dnevnoStanjeRacunaRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDnevnoStanjeRacunaMockMvc;

    private DnevnoStanjeRacuna dnevnoStanjeRacuna;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DnevnoStanjeRacunaResource dnevnoStanjeRacunaResource = new DnevnoStanjeRacunaResource();
        ReflectionTestUtils.setField(dnevnoStanjeRacunaResource, "dnevnoStanjeRacunaRepository", dnevnoStanjeRacunaRepository);
        this.restDnevnoStanjeRacunaMockMvc = MockMvcBuilders.standaloneSetup(dnevnoStanjeRacunaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        dnevnoStanjeRacuna = new DnevnoStanjeRacuna();
        dnevnoStanjeRacuna.setBrojIzvoda(DEFAULT_BROJ_IZVODA);
        dnevnoStanjeRacuna.setDatum(DEFAULT_DATUM);
        dnevnoStanjeRacuna.setPrethodnoStanje(DEFAULT_PRETHODNO_STANJE);
        dnevnoStanjeRacuna.setPrometUKorist(DEFAULT_PROMET_U_KORIST);
        dnevnoStanjeRacuna.setPrometNaTeret(DEFAULT_PROMET_NA_TERET);
        dnevnoStanjeRacuna.setNovoStanje(DEFAULT_NOVO_STANJE);
    }

    @Test
    @Transactional
    public void createDnevnoStanjeRacuna() throws Exception {
        int databaseSizeBeforeCreate = dnevnoStanjeRacunaRepository.findAll().size();

        // Create the DnevnoStanjeRacuna

        restDnevnoStanjeRacunaMockMvc.perform(post("/api/dnevno-stanje-racunas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dnevnoStanjeRacuna)))
                .andExpect(status().isCreated());

        // Validate the DnevnoStanjeRacuna in the database
        List<DnevnoStanjeRacuna> dnevnoStanjeRacunas = dnevnoStanjeRacunaRepository.findAll();
        assertThat(dnevnoStanjeRacunas).hasSize(databaseSizeBeforeCreate + 1);
        DnevnoStanjeRacuna testDnevnoStanjeRacuna = dnevnoStanjeRacunas.get(dnevnoStanjeRacunas.size() - 1);
        assertThat(testDnevnoStanjeRacuna.getBrojIzvoda()).isEqualTo(DEFAULT_BROJ_IZVODA);
        assertThat(testDnevnoStanjeRacuna.getDatum()).isEqualTo(DEFAULT_DATUM);
        assertThat(testDnevnoStanjeRacuna.getPrethodnoStanje()).isEqualTo(DEFAULT_PRETHODNO_STANJE);
        assertThat(testDnevnoStanjeRacuna.getPrometUKorist()).isEqualTo(DEFAULT_PROMET_U_KORIST);
        assertThat(testDnevnoStanjeRacuna.getPrometNaTeret()).isEqualTo(DEFAULT_PROMET_NA_TERET);
        assertThat(testDnevnoStanjeRacuna.getNovoStanje()).isEqualTo(DEFAULT_NOVO_STANJE);
    }

    @Test
    @Transactional
    public void checkBrojIzvodaIsRequired() throws Exception {
        int databaseSizeBeforeTest = dnevnoStanjeRacunaRepository.findAll().size();
        // set the field null
        dnevnoStanjeRacuna.setBrojIzvoda(null);

        // Create the DnevnoStanjeRacuna, which fails.

        restDnevnoStanjeRacunaMockMvc.perform(post("/api/dnevno-stanje-racunas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dnevnoStanjeRacuna)))
                .andExpect(status().isBadRequest());

        List<DnevnoStanjeRacuna> dnevnoStanjeRacunas = dnevnoStanjeRacunaRepository.findAll();
        assertThat(dnevnoStanjeRacunas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDatumIsRequired() throws Exception {
        int databaseSizeBeforeTest = dnevnoStanjeRacunaRepository.findAll().size();
        // set the field null
        dnevnoStanjeRacuna.setDatum(null);

        // Create the DnevnoStanjeRacuna, which fails.

        restDnevnoStanjeRacunaMockMvc.perform(post("/api/dnevno-stanje-racunas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dnevnoStanjeRacuna)))
                .andExpect(status().isBadRequest());

        List<DnevnoStanjeRacuna> dnevnoStanjeRacunas = dnevnoStanjeRacunaRepository.findAll();
        assertThat(dnevnoStanjeRacunas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrethodnoStanjeIsRequired() throws Exception {
        int databaseSizeBeforeTest = dnevnoStanjeRacunaRepository.findAll().size();
        // set the field null
        dnevnoStanjeRacuna.setPrethodnoStanje(null);

        // Create the DnevnoStanjeRacuna, which fails.

        restDnevnoStanjeRacunaMockMvc.perform(post("/api/dnevno-stanje-racunas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dnevnoStanjeRacuna)))
                .andExpect(status().isBadRequest());

        List<DnevnoStanjeRacuna> dnevnoStanjeRacunas = dnevnoStanjeRacunaRepository.findAll();
        assertThat(dnevnoStanjeRacunas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrometUKoristIsRequired() throws Exception {
        int databaseSizeBeforeTest = dnevnoStanjeRacunaRepository.findAll().size();
        // set the field null
        dnevnoStanjeRacuna.setPrometUKorist(null);

        // Create the DnevnoStanjeRacuna, which fails.

        restDnevnoStanjeRacunaMockMvc.perform(post("/api/dnevno-stanje-racunas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dnevnoStanjeRacuna)))
                .andExpect(status().isBadRequest());

        List<DnevnoStanjeRacuna> dnevnoStanjeRacunas = dnevnoStanjeRacunaRepository.findAll();
        assertThat(dnevnoStanjeRacunas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrometNaTeretIsRequired() throws Exception {
        int databaseSizeBeforeTest = dnevnoStanjeRacunaRepository.findAll().size();
        // set the field null
        dnevnoStanjeRacuna.setPrometNaTeret(null);

        // Create the DnevnoStanjeRacuna, which fails.

        restDnevnoStanjeRacunaMockMvc.perform(post("/api/dnevno-stanje-racunas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dnevnoStanjeRacuna)))
                .andExpect(status().isBadRequest());

        List<DnevnoStanjeRacuna> dnevnoStanjeRacunas = dnevnoStanjeRacunaRepository.findAll();
        assertThat(dnevnoStanjeRacunas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNovoStanjeIsRequired() throws Exception {
        int databaseSizeBeforeTest = dnevnoStanjeRacunaRepository.findAll().size();
        // set the field null
        dnevnoStanjeRacuna.setNovoStanje(null);

        // Create the DnevnoStanjeRacuna, which fails.

        restDnevnoStanjeRacunaMockMvc.perform(post("/api/dnevno-stanje-racunas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dnevnoStanjeRacuna)))
                .andExpect(status().isBadRequest());

        List<DnevnoStanjeRacuna> dnevnoStanjeRacunas = dnevnoStanjeRacunaRepository.findAll();
        assertThat(dnevnoStanjeRacunas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDnevnoStanjeRacunas() throws Exception {
        // Initialize the database
        dnevnoStanjeRacunaRepository.saveAndFlush(dnevnoStanjeRacuna);

        // Get all the dnevnoStanjeRacunas
        restDnevnoStanjeRacunaMockMvc.perform(get("/api/dnevno-stanje-racunas?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(dnevnoStanjeRacuna.getId().intValue())))
                .andExpect(jsonPath("$.[*].brojIzvoda").value(hasItem(DEFAULT_BROJ_IZVODA.intValue())))
                .andExpect(jsonPath("$.[*].datum").value(hasItem(DEFAULT_DATUM_STR)))
                .andExpect(jsonPath("$.[*].prethodnoStanje").value(hasItem(DEFAULT_PRETHODNO_STANJE.doubleValue())))
                .andExpect(jsonPath("$.[*].prometUKorist").value(hasItem(DEFAULT_PROMET_U_KORIST.doubleValue())))
                .andExpect(jsonPath("$.[*].prometNaTeret").value(hasItem(DEFAULT_PROMET_NA_TERET.doubleValue())))
                .andExpect(jsonPath("$.[*].novoStanje").value(hasItem(DEFAULT_NOVO_STANJE.doubleValue())));
    }

    @Test
    @Transactional
    public void getDnevnoStanjeRacuna() throws Exception {
        // Initialize the database
        dnevnoStanjeRacunaRepository.saveAndFlush(dnevnoStanjeRacuna);

        // Get the dnevnoStanjeRacuna
        restDnevnoStanjeRacunaMockMvc.perform(get("/api/dnevno-stanje-racunas/{id}", dnevnoStanjeRacuna.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(dnevnoStanjeRacuna.getId().intValue()))
            .andExpect(jsonPath("$.brojIzvoda").value(DEFAULT_BROJ_IZVODA.intValue()))
            .andExpect(jsonPath("$.datum").value(DEFAULT_DATUM_STR))
            .andExpect(jsonPath("$.prethodnoStanje").value(DEFAULT_PRETHODNO_STANJE.doubleValue()))
            .andExpect(jsonPath("$.prometUKorist").value(DEFAULT_PROMET_U_KORIST.doubleValue()))
            .andExpect(jsonPath("$.prometNaTeret").value(DEFAULT_PROMET_NA_TERET.doubleValue()))
            .andExpect(jsonPath("$.novoStanje").value(DEFAULT_NOVO_STANJE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDnevnoStanjeRacuna() throws Exception {
        // Get the dnevnoStanjeRacuna
        restDnevnoStanjeRacunaMockMvc.perform(get("/api/dnevno-stanje-racunas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDnevnoStanjeRacuna() throws Exception {
        // Initialize the database
        dnevnoStanjeRacunaRepository.saveAndFlush(dnevnoStanjeRacuna);
        int databaseSizeBeforeUpdate = dnevnoStanjeRacunaRepository.findAll().size();

        // Update the dnevnoStanjeRacuna
        DnevnoStanjeRacuna updatedDnevnoStanjeRacuna = new DnevnoStanjeRacuna();
        updatedDnevnoStanjeRacuna.setId(dnevnoStanjeRacuna.getId());
        updatedDnevnoStanjeRacuna.setBrojIzvoda(UPDATED_BROJ_IZVODA);
        updatedDnevnoStanjeRacuna.setDatum(UPDATED_DATUM);
        updatedDnevnoStanjeRacuna.setPrethodnoStanje(UPDATED_PRETHODNO_STANJE);
        updatedDnevnoStanjeRacuna.setPrometUKorist(UPDATED_PROMET_U_KORIST);
        updatedDnevnoStanjeRacuna.setPrometNaTeret(UPDATED_PROMET_NA_TERET);
        updatedDnevnoStanjeRacuna.setNovoStanje(UPDATED_NOVO_STANJE);

        restDnevnoStanjeRacunaMockMvc.perform(put("/api/dnevno-stanje-racunas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedDnevnoStanjeRacuna)))
                .andExpect(status().isOk());

        // Validate the DnevnoStanjeRacuna in the database
        List<DnevnoStanjeRacuna> dnevnoStanjeRacunas = dnevnoStanjeRacunaRepository.findAll();
        assertThat(dnevnoStanjeRacunas).hasSize(databaseSizeBeforeUpdate);
        DnevnoStanjeRacuna testDnevnoStanjeRacuna = dnevnoStanjeRacunas.get(dnevnoStanjeRacunas.size() - 1);
        assertThat(testDnevnoStanjeRacuna.getBrojIzvoda()).isEqualTo(UPDATED_BROJ_IZVODA);
        assertThat(testDnevnoStanjeRacuna.getDatum()).isEqualTo(UPDATED_DATUM);
        assertThat(testDnevnoStanjeRacuna.getPrethodnoStanje()).isEqualTo(UPDATED_PRETHODNO_STANJE);
        assertThat(testDnevnoStanjeRacuna.getPrometUKorist()).isEqualTo(UPDATED_PROMET_U_KORIST);
        assertThat(testDnevnoStanjeRacuna.getPrometNaTeret()).isEqualTo(UPDATED_PROMET_NA_TERET);
        assertThat(testDnevnoStanjeRacuna.getNovoStanje()).isEqualTo(UPDATED_NOVO_STANJE);
    }

    @Test
    @Transactional
    public void deleteDnevnoStanjeRacuna() throws Exception {
        // Initialize the database
        dnevnoStanjeRacunaRepository.saveAndFlush(dnevnoStanjeRacuna);
        int databaseSizeBeforeDelete = dnevnoStanjeRacunaRepository.findAll().size();

        // Get the dnevnoStanjeRacuna
        restDnevnoStanjeRacunaMockMvc.perform(delete("/api/dnevno-stanje-racunas/{id}", dnevnoStanjeRacuna.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<DnevnoStanjeRacuna> dnevnoStanjeRacunas = dnevnoStanjeRacunaRepository.findAll();
        assertThat(dnevnoStanjeRacunas).hasSize(databaseSizeBeforeDelete - 1);
    }
}
