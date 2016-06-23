package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.PinfProApp;
import com.mycompany.myapp.domain.KursnaLista;
import com.mycompany.myapp.repository.KursnaListaRepository;

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
 * Test class for the KursnaListaResource REST controller.
 *
 * @see KursnaListaResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PinfProApp.class)
@WebAppConfiguration
@IntegrationTest
public class KursnaListaResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));


    private static final ZonedDateTime DEFAULT_DATUM = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_DATUM = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATUM_STR = dateTimeFormatter.format(DEFAULT_DATUM);

    private static final Integer DEFAULT_BROJ_KURSNE_LISTE = 1;
    private static final Integer UPDATED_BROJ_KURSNE_LISTE = 2;

    private static final ZonedDateTime DEFAULT_DATUM_PRIMENE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_DATUM_PRIMENE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATUM_PRIMENE_STR = dateTimeFormatter.format(DEFAULT_DATUM_PRIMENE);

    @Inject
    private KursnaListaRepository kursnaListaRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restKursnaListaMockMvc;

    private KursnaLista kursnaLista;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        KursnaListaResource kursnaListaResource = new KursnaListaResource();
        ReflectionTestUtils.setField(kursnaListaResource, "kursnaListaRepository", kursnaListaRepository);
        this.restKursnaListaMockMvc = MockMvcBuilders.standaloneSetup(kursnaListaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        kursnaLista = new KursnaLista();
        kursnaLista.setDatum(DEFAULT_DATUM);
        kursnaLista.setBrojKursneListe(DEFAULT_BROJ_KURSNE_LISTE);
        kursnaLista.setDatumPrimene(DEFAULT_DATUM_PRIMENE);
    }

    @Test
    @Transactional
    public void createKursnaLista() throws Exception {
        int databaseSizeBeforeCreate = kursnaListaRepository.findAll().size();

        // Create the KursnaLista

        restKursnaListaMockMvc.perform(post("/api/kursna-listas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(kursnaLista)))
                .andExpect(status().isCreated());

        // Validate the KursnaLista in the database
        List<KursnaLista> kursnaListas = kursnaListaRepository.findAll();
        assertThat(kursnaListas).hasSize(databaseSizeBeforeCreate + 1);
        KursnaLista testKursnaLista = kursnaListas.get(kursnaListas.size() - 1);
        assertThat(testKursnaLista.getDatum()).isEqualTo(DEFAULT_DATUM);
        assertThat(testKursnaLista.getBrojKursneListe()).isEqualTo(DEFAULT_BROJ_KURSNE_LISTE);
        assertThat(testKursnaLista.getDatumPrimene()).isEqualTo(DEFAULT_DATUM_PRIMENE);
    }

    @Test
    @Transactional
    public void checkDatumIsRequired() throws Exception {
        int databaseSizeBeforeTest = kursnaListaRepository.findAll().size();
        // set the field null
        kursnaLista.setDatum(null);

        // Create the KursnaLista, which fails.

        restKursnaListaMockMvc.perform(post("/api/kursna-listas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(kursnaLista)))
                .andExpect(status().isBadRequest());

        List<KursnaLista> kursnaListas = kursnaListaRepository.findAll();
        assertThat(kursnaListas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBrojKursneListeIsRequired() throws Exception {
        int databaseSizeBeforeTest = kursnaListaRepository.findAll().size();
        // set the field null
        kursnaLista.setBrojKursneListe(null);

        // Create the KursnaLista, which fails.

        restKursnaListaMockMvc.perform(post("/api/kursna-listas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(kursnaLista)))
                .andExpect(status().isBadRequest());

        List<KursnaLista> kursnaListas = kursnaListaRepository.findAll();
        assertThat(kursnaListas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDatumPrimeneIsRequired() throws Exception {
        int databaseSizeBeforeTest = kursnaListaRepository.findAll().size();
        // set the field null
        kursnaLista.setDatumPrimene(null);

        // Create the KursnaLista, which fails.

        restKursnaListaMockMvc.perform(post("/api/kursna-listas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(kursnaLista)))
                .andExpect(status().isBadRequest());

        List<KursnaLista> kursnaListas = kursnaListaRepository.findAll();
        assertThat(kursnaListas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllKursnaListas() throws Exception {
        // Initialize the database
        kursnaListaRepository.saveAndFlush(kursnaLista);

        // Get all the kursnaListas
        restKursnaListaMockMvc.perform(get("/api/kursna-listas?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(kursnaLista.getId().intValue())))
                .andExpect(jsonPath("$.[*].datum").value(hasItem(DEFAULT_DATUM_STR)))
                .andExpect(jsonPath("$.[*].brojKursneListe").value(hasItem(DEFAULT_BROJ_KURSNE_LISTE)))
                .andExpect(jsonPath("$.[*].datumPrimene").value(hasItem(DEFAULT_DATUM_PRIMENE_STR)));
    }

    @Test
    @Transactional
    public void getKursnaLista() throws Exception {
        // Initialize the database
        kursnaListaRepository.saveAndFlush(kursnaLista);

        // Get the kursnaLista
        restKursnaListaMockMvc.perform(get("/api/kursna-listas/{id}", kursnaLista.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(kursnaLista.getId().intValue()))
            .andExpect(jsonPath("$.datum").value(DEFAULT_DATUM_STR))
            .andExpect(jsonPath("$.brojKursneListe").value(DEFAULT_BROJ_KURSNE_LISTE))
            .andExpect(jsonPath("$.datumPrimene").value(DEFAULT_DATUM_PRIMENE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingKursnaLista() throws Exception {
        // Get the kursnaLista
        restKursnaListaMockMvc.perform(get("/api/kursna-listas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKursnaLista() throws Exception {
        // Initialize the database
        kursnaListaRepository.saveAndFlush(kursnaLista);
        int databaseSizeBeforeUpdate = kursnaListaRepository.findAll().size();

        // Update the kursnaLista
        KursnaLista updatedKursnaLista = new KursnaLista();
        updatedKursnaLista.setId(kursnaLista.getId());
        updatedKursnaLista.setDatum(UPDATED_DATUM);
        updatedKursnaLista.setBrojKursneListe(UPDATED_BROJ_KURSNE_LISTE);
        updatedKursnaLista.setDatumPrimene(UPDATED_DATUM_PRIMENE);

        restKursnaListaMockMvc.perform(put("/api/kursna-listas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedKursnaLista)))
                .andExpect(status().isOk());

        // Validate the KursnaLista in the database
        List<KursnaLista> kursnaListas = kursnaListaRepository.findAll();
        assertThat(kursnaListas).hasSize(databaseSizeBeforeUpdate);
        KursnaLista testKursnaLista = kursnaListas.get(kursnaListas.size() - 1);
        assertThat(testKursnaLista.getDatum()).isEqualTo(UPDATED_DATUM);
        assertThat(testKursnaLista.getBrojKursneListe()).isEqualTo(UPDATED_BROJ_KURSNE_LISTE);
        assertThat(testKursnaLista.getDatumPrimene()).isEqualTo(UPDATED_DATUM_PRIMENE);
    }

    @Test
    @Transactional
    public void deleteKursnaLista() throws Exception {
        // Initialize the database
        kursnaListaRepository.saveAndFlush(kursnaLista);
        int databaseSizeBeforeDelete = kursnaListaRepository.findAll().size();

        // Get the kursnaLista
        restKursnaListaMockMvc.perform(delete("/api/kursna-listas/{id}", kursnaLista.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<KursnaLista> kursnaListas = kursnaListaRepository.findAll();
        assertThat(kursnaListas).hasSize(databaseSizeBeforeDelete - 1);
    }
}
