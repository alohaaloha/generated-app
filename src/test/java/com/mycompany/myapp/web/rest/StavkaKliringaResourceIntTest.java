package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.PinfProApp;
import com.mycompany.myapp.domain.StavkaKliringa;
import com.mycompany.myapp.repository.StavkaKliringaRepository;

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
 * Test class for the StavkaKliringaResource REST controller.
 *
 * @see StavkaKliringaResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PinfProApp.class)
@WebAppConfiguration
@IntegrationTest
public class StavkaKliringaResourceIntTest {


    @Inject
    private StavkaKliringaRepository stavkaKliringaRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restStavkaKliringaMockMvc;

    private StavkaKliringa stavkaKliringa;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        StavkaKliringaResource stavkaKliringaResource = new StavkaKliringaResource();
        ReflectionTestUtils.setField(stavkaKliringaResource, "stavkaKliringaRepository", stavkaKliringaRepository);
        this.restStavkaKliringaMockMvc = MockMvcBuilders.standaloneSetup(stavkaKliringaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        stavkaKliringa = new StavkaKliringa();
    }

    @Test
    @Transactional
    public void createStavkaKliringa() throws Exception {
        int databaseSizeBeforeCreate = stavkaKliringaRepository.findAll().size();

        // Create the StavkaKliringa

        restStavkaKliringaMockMvc.perform(post("/api/stavka-kliringas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(stavkaKliringa)))
                .andExpect(status().isCreated());

        // Validate the StavkaKliringa in the database
        List<StavkaKliringa> stavkaKliringas = stavkaKliringaRepository.findAll();
        assertThat(stavkaKliringas).hasSize(databaseSizeBeforeCreate + 1);
        StavkaKliringa testStavkaKliringa = stavkaKliringas.get(stavkaKliringas.size() - 1);
    }

    @Test
    @Transactional
    public void getAllStavkaKliringas() throws Exception {
        // Initialize the database
        stavkaKliringaRepository.saveAndFlush(stavkaKliringa);

        // Get all the stavkaKliringas
        restStavkaKliringaMockMvc.perform(get("/api/stavka-kliringas?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(stavkaKliringa.getId().intValue())));
    }

    @Test
    @Transactional
    public void getStavkaKliringa() throws Exception {
        // Initialize the database
        stavkaKliringaRepository.saveAndFlush(stavkaKliringa);

        // Get the stavkaKliringa
        restStavkaKliringaMockMvc.perform(get("/api/stavka-kliringas/{id}", stavkaKliringa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(stavkaKliringa.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingStavkaKliringa() throws Exception {
        // Get the stavkaKliringa
        restStavkaKliringaMockMvc.perform(get("/api/stavka-kliringas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStavkaKliringa() throws Exception {
        // Initialize the database
        stavkaKliringaRepository.saveAndFlush(stavkaKliringa);
        int databaseSizeBeforeUpdate = stavkaKliringaRepository.findAll().size();

        // Update the stavkaKliringa
        StavkaKliringa updatedStavkaKliringa = new StavkaKliringa();
        updatedStavkaKliringa.setId(stavkaKliringa.getId());

        restStavkaKliringaMockMvc.perform(put("/api/stavka-kliringas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedStavkaKliringa)))
                .andExpect(status().isOk());

        // Validate the StavkaKliringa in the database
        List<StavkaKliringa> stavkaKliringas = stavkaKliringaRepository.findAll();
        assertThat(stavkaKliringas).hasSize(databaseSizeBeforeUpdate);
        StavkaKliringa testStavkaKliringa = stavkaKliringas.get(stavkaKliringas.size() - 1);
    }

    @Test
    @Transactional
    public void deleteStavkaKliringa() throws Exception {
        // Initialize the database
        stavkaKliringaRepository.saveAndFlush(stavkaKliringa);
        int databaseSizeBeforeDelete = stavkaKliringaRepository.findAll().size();

        // Get the stavkaKliringa
        restStavkaKliringaMockMvc.perform(delete("/api/stavka-kliringas/{id}", stavkaKliringa.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<StavkaKliringa> stavkaKliringas = stavkaKliringaRepository.findAll();
        assertThat(stavkaKliringas).hasSize(databaseSizeBeforeDelete - 1);
    }
}
