package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.PinfProApp;
import com.mycompany.myapp.domain.Drzava;
import com.mycompany.myapp.repository.DrzavaRepository;

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
 * Test class for the DrzavaResource REST controller.
 *
 * @see DrzavaResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PinfProApp.class)
@WebAppConfiguration
@IntegrationTest
public class DrzavaResourceIntTest {

    private static final String DEFAULT_DR_NAZIV = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_DR_NAZIV = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    @Inject
    private DrzavaRepository drzavaRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDrzavaMockMvc;

    private Drzava drzava;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DrzavaResource drzavaResource = new DrzavaResource();
        ReflectionTestUtils.setField(drzavaResource, "drzavaRepository", drzavaRepository);
        this.restDrzavaMockMvc = MockMvcBuilders.standaloneSetup(drzavaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        drzava = new Drzava();
        drzava.setDr_naziv(DEFAULT_DR_NAZIV);
    }

    @Test
    @Transactional
    public void createDrzava() throws Exception {
        int databaseSizeBeforeCreate = drzavaRepository.findAll().size();

        // Create the Drzava

        restDrzavaMockMvc.perform(post("/api/drzavas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(drzava)))
                .andExpect(status().isCreated());

        // Validate the Drzava in the database
        List<Drzava> drzavas = drzavaRepository.findAll();
        assertThat(drzavas).hasSize(databaseSizeBeforeCreate + 1);
        Drzava testDrzava = drzavas.get(drzavas.size() - 1);
        assertThat(testDrzava.getDr_naziv()).isEqualTo(DEFAULT_DR_NAZIV);
    }

    @Test
    @Transactional
    public void checkDr_nazivIsRequired() throws Exception {
        int databaseSizeBeforeTest = drzavaRepository.findAll().size();
        // set the field null
        drzava.setDr_naziv(null);

        // Create the Drzava, which fails.

        restDrzavaMockMvc.perform(post("/api/drzavas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(drzava)))
                .andExpect(status().isBadRequest());

        List<Drzava> drzavas = drzavaRepository.findAll();
        assertThat(drzavas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDrzavas() throws Exception {
        // Initialize the database
        drzavaRepository.saveAndFlush(drzava);

        // Get all the drzavas
        restDrzavaMockMvc.perform(get("/api/drzavas?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(drzava.getId().intValue())))
                .andExpect(jsonPath("$.[*].dr_naziv").value(hasItem(DEFAULT_DR_NAZIV.toString())));
    }

    @Test
    @Transactional
    public void getDrzava() throws Exception {
        // Initialize the database
        drzavaRepository.saveAndFlush(drzava);

        // Get the drzava
        restDrzavaMockMvc.perform(get("/api/drzavas/{id}", drzava.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(drzava.getId().intValue()))
            .andExpect(jsonPath("$.dr_naziv").value(DEFAULT_DR_NAZIV.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDrzava() throws Exception {
        // Get the drzava
        restDrzavaMockMvc.perform(get("/api/drzavas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDrzava() throws Exception {
        // Initialize the database
        drzavaRepository.saveAndFlush(drzava);
        int databaseSizeBeforeUpdate = drzavaRepository.findAll().size();

        // Update the drzava
        Drzava updatedDrzava = new Drzava();
        updatedDrzava.setId(drzava.getId());
        updatedDrzava.setDr_naziv(UPDATED_DR_NAZIV);

        restDrzavaMockMvc.perform(put("/api/drzavas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedDrzava)))
                .andExpect(status().isOk());

        // Validate the Drzava in the database
        List<Drzava> drzavas = drzavaRepository.findAll();
        assertThat(drzavas).hasSize(databaseSizeBeforeUpdate);
        Drzava testDrzava = drzavas.get(drzavas.size() - 1);
        assertThat(testDrzava.getDr_naziv()).isEqualTo(UPDATED_DR_NAZIV);
    }

    @Test
    @Transactional
    public void deleteDrzava() throws Exception {
        // Initialize the database
        drzavaRepository.saveAndFlush(drzava);
        int databaseSizeBeforeDelete = drzavaRepository.findAll().size();

        // Get the drzava
        restDrzavaMockMvc.perform(delete("/api/drzavas/{id}", drzava.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Drzava> drzavas = drzavaRepository.findAll();
        assertThat(drzavas).hasSize(databaseSizeBeforeDelete - 1);
    }
}
