package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.PinfProApp;
import com.mycompany.myapp.domain.AnalitikaIzvoda;
import com.mycompany.myapp.repository.AnalitikaIzvodaRepository;

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
 * Test class for the AnalitikaIzvodaResource REST controller.
 *
 * @see AnalitikaIzvodaResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PinfProApp.class)
@WebAppConfiguration
@IntegrationTest
public class AnalitikaIzvodaResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final String DEFAULT_DUZNIK = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_DUZNIK = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_SVRHA = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_SVRHA = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_POVERILAC = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_POVERILAC = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATUM_PRIJEMA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_DATUM_PRIJEMA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATUM_PRIJEMA_STR = dateTimeFormatter.format(DEFAULT_DATUM_PRIJEMA);

    private static final ZonedDateTime DEFAULT_DATUM_VALUTE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_DATUM_VALUTE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATUM_VALUTE_STR = dateTimeFormatter.format(DEFAULT_DATUM_VALUTE);
    private static final String DEFAULT_RACUN_DUZNIKA = "AAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_RACUN_DUZNIKA = "BBBBBBBBBBBBBBBBBB";

    private static final Integer DEFAULT_MODEL_ZADUZENJA = 1;
    private static final Integer UPDATED_MODEL_ZADUZENJA = 2;
    private static final String DEFAULT_POZIV_NA_BROJ_ZADUZENJA = "AAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_POZIV_NA_BROJ_ZADUZENJA = "BBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_RACUN_POVERIOCA = "AAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_RACUN_POVERIOCA = "BBBBBBBBBBBBBBBBBB";

    private static final Integer DEFAULT_MODEL_ODOBRENJA = 1;
    private static final Integer UPDATED_MODEL_ODOBRENJA = 2;
    private static final String DEFAULT_POZIV_NA_BROJ_ODOBRENJA = "AAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_POZIV_NA_BROJ_ODOBRENJA = "BBBBBBBBBBBBBBBBBBBB";

    private static final Boolean DEFAULT_IS_HITNO = false;
    private static final Boolean UPDATED_IS_HITNO = true;

    private static final Double DEFAULT_IZNOS = 1D;
    private static final Double UPDATED_IZNOS = 2D;

    private static final Integer DEFAULT_TIP_GRESKE = 9;
    private static final Integer UPDATED_TIP_GRESKE = 8;
    private static final String DEFAULT_STATUS = "A";
    private static final String UPDATED_STATUS = "B";

    @Inject
    private AnalitikaIzvodaRepository analitikaIzvodaRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAnalitikaIzvodaMockMvc;

    private AnalitikaIzvoda analitikaIzvoda;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AnalitikaIzvodaResource analitikaIzvodaResource = new AnalitikaIzvodaResource();
        ReflectionTestUtils.setField(analitikaIzvodaResource, "analitikaIzvodaRepository", analitikaIzvodaRepository);
        this.restAnalitikaIzvodaMockMvc = MockMvcBuilders.standaloneSetup(analitikaIzvodaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        analitikaIzvoda = new AnalitikaIzvoda();
        analitikaIzvoda.setDuznik(DEFAULT_DUZNIK);
        analitikaIzvoda.setSvrha(DEFAULT_SVRHA);
        analitikaIzvoda.setPoverilac(DEFAULT_POVERILAC);
        analitikaIzvoda.setDatumPrijema(DEFAULT_DATUM_PRIJEMA);
        analitikaIzvoda.setDatumValute(DEFAULT_DATUM_VALUTE);
        analitikaIzvoda.setRacunDuznika(DEFAULT_RACUN_DUZNIKA);
        analitikaIzvoda.setModelZaduzenja(DEFAULT_MODEL_ZADUZENJA);
        analitikaIzvoda.setPozivNaBrojZaduzenja(DEFAULT_POZIV_NA_BROJ_ZADUZENJA);
        analitikaIzvoda.setRacunPoverioca(DEFAULT_RACUN_POVERIOCA);
        analitikaIzvoda.setModelOdobrenja(DEFAULT_MODEL_ODOBRENJA);
        analitikaIzvoda.setPozivNaBrojOdobrenja(DEFAULT_POZIV_NA_BROJ_ODOBRENJA);
        analitikaIzvoda.setIsHitno(DEFAULT_IS_HITNO);
        analitikaIzvoda.setIznos(DEFAULT_IZNOS);
        analitikaIzvoda.setTipGreske(DEFAULT_TIP_GRESKE);
        analitikaIzvoda.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createAnalitikaIzvoda() throws Exception {
        int databaseSizeBeforeCreate = analitikaIzvodaRepository.findAll().size();

        // Create the AnalitikaIzvoda

        restAnalitikaIzvodaMockMvc.perform(post("/api/analitika-izvodas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(analitikaIzvoda)))
                .andExpect(status().isCreated());

        // Validate the AnalitikaIzvoda in the database
        List<AnalitikaIzvoda> analitikaIzvodas = analitikaIzvodaRepository.findAll();
        assertThat(analitikaIzvodas).hasSize(databaseSizeBeforeCreate + 1);
        AnalitikaIzvoda testAnalitikaIzvoda = analitikaIzvodas.get(analitikaIzvodas.size() - 1);
        assertThat(testAnalitikaIzvoda.getDuznik()).isEqualTo(DEFAULT_DUZNIK);
        assertThat(testAnalitikaIzvoda.getSvrha()).isEqualTo(DEFAULT_SVRHA);
        assertThat(testAnalitikaIzvoda.getPoverilac()).isEqualTo(DEFAULT_POVERILAC);
        assertThat(testAnalitikaIzvoda.getDatumPrijema()).isEqualTo(DEFAULT_DATUM_PRIJEMA);
        assertThat(testAnalitikaIzvoda.getDatumValute()).isEqualTo(DEFAULT_DATUM_VALUTE);
        assertThat(testAnalitikaIzvoda.getRacunDuznika()).isEqualTo(DEFAULT_RACUN_DUZNIKA);
        assertThat(testAnalitikaIzvoda.getModelZaduzenja()).isEqualTo(DEFAULT_MODEL_ZADUZENJA);
        assertThat(testAnalitikaIzvoda.getPozivNaBrojZaduzenja()).isEqualTo(DEFAULT_POZIV_NA_BROJ_ZADUZENJA);
        assertThat(testAnalitikaIzvoda.getRacunPoverioca()).isEqualTo(DEFAULT_RACUN_POVERIOCA);
        assertThat(testAnalitikaIzvoda.getModelOdobrenja()).isEqualTo(DEFAULT_MODEL_ODOBRENJA);
        assertThat(testAnalitikaIzvoda.getPozivNaBrojOdobrenja()).isEqualTo(DEFAULT_POZIV_NA_BROJ_ODOBRENJA);
        assertThat(testAnalitikaIzvoda.isIsHitno()).isEqualTo(DEFAULT_IS_HITNO);
        assertThat(testAnalitikaIzvoda.getIznos()).isEqualTo(DEFAULT_IZNOS);
        assertThat(testAnalitikaIzvoda.getTipGreske()).isEqualTo(DEFAULT_TIP_GRESKE);
        assertThat(testAnalitikaIzvoda.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void checkDuznikIsRequired() throws Exception {
        int databaseSizeBeforeTest = analitikaIzvodaRepository.findAll().size();
        // set the field null
        analitikaIzvoda.setDuznik(null);

        // Create the AnalitikaIzvoda, which fails.

        restAnalitikaIzvodaMockMvc.perform(post("/api/analitika-izvodas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(analitikaIzvoda)))
                .andExpect(status().isBadRequest());

        List<AnalitikaIzvoda> analitikaIzvodas = analitikaIzvodaRepository.findAll();
        assertThat(analitikaIzvodas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSvrhaIsRequired() throws Exception {
        int databaseSizeBeforeTest = analitikaIzvodaRepository.findAll().size();
        // set the field null
        analitikaIzvoda.setSvrha(null);

        // Create the AnalitikaIzvoda, which fails.

        restAnalitikaIzvodaMockMvc.perform(post("/api/analitika-izvodas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(analitikaIzvoda)))
                .andExpect(status().isBadRequest());

        List<AnalitikaIzvoda> analitikaIzvodas = analitikaIzvodaRepository.findAll();
        assertThat(analitikaIzvodas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPoverilacIsRequired() throws Exception {
        int databaseSizeBeforeTest = analitikaIzvodaRepository.findAll().size();
        // set the field null
        analitikaIzvoda.setPoverilac(null);

        // Create the AnalitikaIzvoda, which fails.

        restAnalitikaIzvodaMockMvc.perform(post("/api/analitika-izvodas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(analitikaIzvoda)))
                .andExpect(status().isBadRequest());

        List<AnalitikaIzvoda> analitikaIzvodas = analitikaIzvodaRepository.findAll();
        assertThat(analitikaIzvodas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDatumPrijemaIsRequired() throws Exception {
        int databaseSizeBeforeTest = analitikaIzvodaRepository.findAll().size();
        // set the field null
        analitikaIzvoda.setDatumPrijema(null);

        // Create the AnalitikaIzvoda, which fails.

        restAnalitikaIzvodaMockMvc.perform(post("/api/analitika-izvodas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(analitikaIzvoda)))
                .andExpect(status().isBadRequest());

        List<AnalitikaIzvoda> analitikaIzvodas = analitikaIzvodaRepository.findAll();
        assertThat(analitikaIzvodas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDatumValuteIsRequired() throws Exception {
        int databaseSizeBeforeTest = analitikaIzvodaRepository.findAll().size();
        // set the field null
        analitikaIzvoda.setDatumValute(null);

        // Create the AnalitikaIzvoda, which fails.

        restAnalitikaIzvodaMockMvc.perform(post("/api/analitika-izvodas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(analitikaIzvoda)))
                .andExpect(status().isBadRequest());

        List<AnalitikaIzvoda> analitikaIzvodas = analitikaIzvodaRepository.findAll();
        assertThat(analitikaIzvodas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRacunDuznikaIsRequired() throws Exception {
        int databaseSizeBeforeTest = analitikaIzvodaRepository.findAll().size();
        // set the field null
        analitikaIzvoda.setRacunDuznika(null);

        // Create the AnalitikaIzvoda, which fails.

        restAnalitikaIzvodaMockMvc.perform(post("/api/analitika-izvodas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(analitikaIzvoda)))
                .andExpect(status().isBadRequest());

        List<AnalitikaIzvoda> analitikaIzvodas = analitikaIzvodaRepository.findAll();
        assertThat(analitikaIzvodas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsHitnoIsRequired() throws Exception {
        int databaseSizeBeforeTest = analitikaIzvodaRepository.findAll().size();
        // set the field null
        analitikaIzvoda.setIsHitno(null);

        // Create the AnalitikaIzvoda, which fails.

        restAnalitikaIzvodaMockMvc.perform(post("/api/analitika-izvodas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(analitikaIzvoda)))
                .andExpect(status().isBadRequest());

        List<AnalitikaIzvoda> analitikaIzvodas = analitikaIzvodaRepository.findAll();
        assertThat(analitikaIzvodas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIznosIsRequired() throws Exception {
        int databaseSizeBeforeTest = analitikaIzvodaRepository.findAll().size();
        // set the field null
        analitikaIzvoda.setIznos(null);

        // Create the AnalitikaIzvoda, which fails.

        restAnalitikaIzvodaMockMvc.perform(post("/api/analitika-izvodas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(analitikaIzvoda)))
                .andExpect(status().isBadRequest());

        List<AnalitikaIzvoda> analitikaIzvodas = analitikaIzvodaRepository.findAll();
        assertThat(analitikaIzvodas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTipGreskeIsRequired() throws Exception {
        int databaseSizeBeforeTest = analitikaIzvodaRepository.findAll().size();
        // set the field null
        analitikaIzvoda.setTipGreske(null);

        // Create the AnalitikaIzvoda, which fails.

        restAnalitikaIzvodaMockMvc.perform(post("/api/analitika-izvodas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(analitikaIzvoda)))
                .andExpect(status().isBadRequest());

        List<AnalitikaIzvoda> analitikaIzvodas = analitikaIzvodaRepository.findAll();
        assertThat(analitikaIzvodas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAnalitikaIzvodas() throws Exception {
        // Initialize the database
        analitikaIzvodaRepository.saveAndFlush(analitikaIzvoda);

        // Get all the analitikaIzvodas
        restAnalitikaIzvodaMockMvc.perform(get("/api/analitika-izvodas?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(analitikaIzvoda.getId().intValue())))
                .andExpect(jsonPath("$.[*].duznik").value(hasItem(DEFAULT_DUZNIK.toString())))
                .andExpect(jsonPath("$.[*].svrha").value(hasItem(DEFAULT_SVRHA.toString())))
                .andExpect(jsonPath("$.[*].poverilac").value(hasItem(DEFAULT_POVERILAC.toString())))
                .andExpect(jsonPath("$.[*].datumPrijema").value(hasItem(DEFAULT_DATUM_PRIJEMA_STR)))
                .andExpect(jsonPath("$.[*].datumValute").value(hasItem(DEFAULT_DATUM_VALUTE_STR)))
                .andExpect(jsonPath("$.[*].racunDuznika").value(hasItem(DEFAULT_RACUN_DUZNIKA.toString())))
                .andExpect(jsonPath("$.[*].modelZaduzenja").value(hasItem(DEFAULT_MODEL_ZADUZENJA)))
                .andExpect(jsonPath("$.[*].pozivNaBrojZaduzenja").value(hasItem(DEFAULT_POZIV_NA_BROJ_ZADUZENJA.toString())))
                .andExpect(jsonPath("$.[*].racunPoverioca").value(hasItem(DEFAULT_RACUN_POVERIOCA.toString())))
                .andExpect(jsonPath("$.[*].modelOdobrenja").value(hasItem(DEFAULT_MODEL_ODOBRENJA)))
                .andExpect(jsonPath("$.[*].pozivNaBrojOdobrenja").value(hasItem(DEFAULT_POZIV_NA_BROJ_ODOBRENJA.toString())))
                .andExpect(jsonPath("$.[*].isHitno").value(hasItem(DEFAULT_IS_HITNO.booleanValue())))
                .andExpect(jsonPath("$.[*].iznos").value(hasItem(DEFAULT_IZNOS.doubleValue())))
                .andExpect(jsonPath("$.[*].tipGreske").value(hasItem(DEFAULT_TIP_GRESKE)))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getAnalitikaIzvoda() throws Exception {
        // Initialize the database
        analitikaIzvodaRepository.saveAndFlush(analitikaIzvoda);

        // Get the analitikaIzvoda
        restAnalitikaIzvodaMockMvc.perform(get("/api/analitika-izvodas/{id}", analitikaIzvoda.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(analitikaIzvoda.getId().intValue()))
            .andExpect(jsonPath("$.duznik").value(DEFAULT_DUZNIK.toString()))
            .andExpect(jsonPath("$.svrha").value(DEFAULT_SVRHA.toString()))
            .andExpect(jsonPath("$.poverilac").value(DEFAULT_POVERILAC.toString()))
            .andExpect(jsonPath("$.datumPrijema").value(DEFAULT_DATUM_PRIJEMA_STR))
            .andExpect(jsonPath("$.datumValute").value(DEFAULT_DATUM_VALUTE_STR))
            .andExpect(jsonPath("$.racunDuznika").value(DEFAULT_RACUN_DUZNIKA.toString()))
            .andExpect(jsonPath("$.modelZaduzenja").value(DEFAULT_MODEL_ZADUZENJA))
            .andExpect(jsonPath("$.pozivNaBrojZaduzenja").value(DEFAULT_POZIV_NA_BROJ_ZADUZENJA.toString()))
            .andExpect(jsonPath("$.racunPoverioca").value(DEFAULT_RACUN_POVERIOCA.toString()))
            .andExpect(jsonPath("$.modelOdobrenja").value(DEFAULT_MODEL_ODOBRENJA))
            .andExpect(jsonPath("$.pozivNaBrojOdobrenja").value(DEFAULT_POZIV_NA_BROJ_ODOBRENJA.toString()))
            .andExpect(jsonPath("$.isHitno").value(DEFAULT_IS_HITNO.booleanValue()))
            .andExpect(jsonPath("$.iznos").value(DEFAULT_IZNOS.doubleValue()))
            .andExpect(jsonPath("$.tipGreske").value(DEFAULT_TIP_GRESKE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAnalitikaIzvoda() throws Exception {
        // Get the analitikaIzvoda
        restAnalitikaIzvodaMockMvc.perform(get("/api/analitika-izvodas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnalitikaIzvoda() throws Exception {
        // Initialize the database
        analitikaIzvodaRepository.saveAndFlush(analitikaIzvoda);
        int databaseSizeBeforeUpdate = analitikaIzvodaRepository.findAll().size();

        // Update the analitikaIzvoda
        AnalitikaIzvoda updatedAnalitikaIzvoda = new AnalitikaIzvoda();
        updatedAnalitikaIzvoda.setId(analitikaIzvoda.getId());
        updatedAnalitikaIzvoda.setDuznik(UPDATED_DUZNIK);
        updatedAnalitikaIzvoda.setSvrha(UPDATED_SVRHA);
        updatedAnalitikaIzvoda.setPoverilac(UPDATED_POVERILAC);
        updatedAnalitikaIzvoda.setDatumPrijema(UPDATED_DATUM_PRIJEMA);
        updatedAnalitikaIzvoda.setDatumValute(UPDATED_DATUM_VALUTE);
        updatedAnalitikaIzvoda.setRacunDuznika(UPDATED_RACUN_DUZNIKA);
        updatedAnalitikaIzvoda.setModelZaduzenja(UPDATED_MODEL_ZADUZENJA);
        updatedAnalitikaIzvoda.setPozivNaBrojZaduzenja(UPDATED_POZIV_NA_BROJ_ZADUZENJA);
        updatedAnalitikaIzvoda.setRacunPoverioca(UPDATED_RACUN_POVERIOCA);
        updatedAnalitikaIzvoda.setModelOdobrenja(UPDATED_MODEL_ODOBRENJA);
        updatedAnalitikaIzvoda.setPozivNaBrojOdobrenja(UPDATED_POZIV_NA_BROJ_ODOBRENJA);
        updatedAnalitikaIzvoda.setIsHitno(UPDATED_IS_HITNO);
        updatedAnalitikaIzvoda.setIznos(UPDATED_IZNOS);
        updatedAnalitikaIzvoda.setTipGreske(UPDATED_TIP_GRESKE);
        updatedAnalitikaIzvoda.setStatus(UPDATED_STATUS);

        restAnalitikaIzvodaMockMvc.perform(put("/api/analitika-izvodas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedAnalitikaIzvoda)))
                .andExpect(status().isOk());

        // Validate the AnalitikaIzvoda in the database
        List<AnalitikaIzvoda> analitikaIzvodas = analitikaIzvodaRepository.findAll();
        assertThat(analitikaIzvodas).hasSize(databaseSizeBeforeUpdate);
        AnalitikaIzvoda testAnalitikaIzvoda = analitikaIzvodas.get(analitikaIzvodas.size() - 1);
        assertThat(testAnalitikaIzvoda.getDuznik()).isEqualTo(UPDATED_DUZNIK);
        assertThat(testAnalitikaIzvoda.getSvrha()).isEqualTo(UPDATED_SVRHA);
        assertThat(testAnalitikaIzvoda.getPoverilac()).isEqualTo(UPDATED_POVERILAC);
        assertThat(testAnalitikaIzvoda.getDatumPrijema()).isEqualTo(UPDATED_DATUM_PRIJEMA);
        assertThat(testAnalitikaIzvoda.getDatumValute()).isEqualTo(UPDATED_DATUM_VALUTE);
        assertThat(testAnalitikaIzvoda.getRacunDuznika()).isEqualTo(UPDATED_RACUN_DUZNIKA);
        assertThat(testAnalitikaIzvoda.getModelZaduzenja()).isEqualTo(UPDATED_MODEL_ZADUZENJA);
        assertThat(testAnalitikaIzvoda.getPozivNaBrojZaduzenja()).isEqualTo(UPDATED_POZIV_NA_BROJ_ZADUZENJA);
        assertThat(testAnalitikaIzvoda.getRacunPoverioca()).isEqualTo(UPDATED_RACUN_POVERIOCA);
        assertThat(testAnalitikaIzvoda.getModelOdobrenja()).isEqualTo(UPDATED_MODEL_ODOBRENJA);
        assertThat(testAnalitikaIzvoda.getPozivNaBrojOdobrenja()).isEqualTo(UPDATED_POZIV_NA_BROJ_ODOBRENJA);
        assertThat(testAnalitikaIzvoda.isIsHitno()).isEqualTo(UPDATED_IS_HITNO);
        assertThat(testAnalitikaIzvoda.getIznos()).isEqualTo(UPDATED_IZNOS);
        assertThat(testAnalitikaIzvoda.getTipGreske()).isEqualTo(UPDATED_TIP_GRESKE);
        assertThat(testAnalitikaIzvoda.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteAnalitikaIzvoda() throws Exception {
        // Initialize the database
        analitikaIzvodaRepository.saveAndFlush(analitikaIzvoda);
        int databaseSizeBeforeDelete = analitikaIzvodaRepository.findAll().size();

        // Get the analitikaIzvoda
        restAnalitikaIzvodaMockMvc.perform(delete("/api/analitika-izvodas/{id}", analitikaIzvoda.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<AnalitikaIzvoda> analitikaIzvodas = analitikaIzvodaRepository.findAll();
        assertThat(analitikaIzvodas).hasSize(databaseSizeBeforeDelete - 1);
    }
}
