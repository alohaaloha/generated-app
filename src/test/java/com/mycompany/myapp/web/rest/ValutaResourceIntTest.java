package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.PinfProApp;
import com.mycompany.myapp.domain.Valuta;
import com.mycompany.myapp.repository.ValutaRepository;

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
 * Test class for the ValutaResource REST controller.
 *
 * @see ValutaResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PinfProApp.class)
@WebAppConfiguration
@IntegrationTest
public class ValutaResourceIntTest {

    private static final String DEFAULT_ZVANICNA_SIFRA = "AAA";
    private static final String UPDATED_ZVANICNA_SIFRA = "BBB";
    private static final String DEFAULT_NAZIV_VALUTE = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_NAZIV_VALUTE = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    private static final Integer DEFAULT_DOMICILNA = 1;
    private static final Integer UPDATED_DOMICILNA = 2;

    @Inject
    private ValutaRepository valutaRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restValutaMockMvc;

    private Valuta valuta;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ValutaResource valutaResource = new ValutaResource();
        ReflectionTestUtils.setField(valutaResource, "valutaRepository", valutaRepository);
        this.restValutaMockMvc = MockMvcBuilders.standaloneSetup(valutaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        valuta = new Valuta();
        valuta.setZvanicnaSifra(DEFAULT_ZVANICNA_SIFRA);
        valuta.setNazivValute(DEFAULT_NAZIV_VALUTE);
        valuta.setDomicilna(DEFAULT_DOMICILNA);
    }

    @Test
    @Transactional
    public void createValuta() throws Exception {
        int databaseSizeBeforeCreate = valutaRepository.findAll().size();

        // Create the Valuta

        restValutaMockMvc.perform(post("/api/valutas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(valuta)))
                .andExpect(status().isCreated());

        // Validate the Valuta in the database
        List<Valuta> valutas = valutaRepository.findAll();
        assertThat(valutas).hasSize(databaseSizeBeforeCreate + 1);
        Valuta testValuta = valutas.get(valutas.size() - 1);
        assertThat(testValuta.getZvanicnaSifra()).isEqualTo(DEFAULT_ZVANICNA_SIFRA);
        assertThat(testValuta.getNazivValute()).isEqualTo(DEFAULT_NAZIV_VALUTE);
        assertThat(testValuta.getDomicilna()).isEqualTo(DEFAULT_DOMICILNA);
    }

    @Test
    @Transactional
    public void checkZvanicnaSifraIsRequired() throws Exception {
        int databaseSizeBeforeTest = valutaRepository.findAll().size();
        // set the field null
        valuta.setZvanicnaSifra(null);

        // Create the Valuta, which fails.

        restValutaMockMvc.perform(post("/api/valutas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(valuta)))
                .andExpect(status().isBadRequest());

        List<Valuta> valutas = valutaRepository.findAll();
        assertThat(valutas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNazivValuteIsRequired() throws Exception {
        int databaseSizeBeforeTest = valutaRepository.findAll().size();
        // set the field null
        valuta.setNazivValute(null);

        // Create the Valuta, which fails.

        restValutaMockMvc.perform(post("/api/valutas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(valuta)))
                .andExpect(status().isBadRequest());

        List<Valuta> valutas = valutaRepository.findAll();
        assertThat(valutas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDomicilnaIsRequired() throws Exception {
        int databaseSizeBeforeTest = valutaRepository.findAll().size();
        // set the field null
        valuta.setDomicilna(null);

        // Create the Valuta, which fails.

        restValutaMockMvc.perform(post("/api/valutas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(valuta)))
                .andExpect(status().isBadRequest());

        List<Valuta> valutas = valutaRepository.findAll();
        assertThat(valutas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllValutas() throws Exception {
        // Initialize the database
        valutaRepository.saveAndFlush(valuta);

        // Get all the valutas
        restValutaMockMvc.perform(get("/api/valutas?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(valuta.getId().intValue())))
                .andExpect(jsonPath("$.[*].zvanicnaSifra").value(hasItem(DEFAULT_ZVANICNA_SIFRA.toString())))
                .andExpect(jsonPath("$.[*].nazivValute").value(hasItem(DEFAULT_NAZIV_VALUTE.toString())))
                .andExpect(jsonPath("$.[*].domicilna").value(hasItem(DEFAULT_DOMICILNA)));
    }

    @Test
    @Transactional
    public void getValuta() throws Exception {
        // Initialize the database
        valutaRepository.saveAndFlush(valuta);

        // Get the valuta
        restValutaMockMvc.perform(get("/api/valutas/{id}", valuta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(valuta.getId().intValue()))
            .andExpect(jsonPath("$.zvanicnaSifra").value(DEFAULT_ZVANICNA_SIFRA.toString()))
            .andExpect(jsonPath("$.nazivValute").value(DEFAULT_NAZIV_VALUTE.toString()))
            .andExpect(jsonPath("$.domicilna").value(DEFAULT_DOMICILNA));
    }

    @Test
    @Transactional
    public void getNonExistingValuta() throws Exception {
        // Get the valuta
        restValutaMockMvc.perform(get("/api/valutas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateValuta() throws Exception {
        // Initialize the database
        valutaRepository.saveAndFlush(valuta);
        int databaseSizeBeforeUpdate = valutaRepository.findAll().size();

        // Update the valuta
        Valuta updatedValuta = new Valuta();
        updatedValuta.setId(valuta.getId());
        updatedValuta.setZvanicnaSifra(UPDATED_ZVANICNA_SIFRA);
        updatedValuta.setNazivValute(UPDATED_NAZIV_VALUTE);
        updatedValuta.setDomicilna(UPDATED_DOMICILNA);

        restValutaMockMvc.perform(put("/api/valutas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedValuta)))
                .andExpect(status().isOk());

        // Validate the Valuta in the database
        List<Valuta> valutas = valutaRepository.findAll();
        assertThat(valutas).hasSize(databaseSizeBeforeUpdate);
        Valuta testValuta = valutas.get(valutas.size() - 1);
        assertThat(testValuta.getZvanicnaSifra()).isEqualTo(UPDATED_ZVANICNA_SIFRA);
        assertThat(testValuta.getNazivValute()).isEqualTo(UPDATED_NAZIV_VALUTE);
        assertThat(testValuta.getDomicilna()).isEqualTo(UPDATED_DOMICILNA);
    }

    @Test
    @Transactional
    public void deleteValuta() throws Exception {
        // Initialize the database
        valutaRepository.saveAndFlush(valuta);
        int databaseSizeBeforeDelete = valutaRepository.findAll().size();

        // Get the valuta
        restValutaMockMvc.perform(delete("/api/valutas/{id}", valuta.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Valuta> valutas = valutaRepository.findAll();
        assertThat(valutas).hasSize(databaseSizeBeforeDelete - 1);
    }
}
