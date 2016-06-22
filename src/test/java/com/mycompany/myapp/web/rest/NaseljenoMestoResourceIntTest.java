package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.PinfProApp;
import com.mycompany.myapp.domain.NaseljenoMesto;
import com.mycompany.myapp.repository.NaseljenoMestoRepository;

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
 * Test class for the NaseljenoMestoResource REST controller.
 *
 * @see NaseljenoMestoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PinfProApp.class)
@WebAppConfiguration
@IntegrationTest
public class NaseljenoMestoResourceIntTest {

    private static final String DEFAULT_NM_NAZIV = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_NM_NAZIV = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_NM_PTTOZNAKA = "AAAAAAAAAAAA";
    private static final String UPDATED_NM_PTTOZNAKA = "BBBBBBBBBBBB";

    @Inject
    private NaseljenoMestoRepository naseljenoMestoRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restNaseljenoMestoMockMvc;

    private NaseljenoMesto naseljenoMesto;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        NaseljenoMestoResource naseljenoMestoResource = new NaseljenoMestoResource();
        ReflectionTestUtils.setField(naseljenoMestoResource, "naseljenoMestoRepository", naseljenoMestoRepository);
        this.restNaseljenoMestoMockMvc = MockMvcBuilders.standaloneSetup(naseljenoMestoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        naseljenoMesto = new NaseljenoMesto();
        naseljenoMesto.setNm_naziv(DEFAULT_NM_NAZIV);
        naseljenoMesto.setNm_pttoznaka(DEFAULT_NM_PTTOZNAKA);
    }

    @Test
    @Transactional
    public void createNaseljenoMesto() throws Exception {
        int databaseSizeBeforeCreate = naseljenoMestoRepository.findAll().size();

        // Create the NaseljenoMesto

        restNaseljenoMestoMockMvc.perform(post("/api/naseljeno-mestos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(naseljenoMesto)))
                .andExpect(status().isCreated());

        // Validate the NaseljenoMesto in the database
        List<NaseljenoMesto> naseljenoMestos = naseljenoMestoRepository.findAll();
        assertThat(naseljenoMestos).hasSize(databaseSizeBeforeCreate + 1);
        NaseljenoMesto testNaseljenoMesto = naseljenoMestos.get(naseljenoMestos.size() - 1);
        assertThat(testNaseljenoMesto.getNm_naziv()).isEqualTo(DEFAULT_NM_NAZIV);
        assertThat(testNaseljenoMesto.getNm_pttoznaka()).isEqualTo(DEFAULT_NM_PTTOZNAKA);
    }

    @Test
    @Transactional
    public void checkNm_nazivIsRequired() throws Exception {
        int databaseSizeBeforeTest = naseljenoMestoRepository.findAll().size();
        // set the field null
        naseljenoMesto.setNm_naziv(null);

        // Create the NaseljenoMesto, which fails.

        restNaseljenoMestoMockMvc.perform(post("/api/naseljeno-mestos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(naseljenoMesto)))
                .andExpect(status().isBadRequest());

        List<NaseljenoMesto> naseljenoMestos = naseljenoMestoRepository.findAll();
        assertThat(naseljenoMestos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNm_pttoznakaIsRequired() throws Exception {
        int databaseSizeBeforeTest = naseljenoMestoRepository.findAll().size();
        // set the field null
        naseljenoMesto.setNm_pttoznaka(null);

        // Create the NaseljenoMesto, which fails.

        restNaseljenoMestoMockMvc.perform(post("/api/naseljeno-mestos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(naseljenoMesto)))
                .andExpect(status().isBadRequest());

        List<NaseljenoMesto> naseljenoMestos = naseljenoMestoRepository.findAll();
        assertThat(naseljenoMestos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNaseljenoMestos() throws Exception {
        // Initialize the database
        naseljenoMestoRepository.saveAndFlush(naseljenoMesto);

        // Get all the naseljenoMestos
        restNaseljenoMestoMockMvc.perform(get("/api/naseljeno-mestos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(naseljenoMesto.getId().intValue())))
                .andExpect(jsonPath("$.[*].nm_naziv").value(hasItem(DEFAULT_NM_NAZIV.toString())))
                .andExpect(jsonPath("$.[*].nm_pttoznaka").value(hasItem(DEFAULT_NM_PTTOZNAKA.toString())));
    }

    @Test
    @Transactional
    public void getNaseljenoMesto() throws Exception {
        // Initialize the database
        naseljenoMestoRepository.saveAndFlush(naseljenoMesto);

        // Get the naseljenoMesto
        restNaseljenoMestoMockMvc.perform(get("/api/naseljeno-mestos/{id}", naseljenoMesto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(naseljenoMesto.getId().intValue()))
            .andExpect(jsonPath("$.nm_naziv").value(DEFAULT_NM_NAZIV.toString()))
            .andExpect(jsonPath("$.nm_pttoznaka").value(DEFAULT_NM_PTTOZNAKA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNaseljenoMesto() throws Exception {
        // Get the naseljenoMesto
        restNaseljenoMestoMockMvc.perform(get("/api/naseljeno-mestos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNaseljenoMesto() throws Exception {
        // Initialize the database
        naseljenoMestoRepository.saveAndFlush(naseljenoMesto);
        int databaseSizeBeforeUpdate = naseljenoMestoRepository.findAll().size();

        // Update the naseljenoMesto
        NaseljenoMesto updatedNaseljenoMesto = new NaseljenoMesto();
        updatedNaseljenoMesto.setId(naseljenoMesto.getId());
        updatedNaseljenoMesto.setNm_naziv(UPDATED_NM_NAZIV);
        updatedNaseljenoMesto.setNm_pttoznaka(UPDATED_NM_PTTOZNAKA);

        restNaseljenoMestoMockMvc.perform(put("/api/naseljeno-mestos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedNaseljenoMesto)))
                .andExpect(status().isOk());

        // Validate the NaseljenoMesto in the database
        List<NaseljenoMesto> naseljenoMestos = naseljenoMestoRepository.findAll();
        assertThat(naseljenoMestos).hasSize(databaseSizeBeforeUpdate);
        NaseljenoMesto testNaseljenoMesto = naseljenoMestos.get(naseljenoMestos.size() - 1);
        assertThat(testNaseljenoMesto.getNm_naziv()).isEqualTo(UPDATED_NM_NAZIV);
        assertThat(testNaseljenoMesto.getNm_pttoznaka()).isEqualTo(UPDATED_NM_PTTOZNAKA);
    }

    @Test
    @Transactional
    public void deleteNaseljenoMesto() throws Exception {
        // Initialize the database
        naseljenoMestoRepository.saveAndFlush(naseljenoMesto);
        int databaseSizeBeforeDelete = naseljenoMestoRepository.findAll().size();

        // Get the naseljenoMesto
        restNaseljenoMestoMockMvc.perform(delete("/api/naseljeno-mestos/{id}", naseljenoMesto.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<NaseljenoMesto> naseljenoMestos = naseljenoMestoRepository.findAll();
        assertThat(naseljenoMestos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
