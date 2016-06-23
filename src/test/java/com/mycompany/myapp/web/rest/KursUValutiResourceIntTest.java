package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.PinfProApp;
import com.mycompany.myapp.domain.KursUValuti;
import com.mycompany.myapp.repository.KursUValutiRepository;

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
 * Test class for the KursUValutiResource REST controller.
 *
 * @see KursUValutiResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PinfProApp.class)
@WebAppConfiguration
@IntegrationTest
public class KursUValutiResourceIntTest {


    private static final Integer DEFAULT_REDNI_BROJ = 1;
    private static final Integer UPDATED_REDNI_BROJ = 2;

    private static final Double DEFAULT_KURS_KUPOVNI = 1D;
    private static final Double UPDATED_KURS_KUPOVNI = 2D;

    private static final Double DEFAULT_KURS_SREDNJI = 1D;
    private static final Double UPDATED_KURS_SREDNJI = 2D;

    private static final Double DEFAULT_KURS_PRODAJNI = 1D;
    private static final Double UPDATED_KURS_PRODAJNI = 2D;

    @Inject
    private KursUValutiRepository kursUValutiRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restKursUValutiMockMvc;

    private KursUValuti kursUValuti;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        KursUValutiResource kursUValutiResource = new KursUValutiResource();
        ReflectionTestUtils.setField(kursUValutiResource, "kursUValutiRepository", kursUValutiRepository);
        this.restKursUValutiMockMvc = MockMvcBuilders.standaloneSetup(kursUValutiResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        kursUValuti = new KursUValuti();
        kursUValuti.setRedniBroj(DEFAULT_REDNI_BROJ);
        kursUValuti.setKursKupovni(DEFAULT_KURS_KUPOVNI);
        kursUValuti.setKursSrednji(DEFAULT_KURS_SREDNJI);
        kursUValuti.setKursProdajni(DEFAULT_KURS_PRODAJNI);
    }

    @Test
    @Transactional
    public void createKursUValuti() throws Exception {
        int databaseSizeBeforeCreate = kursUValutiRepository.findAll().size();

        // Create the KursUValuti

        restKursUValutiMockMvc.perform(post("/api/kurs-u-valutis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(kursUValuti)))
                .andExpect(status().isCreated());

        // Validate the KursUValuti in the database
        List<KursUValuti> kursUValutis = kursUValutiRepository.findAll();
        assertThat(kursUValutis).hasSize(databaseSizeBeforeCreate + 1);
        KursUValuti testKursUValuti = kursUValutis.get(kursUValutis.size() - 1);
        assertThat(testKursUValuti.getRedniBroj()).isEqualTo(DEFAULT_REDNI_BROJ);
        assertThat(testKursUValuti.getKursKupovni()).isEqualTo(DEFAULT_KURS_KUPOVNI);
        assertThat(testKursUValuti.getKursSrednji()).isEqualTo(DEFAULT_KURS_SREDNJI);
        assertThat(testKursUValuti.getKursProdajni()).isEqualTo(DEFAULT_KURS_PRODAJNI);
    }

    @Test
    @Transactional
    public void checkRedniBrojIsRequired() throws Exception {
        int databaseSizeBeforeTest = kursUValutiRepository.findAll().size();
        // set the field null
        kursUValuti.setRedniBroj(null);

        // Create the KursUValuti, which fails.

        restKursUValutiMockMvc.perform(post("/api/kurs-u-valutis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(kursUValuti)))
                .andExpect(status().isBadRequest());

        List<KursUValuti> kursUValutis = kursUValutiRepository.findAll();
        assertThat(kursUValutis).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkKursKupovniIsRequired() throws Exception {
        int databaseSizeBeforeTest = kursUValutiRepository.findAll().size();
        // set the field null
        kursUValuti.setKursKupovni(null);

        // Create the KursUValuti, which fails.

        restKursUValutiMockMvc.perform(post("/api/kurs-u-valutis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(kursUValuti)))
                .andExpect(status().isBadRequest());

        List<KursUValuti> kursUValutis = kursUValutiRepository.findAll();
        assertThat(kursUValutis).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkKursSrednjiIsRequired() throws Exception {
        int databaseSizeBeforeTest = kursUValutiRepository.findAll().size();
        // set the field null
        kursUValuti.setKursSrednji(null);

        // Create the KursUValuti, which fails.

        restKursUValutiMockMvc.perform(post("/api/kurs-u-valutis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(kursUValuti)))
                .andExpect(status().isBadRequest());

        List<KursUValuti> kursUValutis = kursUValutiRepository.findAll();
        assertThat(kursUValutis).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkKursProdajniIsRequired() throws Exception {
        int databaseSizeBeforeTest = kursUValutiRepository.findAll().size();
        // set the field null
        kursUValuti.setKursProdajni(null);

        // Create the KursUValuti, which fails.

        restKursUValutiMockMvc.perform(post("/api/kurs-u-valutis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(kursUValuti)))
                .andExpect(status().isBadRequest());

        List<KursUValuti> kursUValutis = kursUValutiRepository.findAll();
        assertThat(kursUValutis).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllKursUValutis() throws Exception {
        // Initialize the database
        kursUValutiRepository.saveAndFlush(kursUValuti);

        // Get all the kursUValutis
        restKursUValutiMockMvc.perform(get("/api/kurs-u-valutis?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(kursUValuti.getId().intValue())))
                .andExpect(jsonPath("$.[*].redniBroj").value(hasItem(DEFAULT_REDNI_BROJ)))
                .andExpect(jsonPath("$.[*].kursKupovni").value(hasItem(DEFAULT_KURS_KUPOVNI.doubleValue())))
                .andExpect(jsonPath("$.[*].kursSrednji").value(hasItem(DEFAULT_KURS_SREDNJI.doubleValue())))
                .andExpect(jsonPath("$.[*].kursProdajni").value(hasItem(DEFAULT_KURS_PRODAJNI.doubleValue())));
    }

    @Test
    @Transactional
    public void getKursUValuti() throws Exception {
        // Initialize the database
        kursUValutiRepository.saveAndFlush(kursUValuti);

        // Get the kursUValuti
        restKursUValutiMockMvc.perform(get("/api/kurs-u-valutis/{id}", kursUValuti.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(kursUValuti.getId().intValue()))
            .andExpect(jsonPath("$.redniBroj").value(DEFAULT_REDNI_BROJ))
            .andExpect(jsonPath("$.kursKupovni").value(DEFAULT_KURS_KUPOVNI.doubleValue()))
            .andExpect(jsonPath("$.kursSrednji").value(DEFAULT_KURS_SREDNJI.doubleValue()))
            .andExpect(jsonPath("$.kursProdajni").value(DEFAULT_KURS_PRODAJNI.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingKursUValuti() throws Exception {
        // Get the kursUValuti
        restKursUValutiMockMvc.perform(get("/api/kurs-u-valutis/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKursUValuti() throws Exception {
        // Initialize the database
        kursUValutiRepository.saveAndFlush(kursUValuti);
        int databaseSizeBeforeUpdate = kursUValutiRepository.findAll().size();

        // Update the kursUValuti
        KursUValuti updatedKursUValuti = new KursUValuti();
        updatedKursUValuti.setId(kursUValuti.getId());
        updatedKursUValuti.setRedniBroj(UPDATED_REDNI_BROJ);
        updatedKursUValuti.setKursKupovni(UPDATED_KURS_KUPOVNI);
        updatedKursUValuti.setKursSrednji(UPDATED_KURS_SREDNJI);
        updatedKursUValuti.setKursProdajni(UPDATED_KURS_PRODAJNI);

        restKursUValutiMockMvc.perform(put("/api/kurs-u-valutis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedKursUValuti)))
                .andExpect(status().isOk());

        // Validate the KursUValuti in the database
        List<KursUValuti> kursUValutis = kursUValutiRepository.findAll();
        assertThat(kursUValutis).hasSize(databaseSizeBeforeUpdate);
        KursUValuti testKursUValuti = kursUValutis.get(kursUValutis.size() - 1);
        assertThat(testKursUValuti.getRedniBroj()).isEqualTo(UPDATED_REDNI_BROJ);
        assertThat(testKursUValuti.getKursKupovni()).isEqualTo(UPDATED_KURS_KUPOVNI);
        assertThat(testKursUValuti.getKursSrednji()).isEqualTo(UPDATED_KURS_SREDNJI);
        assertThat(testKursUValuti.getKursProdajni()).isEqualTo(UPDATED_KURS_PRODAJNI);
    }

    @Test
    @Transactional
    public void deleteKursUValuti() throws Exception {
        // Initialize the database
        kursUValutiRepository.saveAndFlush(kursUValuti);
        int databaseSizeBeforeDelete = kursUValutiRepository.findAll().size();

        // Get the kursUValuti
        restKursUValutiMockMvc.perform(delete("/api/kurs-u-valutis/{id}", kursUValuti.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<KursUValuti> kursUValutis = kursUValutiRepository.findAll();
        assertThat(kursUValutis).hasSize(databaseSizeBeforeDelete - 1);
    }
}
