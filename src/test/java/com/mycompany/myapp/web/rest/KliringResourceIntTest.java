package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.PinfProApp;
import com.mycompany.myapp.domain.Kliring;
import com.mycompany.myapp.repository.KliringRepository;

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
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the KliringResource REST controller.
 *
 * @see KliringResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PinfProApp.class)
@WebAppConfiguration
@IntegrationTest
public class KliringResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final String DEFAULT_ID_PORUKE = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_ID_PORUKE = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_SWWIFT_DUZNIKA = "AAAAAAAA";
    private static final String UPDATED_SWWIFT_DUZNIKA = "BBBBBBBB";
    private static final String DEFAULT_OBRACUNSKI_RACUN_DUZNIKA = "AAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_OBRACUNSKI_RACUN_DUZNIKA = "BBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_SWIFT_POVERIOCA = "AAAAAAAA";
    private static final String UPDATED_SWIFT_POVERIOCA = "BBBBBBBB";
    private static final String DEFAULT_OBRACUNSKI_RACUN_POVERIOCA = "AAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_OBRACUNSKI_RACUN_POVERIOCA = "BBBBBBBBBBBBBBBBBB";

    private static final Double DEFAULT_UKUPAN_IZNOS = 1D;
    private static final Double UPDATED_UKUPAN_IZNOS = 2D;

    private static final LocalDate DEFAULT_DATUM_VALUTE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUM_VALUTE = LocalDate.now(ZoneId.systemDefault());

    private static final ZonedDateTime DEFAULT_DATUM = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_DATUM = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATUM_STR = dateTimeFormatter.format(DEFAULT_DATUM);

    @Inject
    private KliringRepository kliringRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restKliringMockMvc;

    private Kliring kliring;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        KliringResource kliringResource = new KliringResource();
        ReflectionTestUtils.setField(kliringResource, "kliringRepository", kliringRepository);
        this.restKliringMockMvc = MockMvcBuilders.standaloneSetup(kliringResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        kliring = new Kliring();
        kliring.setIdPoruke(DEFAULT_ID_PORUKE);
        kliring.setSwwift_duznika(DEFAULT_SWWIFT_DUZNIKA);
        kliring.setObracunskiRacunDuznika(DEFAULT_OBRACUNSKI_RACUN_DUZNIKA);
        kliring.setSwift_poverioca(DEFAULT_SWIFT_POVERIOCA);
        kliring.setObracunskiRacunPoverioca(DEFAULT_OBRACUNSKI_RACUN_POVERIOCA);
        kliring.setUkupanIznos(DEFAULT_UKUPAN_IZNOS);
        kliring.setDatumValute(DEFAULT_DATUM_VALUTE);
        kliring.setDatum(DEFAULT_DATUM);
    }

    @Test
    @Transactional
    public void createKliring() throws Exception {
        int databaseSizeBeforeCreate = kliringRepository.findAll().size();

        // Create the Kliring

        restKliringMockMvc.perform(post("/api/klirings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(kliring)))
                .andExpect(status().isCreated());

        // Validate the Kliring in the database
        List<Kliring> klirings = kliringRepository.findAll();
        assertThat(klirings).hasSize(databaseSizeBeforeCreate + 1);
        Kliring testKliring = klirings.get(klirings.size() - 1);
        assertThat(testKliring.getIdPoruke()).isEqualTo(DEFAULT_ID_PORUKE);
        assertThat(testKliring.getSwwift_duznika()).isEqualTo(DEFAULT_SWWIFT_DUZNIKA);
        assertThat(testKliring.getObracunskiRacunDuznika()).isEqualTo(DEFAULT_OBRACUNSKI_RACUN_DUZNIKA);
        assertThat(testKliring.getSwift_poverioca()).isEqualTo(DEFAULT_SWIFT_POVERIOCA);
        assertThat(testKliring.getObracunskiRacunPoverioca()).isEqualTo(DEFAULT_OBRACUNSKI_RACUN_POVERIOCA);
        assertThat(testKliring.getUkupanIznos()).isEqualTo(DEFAULT_UKUPAN_IZNOS);
        assertThat(testKliring.getDatumValute()).isEqualTo(DEFAULT_DATUM_VALUTE);
        assertThat(testKliring.getDatum()).isEqualTo(DEFAULT_DATUM);
    }

    @Test
    @Transactional
    public void checkIdPorukeIsRequired() throws Exception {
        int databaseSizeBeforeTest = kliringRepository.findAll().size();
        // set the field null
        kliring.setIdPoruke(null);

        // Create the Kliring, which fails.

        restKliringMockMvc.perform(post("/api/klirings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(kliring)))
                .andExpect(status().isBadRequest());

        List<Kliring> klirings = kliringRepository.findAll();
        assertThat(klirings).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSwwift_duznikaIsRequired() throws Exception {
        int databaseSizeBeforeTest = kliringRepository.findAll().size();
        // set the field null
        kliring.setSwwift_duznika(null);

        // Create the Kliring, which fails.

        restKliringMockMvc.perform(post("/api/klirings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(kliring)))
                .andExpect(status().isBadRequest());

        List<Kliring> klirings = kliringRepository.findAll();
        assertThat(klirings).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkObracunskiRacunDuznikaIsRequired() throws Exception {
        int databaseSizeBeforeTest = kliringRepository.findAll().size();
        // set the field null
        kliring.setObracunskiRacunDuznika(null);

        // Create the Kliring, which fails.

        restKliringMockMvc.perform(post("/api/klirings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(kliring)))
                .andExpect(status().isBadRequest());

        List<Kliring> klirings = kliringRepository.findAll();
        assertThat(klirings).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSwift_poveriocaIsRequired() throws Exception {
        int databaseSizeBeforeTest = kliringRepository.findAll().size();
        // set the field null
        kliring.setSwift_poverioca(null);

        // Create the Kliring, which fails.

        restKliringMockMvc.perform(post("/api/klirings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(kliring)))
                .andExpect(status().isBadRequest());

        List<Kliring> klirings = kliringRepository.findAll();
        assertThat(klirings).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkObracunskiRacunPoveriocaIsRequired() throws Exception {
        int databaseSizeBeforeTest = kliringRepository.findAll().size();
        // set the field null
        kliring.setObracunskiRacunPoverioca(null);

        // Create the Kliring, which fails.

        restKliringMockMvc.perform(post("/api/klirings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(kliring)))
                .andExpect(status().isBadRequest());

        List<Kliring> klirings = kliringRepository.findAll();
        assertThat(klirings).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUkupanIznosIsRequired() throws Exception {
        int databaseSizeBeforeTest = kliringRepository.findAll().size();
        // set the field null
        kliring.setUkupanIznos(null);

        // Create the Kliring, which fails.

        restKliringMockMvc.perform(post("/api/klirings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(kliring)))
                .andExpect(status().isBadRequest());

        List<Kliring> klirings = kliringRepository.findAll();
        assertThat(klirings).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDatumValuteIsRequired() throws Exception {
        int databaseSizeBeforeTest = kliringRepository.findAll().size();
        // set the field null
        kliring.setDatumValute(null);

        // Create the Kliring, which fails.

        restKliringMockMvc.perform(post("/api/klirings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(kliring)))
                .andExpect(status().isBadRequest());

        List<Kliring> klirings = kliringRepository.findAll();
        assertThat(klirings).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDatumIsRequired() throws Exception {
        int databaseSizeBeforeTest = kliringRepository.findAll().size();
        // set the field null
        kliring.setDatum(null);

        // Create the Kliring, which fails.

        restKliringMockMvc.perform(post("/api/klirings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(kliring)))
                .andExpect(status().isBadRequest());

        List<Kliring> klirings = kliringRepository.findAll();
        assertThat(klirings).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllKlirings() throws Exception {
        // Initialize the database
        kliringRepository.saveAndFlush(kliring);

        // Get all the klirings
        restKliringMockMvc.perform(get("/api/klirings?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(kliring.getId().intValue())))
                .andExpect(jsonPath("$.[*].idPoruke").value(hasItem(DEFAULT_ID_PORUKE.toString())))
                .andExpect(jsonPath("$.[*].swwift_duznika").value(hasItem(DEFAULT_SWWIFT_DUZNIKA.toString())))
                .andExpect(jsonPath("$.[*].obracunskiRacunDuznika").value(hasItem(DEFAULT_OBRACUNSKI_RACUN_DUZNIKA.toString())))
                .andExpect(jsonPath("$.[*].swift_poverioca").value(hasItem(DEFAULT_SWIFT_POVERIOCA.toString())))
                .andExpect(jsonPath("$.[*].obracunskiRacunPoverioca").value(hasItem(DEFAULT_OBRACUNSKI_RACUN_POVERIOCA.toString())))
                .andExpect(jsonPath("$.[*].ukupanIznos").value(hasItem(DEFAULT_UKUPAN_IZNOS.doubleValue())))
                .andExpect(jsonPath("$.[*].datumValute").value(hasItem(DEFAULT_DATUM_VALUTE.toString())))
                .andExpect(jsonPath("$.[*].datum").value(hasItem(DEFAULT_DATUM_STR)));
    }

    @Test
    @Transactional
    public void getKliring() throws Exception {
        // Initialize the database
        kliringRepository.saveAndFlush(kliring);

        // Get the kliring
        restKliringMockMvc.perform(get("/api/klirings/{id}", kliring.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(kliring.getId().intValue()))
            .andExpect(jsonPath("$.idPoruke").value(DEFAULT_ID_PORUKE.toString()))
            .andExpect(jsonPath("$.swwift_duznika").value(DEFAULT_SWWIFT_DUZNIKA.toString()))
            .andExpect(jsonPath("$.obracunskiRacunDuznika").value(DEFAULT_OBRACUNSKI_RACUN_DUZNIKA.toString()))
            .andExpect(jsonPath("$.swift_poverioca").value(DEFAULT_SWIFT_POVERIOCA.toString()))
            .andExpect(jsonPath("$.obracunskiRacunPoverioca").value(DEFAULT_OBRACUNSKI_RACUN_POVERIOCA.toString()))
            .andExpect(jsonPath("$.ukupanIznos").value(DEFAULT_UKUPAN_IZNOS.doubleValue()))
            .andExpect(jsonPath("$.datumValute").value(DEFAULT_DATUM_VALUTE.toString()))
            .andExpect(jsonPath("$.datum").value(DEFAULT_DATUM_STR));
    }

    @Test
    @Transactional
    public void getNonExistingKliring() throws Exception {
        // Get the kliring
        restKliringMockMvc.perform(get("/api/klirings/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKliring() throws Exception {
        // Initialize the database
        kliringRepository.saveAndFlush(kliring);
        int databaseSizeBeforeUpdate = kliringRepository.findAll().size();

        // Update the kliring
        Kliring updatedKliring = new Kliring();
        updatedKliring.setId(kliring.getId());
        updatedKliring.setIdPoruke(UPDATED_ID_PORUKE);
        updatedKliring.setSwwift_duznika(UPDATED_SWWIFT_DUZNIKA);
        updatedKliring.setObracunskiRacunDuznika(UPDATED_OBRACUNSKI_RACUN_DUZNIKA);
        updatedKliring.setSwift_poverioca(UPDATED_SWIFT_POVERIOCA);
        updatedKliring.setObracunskiRacunPoverioca(UPDATED_OBRACUNSKI_RACUN_POVERIOCA);
        updatedKliring.setUkupanIznos(UPDATED_UKUPAN_IZNOS);
        updatedKliring.setDatumValute(UPDATED_DATUM_VALUTE);
        updatedKliring.setDatum(UPDATED_DATUM);

        restKliringMockMvc.perform(put("/api/klirings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedKliring)))
                .andExpect(status().isOk());

        // Validate the Kliring in the database
        List<Kliring> klirings = kliringRepository.findAll();
        assertThat(klirings).hasSize(databaseSizeBeforeUpdate);
        Kliring testKliring = klirings.get(klirings.size() - 1);
        assertThat(testKliring.getIdPoruke()).isEqualTo(UPDATED_ID_PORUKE);
        assertThat(testKliring.getSwwift_duznika()).isEqualTo(UPDATED_SWWIFT_DUZNIKA);
        assertThat(testKliring.getObracunskiRacunDuznika()).isEqualTo(UPDATED_OBRACUNSKI_RACUN_DUZNIKA);
        assertThat(testKliring.getSwift_poverioca()).isEqualTo(UPDATED_SWIFT_POVERIOCA);
        assertThat(testKliring.getObracunskiRacunPoverioca()).isEqualTo(UPDATED_OBRACUNSKI_RACUN_POVERIOCA);
        assertThat(testKliring.getUkupanIznos()).isEqualTo(UPDATED_UKUPAN_IZNOS);
        assertThat(testKliring.getDatumValute()).isEqualTo(UPDATED_DATUM_VALUTE);
        assertThat(testKliring.getDatum()).isEqualTo(UPDATED_DATUM);
    }

    @Test
    @Transactional
    public void deleteKliring() throws Exception {
        // Initialize the database
        kliringRepository.saveAndFlush(kliring);
        int databaseSizeBeforeDelete = kliringRepository.findAll().size();

        // Get the kliring
        restKliringMockMvc.perform(delete("/api/klirings/{id}", kliring.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Kliring> klirings = kliringRepository.findAll();
        assertThat(klirings).hasSize(databaseSizeBeforeDelete - 1);
    }
}
