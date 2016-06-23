package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.PinfProApp;
import com.mycompany.myapp.domain.VrstaPlacanja;
import com.mycompany.myapp.repository.VrstaPlacanjaRepository;

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
 * Test class for the VrstaPlacanjaResource REST controller.
 *
 * @see VrstaPlacanjaResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PinfProApp.class)
@WebAppConfiguration
@IntegrationTest
public class VrstaPlacanjaResourceIntTest {


    private static final Integer DEFAULT_OZNAKA_VRSTE_PLACANJA = 1;
    private static final Integer UPDATED_OZNAKA_VRSTE_PLACANJA = 2;
    private static final String DEFAULT_NAZIV_VRSTE_PLACANJA = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_NAZIV_VRSTE_PLACANJA = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    @Inject
    private VrstaPlacanjaRepository vrstaPlacanjaRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restVrstaPlacanjaMockMvc;

    private VrstaPlacanja vrstaPlacanja;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        VrstaPlacanjaResource vrstaPlacanjaResource = new VrstaPlacanjaResource();
        ReflectionTestUtils.setField(vrstaPlacanjaResource, "vrstaPlacanjaRepository", vrstaPlacanjaRepository);
        this.restVrstaPlacanjaMockMvc = MockMvcBuilders.standaloneSetup(vrstaPlacanjaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        vrstaPlacanja = new VrstaPlacanja();
        vrstaPlacanja.setOznakaVrstePlacanja(DEFAULT_OZNAKA_VRSTE_PLACANJA);
        vrstaPlacanja.setNazivVrstePlacanja(DEFAULT_NAZIV_VRSTE_PLACANJA);
    }

    @Test
    @Transactional
    public void createVrstaPlacanja() throws Exception {
        int databaseSizeBeforeCreate = vrstaPlacanjaRepository.findAll().size();

        // Create the VrstaPlacanja

        restVrstaPlacanjaMockMvc.perform(post("/api/vrsta-placanjas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(vrstaPlacanja)))
                .andExpect(status().isCreated());

        // Validate the VrstaPlacanja in the database
        List<VrstaPlacanja> vrstaPlacanjas = vrstaPlacanjaRepository.findAll();
        assertThat(vrstaPlacanjas).hasSize(databaseSizeBeforeCreate + 1);
        VrstaPlacanja testVrstaPlacanja = vrstaPlacanjas.get(vrstaPlacanjas.size() - 1);
        assertThat(testVrstaPlacanja.getOznakaVrstePlacanja()).isEqualTo(DEFAULT_OZNAKA_VRSTE_PLACANJA);
        assertThat(testVrstaPlacanja.getNazivVrstePlacanja()).isEqualTo(DEFAULT_NAZIV_VRSTE_PLACANJA);
    }

    @Test
    @Transactional
    public void checkOznakaVrstePlacanjaIsRequired() throws Exception {
        int databaseSizeBeforeTest = vrstaPlacanjaRepository.findAll().size();
        // set the field null
        vrstaPlacanja.setOznakaVrstePlacanja(null);

        // Create the VrstaPlacanja, which fails.

        restVrstaPlacanjaMockMvc.perform(post("/api/vrsta-placanjas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(vrstaPlacanja)))
                .andExpect(status().isBadRequest());

        List<VrstaPlacanja> vrstaPlacanjas = vrstaPlacanjaRepository.findAll();
        assertThat(vrstaPlacanjas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNazivVrstePlacanjaIsRequired() throws Exception {
        int databaseSizeBeforeTest = vrstaPlacanjaRepository.findAll().size();
        // set the field null
        vrstaPlacanja.setNazivVrstePlacanja(null);

        // Create the VrstaPlacanja, which fails.

        restVrstaPlacanjaMockMvc.perform(post("/api/vrsta-placanjas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(vrstaPlacanja)))
                .andExpect(status().isBadRequest());

        List<VrstaPlacanja> vrstaPlacanjas = vrstaPlacanjaRepository.findAll();
        assertThat(vrstaPlacanjas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVrstaPlacanjas() throws Exception {
        // Initialize the database
        vrstaPlacanjaRepository.saveAndFlush(vrstaPlacanja);

        // Get all the vrstaPlacanjas
        restVrstaPlacanjaMockMvc.perform(get("/api/vrsta-placanjas?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(vrstaPlacanja.getId().intValue())))
                .andExpect(jsonPath("$.[*].oznakaVrstePlacanja").value(hasItem(DEFAULT_OZNAKA_VRSTE_PLACANJA)))
                .andExpect(jsonPath("$.[*].nazivVrstePlacanja").value(hasItem(DEFAULT_NAZIV_VRSTE_PLACANJA.toString())));
    }

    @Test
    @Transactional
    public void getVrstaPlacanja() throws Exception {
        // Initialize the database
        vrstaPlacanjaRepository.saveAndFlush(vrstaPlacanja);

        // Get the vrstaPlacanja
        restVrstaPlacanjaMockMvc.perform(get("/api/vrsta-placanjas/{id}", vrstaPlacanja.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(vrstaPlacanja.getId().intValue()))
            .andExpect(jsonPath("$.oznakaVrstePlacanja").value(DEFAULT_OZNAKA_VRSTE_PLACANJA))
            .andExpect(jsonPath("$.nazivVrstePlacanja").value(DEFAULT_NAZIV_VRSTE_PLACANJA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVrstaPlacanja() throws Exception {
        // Get the vrstaPlacanja
        restVrstaPlacanjaMockMvc.perform(get("/api/vrsta-placanjas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVrstaPlacanja() throws Exception {
        // Initialize the database
        vrstaPlacanjaRepository.saveAndFlush(vrstaPlacanja);
        int databaseSizeBeforeUpdate = vrstaPlacanjaRepository.findAll().size();

        // Update the vrstaPlacanja
        VrstaPlacanja updatedVrstaPlacanja = new VrstaPlacanja();
        updatedVrstaPlacanja.setId(vrstaPlacanja.getId());
        updatedVrstaPlacanja.setOznakaVrstePlacanja(UPDATED_OZNAKA_VRSTE_PLACANJA);
        updatedVrstaPlacanja.setNazivVrstePlacanja(UPDATED_NAZIV_VRSTE_PLACANJA);

        restVrstaPlacanjaMockMvc.perform(put("/api/vrsta-placanjas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedVrstaPlacanja)))
                .andExpect(status().isOk());

        // Validate the VrstaPlacanja in the database
        List<VrstaPlacanja> vrstaPlacanjas = vrstaPlacanjaRepository.findAll();
        assertThat(vrstaPlacanjas).hasSize(databaseSizeBeforeUpdate);
        VrstaPlacanja testVrstaPlacanja = vrstaPlacanjas.get(vrstaPlacanjas.size() - 1);
        assertThat(testVrstaPlacanja.getOznakaVrstePlacanja()).isEqualTo(UPDATED_OZNAKA_VRSTE_PLACANJA);
        assertThat(testVrstaPlacanja.getNazivVrstePlacanja()).isEqualTo(UPDATED_NAZIV_VRSTE_PLACANJA);
    }

    @Test
    @Transactional
    public void deleteVrstaPlacanja() throws Exception {
        // Initialize the database
        vrstaPlacanjaRepository.saveAndFlush(vrstaPlacanja);
        int databaseSizeBeforeDelete = vrstaPlacanjaRepository.findAll().size();

        // Get the vrstaPlacanja
        restVrstaPlacanjaMockMvc.perform(delete("/api/vrsta-placanjas/{id}", vrstaPlacanja.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<VrstaPlacanja> vrstaPlacanjas = vrstaPlacanjaRepository.findAll();
        assertThat(vrstaPlacanjas).hasSize(databaseSizeBeforeDelete - 1);
    }
}
