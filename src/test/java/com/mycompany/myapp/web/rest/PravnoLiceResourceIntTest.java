package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.PinfProApp;
import com.mycompany.myapp.domain.PravnoLice;
import com.mycompany.myapp.repository.PravnoLiceRepository;

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
 * Test class for the PravnoLiceResource REST controller.
 *
 * @see PravnoLiceResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PinfProApp.class)
@WebAppConfiguration
@IntegrationTest
public class PravnoLiceResourceIntTest {

    private static final String DEFAULT_IME_ODGOVORNOG_LICA = "AAAAA";
    private static final String UPDATED_IME_ODGOVORNOG_LICA = "BBBBB";
    private static final String DEFAULT_PREZIME_ODGOVORNOG_LICA = "AAAAA";
    private static final String UPDATED_PREZIME_ODGOVORNOG_LICA = "BBBBB";
    private static final String DEFAULT_JMBG = "AAAAA";
    private static final String UPDATED_JMBG = "BBBBB";

    @Inject
    private PravnoLiceRepository pravnoLiceRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPravnoLiceMockMvc;

    private PravnoLice pravnoLice;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PravnoLiceResource pravnoLiceResource = new PravnoLiceResource();
        ReflectionTestUtils.setField(pravnoLiceResource, "pravnoLiceRepository", pravnoLiceRepository);
        this.restPravnoLiceMockMvc = MockMvcBuilders.standaloneSetup(pravnoLiceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        pravnoLice = new PravnoLice();
        pravnoLice.setImeOdgovornogLica(DEFAULT_IME_ODGOVORNOG_LICA);
        pravnoLice.setPrezimeOdgovornogLica(DEFAULT_PREZIME_ODGOVORNOG_LICA);
        pravnoLice.setJmbg(DEFAULT_JMBG);
    }

    @Test
    @Transactional
    public void createPravnoLice() throws Exception {
        int databaseSizeBeforeCreate = pravnoLiceRepository.findAll().size();

        // Create the PravnoLice

        restPravnoLiceMockMvc.perform(post("/api/pravno-lice")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pravnoLice)))
                .andExpect(status().isCreated());

        // Validate the PravnoLice in the database
        List<PravnoLice> pravnoLice = pravnoLiceRepository.findAll();
        assertThat(pravnoLice).hasSize(databaseSizeBeforeCreate + 1);
        PravnoLice testPravnoLice = pravnoLice.get(pravnoLice.size() - 1);
        assertThat(testPravnoLice.getImeOdgovornogLica()).isEqualTo(DEFAULT_IME_ODGOVORNOG_LICA);
        assertThat(testPravnoLice.getPrezimeOdgovornogLica()).isEqualTo(DEFAULT_PREZIME_ODGOVORNOG_LICA);
        assertThat(testPravnoLice.getJmbg()).isEqualTo(DEFAULT_JMBG);
    }

    @Test
    @Transactional
    public void checkImeOdgovornogLicaIsRequired() throws Exception {
        int databaseSizeBeforeTest = pravnoLiceRepository.findAll().size();
        // set the field null
        pravnoLice.setImeOdgovornogLica(null);

        // Create the PravnoLice, which fails.

        restPravnoLiceMockMvc.perform(post("/api/pravno-lice")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pravnoLice)))
                .andExpect(status().isBadRequest());

        List<PravnoLice> pravnoLice = pravnoLiceRepository.findAll();
        assertThat(pravnoLice).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrezimeOdgovornogLicaIsRequired() throws Exception {
        int databaseSizeBeforeTest = pravnoLiceRepository.findAll().size();
        // set the field null
        pravnoLice.setPrezimeOdgovornogLica(null);

        // Create the PravnoLice, which fails.

        restPravnoLiceMockMvc.perform(post("/api/pravno-lice")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pravnoLice)))
                .andExpect(status().isBadRequest());

        List<PravnoLice> pravnoLice = pravnoLiceRepository.findAll();
        assertThat(pravnoLice).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPravnoLice() throws Exception {
        // Initialize the database
        pravnoLiceRepository.saveAndFlush(pravnoLice);

        // Get all the pravnoLice
        restPravnoLiceMockMvc.perform(get("/api/pravno-lice?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(pravnoLice.getId().intValue())))
                .andExpect(jsonPath("$.[*].imeOdgovornogLica").value(hasItem(DEFAULT_IME_ODGOVORNOG_LICA.toString())))
                .andExpect(jsonPath("$.[*].prezimeOdgovornogLica").value(hasItem(DEFAULT_PREZIME_ODGOVORNOG_LICA.toString())))
                .andExpect(jsonPath("$.[*].jmbg").value(hasItem(DEFAULT_JMBG.toString())));
    }

    @Test
    @Transactional
    public void getPravnoLice() throws Exception {
        // Initialize the database
        pravnoLiceRepository.saveAndFlush(pravnoLice);

        // Get the pravnoLice
        restPravnoLiceMockMvc.perform(get("/api/pravno-lice/{id}", pravnoLice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(pravnoLice.getId().intValue()))
            .andExpect(jsonPath("$.imeOdgovornogLica").value(DEFAULT_IME_ODGOVORNOG_LICA.toString()))
            .andExpect(jsonPath("$.prezimeOdgovornogLica").value(DEFAULT_PREZIME_ODGOVORNOG_LICA.toString()))
            .andExpect(jsonPath("$.jmbg").value(DEFAULT_JMBG.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPravnoLice() throws Exception {
        // Get the pravnoLice
        restPravnoLiceMockMvc.perform(get("/api/pravno-lice/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePravnoLice() throws Exception {
        // Initialize the database
        pravnoLiceRepository.saveAndFlush(pravnoLice);
        int databaseSizeBeforeUpdate = pravnoLiceRepository.findAll().size();

        // Update the pravnoLice
        PravnoLice updatedPravnoLice = new PravnoLice();
        updatedPravnoLice.setId(pravnoLice.getId());
        updatedPravnoLice.setImeOdgovornogLica(UPDATED_IME_ODGOVORNOG_LICA);
        updatedPravnoLice.setPrezimeOdgovornogLica(UPDATED_PREZIME_ODGOVORNOG_LICA);
        updatedPravnoLice.setJmbg(UPDATED_JMBG);

        restPravnoLiceMockMvc.perform(put("/api/pravno-lice")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedPravnoLice)))
                .andExpect(status().isOk());

        // Validate the PravnoLice in the database
        List<PravnoLice> pravnoLice = pravnoLiceRepository.findAll();
        assertThat(pravnoLice).hasSize(databaseSizeBeforeUpdate);
        PravnoLice testPravnoLice = pravnoLice.get(pravnoLice.size() - 1);
        assertThat(testPravnoLice.getImeOdgovornogLica()).isEqualTo(UPDATED_IME_ODGOVORNOG_LICA);
        assertThat(testPravnoLice.getPrezimeOdgovornogLica()).isEqualTo(UPDATED_PREZIME_ODGOVORNOG_LICA);
        assertThat(testPravnoLice.getJmbg()).isEqualTo(UPDATED_JMBG);
    }

    @Test
    @Transactional
    public void deletePravnoLice() throws Exception {
        // Initialize the database
        pravnoLiceRepository.saveAndFlush(pravnoLice);
        int databaseSizeBeforeDelete = pravnoLiceRepository.findAll().size();

        // Get the pravnoLice
        restPravnoLiceMockMvc.perform(delete("/api/pravno-lice/{id}", pravnoLice.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PravnoLice> pravnoLice = pravnoLiceRepository.findAll();
        assertThat(pravnoLice).hasSize(databaseSizeBeforeDelete - 1);
    }
}
