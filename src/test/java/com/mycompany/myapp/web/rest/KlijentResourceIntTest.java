package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.PinfProApp;
import com.mycompany.myapp.domain.Klijent;
import com.mycompany.myapp.repository.KlijentRepository;

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
 * Test class for the KlijentResource REST controller.
 *
 * @see KlijentResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PinfProApp.class)
@WebAppConfiguration
@IntegrationTest
public class KlijentResourceIntTest {

    private static final String DEFAULT_NAZIV_PRAVNOG_LICA = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_NAZIV_PRAVNOG_LICA = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_IME = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_IME = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_PREZIME = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_PREZIME = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_JMBG = "AAAAAAAAAAAAA";
    private static final String UPDATED_JMBG = "BBBBBBBBBBBBB";
    private static final String DEFAULT_ADRESA = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_ADRESA = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_TELEFON = "AAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_TELEFON = "BBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_FAX = "AAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_FAX = "BBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_EMAIL = "AAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_PIB = "AAAAAAAAA";
    private static final String UPDATED_PIB = "BBBBBBBBB";

    private static final Integer DEFAULT_SIFRA_DELATNOSTI = 1;
    private static final Integer UPDATED_SIFRA_DELATNOSTI = 2;

    @Inject
    private KlijentRepository klijentRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restKlijentMockMvc;

    private Klijent klijent;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        KlijentResource klijentResource = new KlijentResource();
        ReflectionTestUtils.setField(klijentResource, "klijentRepository", klijentRepository);
        this.restKlijentMockMvc = MockMvcBuilders.standaloneSetup(klijentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        klijent = new Klijent();
        klijent.setNazivPravnogLica(DEFAULT_NAZIV_PRAVNOG_LICA);
        klijent.setIme(DEFAULT_IME);
        klijent.setPrezime(DEFAULT_PREZIME);
        klijent.setJmbg(DEFAULT_JMBG);
        klijent.setAdresa(DEFAULT_ADRESA);
        klijent.setTelefon(DEFAULT_TELEFON);
        klijent.setFax(DEFAULT_FAX);
        klijent.setEmail(DEFAULT_EMAIL);
        klijent.setPib(DEFAULT_PIB);
        klijent.setSifraDelatnosti(DEFAULT_SIFRA_DELATNOSTI);
    }

    @Test
    @Transactional
    public void createKlijent() throws Exception {
        int databaseSizeBeforeCreate = klijentRepository.findAll().size();

        // Create the Klijent

        restKlijentMockMvc.perform(post("/api/klijents")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(klijent)))
                .andExpect(status().isCreated());

        // Validate the Klijent in the database
        List<Klijent> klijents = klijentRepository.findAll();
        assertThat(klijents).hasSize(databaseSizeBeforeCreate + 1);
        Klijent testKlijent = klijents.get(klijents.size() - 1);
        assertThat(testKlijent.getNazivPravnogLica()).isEqualTo(DEFAULT_NAZIV_PRAVNOG_LICA);
        assertThat(testKlijent.getIme()).isEqualTo(DEFAULT_IME);
        assertThat(testKlijent.getPrezime()).isEqualTo(DEFAULT_PREZIME);
        assertThat(testKlijent.getJmbg()).isEqualTo(DEFAULT_JMBG);
        assertThat(testKlijent.getAdresa()).isEqualTo(DEFAULT_ADRESA);
        assertThat(testKlijent.getTelefon()).isEqualTo(DEFAULT_TELEFON);
        assertThat(testKlijent.getFax()).isEqualTo(DEFAULT_FAX);
        assertThat(testKlijent.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testKlijent.getPib()).isEqualTo(DEFAULT_PIB);
        assertThat(testKlijent.getSifraDelatnosti()).isEqualTo(DEFAULT_SIFRA_DELATNOSTI);
    }

    @Test
    @Transactional
    public void checkImeIsRequired() throws Exception {
        int databaseSizeBeforeTest = klijentRepository.findAll().size();
        // set the field null
        klijent.setIme(null);

        // Create the Klijent, which fails.

        restKlijentMockMvc.perform(post("/api/klijents")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(klijent)))
                .andExpect(status().isBadRequest());

        List<Klijent> klijents = klijentRepository.findAll();
        assertThat(klijents).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrezimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = klijentRepository.findAll().size();
        // set the field null
        klijent.setPrezime(null);

        // Create the Klijent, which fails.

        restKlijentMockMvc.perform(post("/api/klijents")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(klijent)))
                .andExpect(status().isBadRequest());

        List<Klijent> klijents = klijentRepository.findAll();
        assertThat(klijents).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkJmbgIsRequired() throws Exception {
        int databaseSizeBeforeTest = klijentRepository.findAll().size();
        // set the field null
        klijent.setJmbg(null);

        // Create the Klijent, which fails.

        restKlijentMockMvc.perform(post("/api/klijents")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(klijent)))
                .andExpect(status().isBadRequest());

        List<Klijent> klijents = klijentRepository.findAll();
        assertThat(klijents).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAdresaIsRequired() throws Exception {
        int databaseSizeBeforeTest = klijentRepository.findAll().size();
        // set the field null
        klijent.setAdresa(null);

        // Create the Klijent, which fails.

        restKlijentMockMvc.perform(post("/api/klijents")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(klijent)))
                .andExpect(status().isBadRequest());

        List<Klijent> klijents = klijentRepository.findAll();
        assertThat(klijents).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPibIsRequired() throws Exception {
        int databaseSizeBeforeTest = klijentRepository.findAll().size();
        // set the field null
        klijent.setPib(null);

        // Create the Klijent, which fails.

        restKlijentMockMvc.perform(post("/api/klijents")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(klijent)))
                .andExpect(status().isBadRequest());

        List<Klijent> klijents = klijentRepository.findAll();
        assertThat(klijents).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllKlijents() throws Exception {
        // Initialize the database
        klijentRepository.saveAndFlush(klijent);

        // Get all the klijents
        restKlijentMockMvc.perform(get("/api/klijents?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(klijent.getId().intValue())))
                .andExpect(jsonPath("$.[*].nazivPravnogLica").value(hasItem(DEFAULT_NAZIV_PRAVNOG_LICA.toString())))
                .andExpect(jsonPath("$.[*].ime").value(hasItem(DEFAULT_IME.toString())))
                .andExpect(jsonPath("$.[*].prezime").value(hasItem(DEFAULT_PREZIME.toString())))
                .andExpect(jsonPath("$.[*].jmbg").value(hasItem(DEFAULT_JMBG.toString())))
                .andExpect(jsonPath("$.[*].adresa").value(hasItem(DEFAULT_ADRESA.toString())))
                .andExpect(jsonPath("$.[*].telefon").value(hasItem(DEFAULT_TELEFON.toString())))
                .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].pib").value(hasItem(DEFAULT_PIB.toString())))
                .andExpect(jsonPath("$.[*].sifraDelatnosti").value(hasItem(DEFAULT_SIFRA_DELATNOSTI)));
    }

    @Test
    @Transactional
    public void getKlijent() throws Exception {
        // Initialize the database
        klijentRepository.saveAndFlush(klijent);

        // Get the klijent
        restKlijentMockMvc.perform(get("/api/klijents/{id}", klijent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(klijent.getId().intValue()))
            .andExpect(jsonPath("$.nazivPravnogLica").value(DEFAULT_NAZIV_PRAVNOG_LICA.toString()))
            .andExpect(jsonPath("$.ime").value(DEFAULT_IME.toString()))
            .andExpect(jsonPath("$.prezime").value(DEFAULT_PREZIME.toString()))
            .andExpect(jsonPath("$.jmbg").value(DEFAULT_JMBG.toString()))
            .andExpect(jsonPath("$.adresa").value(DEFAULT_ADRESA.toString()))
            .andExpect(jsonPath("$.telefon").value(DEFAULT_TELEFON.toString()))
            .andExpect(jsonPath("$.fax").value(DEFAULT_FAX.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.pib").value(DEFAULT_PIB.toString()))
            .andExpect(jsonPath("$.sifraDelatnosti").value(DEFAULT_SIFRA_DELATNOSTI));
    }

    @Test
    @Transactional
    public void getNonExistingKlijent() throws Exception {
        // Get the klijent
        restKlijentMockMvc.perform(get("/api/klijents/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKlijent() throws Exception {
        // Initialize the database
        klijentRepository.saveAndFlush(klijent);
        int databaseSizeBeforeUpdate = klijentRepository.findAll().size();

        // Update the klijent
        Klijent updatedKlijent = new Klijent();
        updatedKlijent.setId(klijent.getId());
        updatedKlijent.setNazivPravnogLica(UPDATED_NAZIV_PRAVNOG_LICA);
        updatedKlijent.setIme(UPDATED_IME);
        updatedKlijent.setPrezime(UPDATED_PREZIME);
        updatedKlijent.setJmbg(UPDATED_JMBG);
        updatedKlijent.setAdresa(UPDATED_ADRESA);
        updatedKlijent.setTelefon(UPDATED_TELEFON);
        updatedKlijent.setFax(UPDATED_FAX);
        updatedKlijent.setEmail(UPDATED_EMAIL);
        updatedKlijent.setPib(UPDATED_PIB);
        updatedKlijent.setSifraDelatnosti(UPDATED_SIFRA_DELATNOSTI);

        restKlijentMockMvc.perform(put("/api/klijents")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedKlijent)))
                .andExpect(status().isOk());

        // Validate the Klijent in the database
        List<Klijent> klijents = klijentRepository.findAll();
        assertThat(klijents).hasSize(databaseSizeBeforeUpdate);
        Klijent testKlijent = klijents.get(klijents.size() - 1);
        assertThat(testKlijent.getNazivPravnogLica()).isEqualTo(UPDATED_NAZIV_PRAVNOG_LICA);
        assertThat(testKlijent.getIme()).isEqualTo(UPDATED_IME);
        assertThat(testKlijent.getPrezime()).isEqualTo(UPDATED_PREZIME);
        assertThat(testKlijent.getJmbg()).isEqualTo(UPDATED_JMBG);
        assertThat(testKlijent.getAdresa()).isEqualTo(UPDATED_ADRESA);
        assertThat(testKlijent.getTelefon()).isEqualTo(UPDATED_TELEFON);
        assertThat(testKlijent.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testKlijent.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testKlijent.getPib()).isEqualTo(UPDATED_PIB);
        assertThat(testKlijent.getSifraDelatnosti()).isEqualTo(UPDATED_SIFRA_DELATNOSTI);
    }

    @Test
    @Transactional
    public void deleteKlijent() throws Exception {
        // Initialize the database
        klijentRepository.saveAndFlush(klijent);
        int databaseSizeBeforeDelete = klijentRepository.findAll().size();

        // Get the klijent
        restKlijentMockMvc.perform(delete("/api/klijents/{id}", klijent.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Klijent> klijents = klijentRepository.findAll();
        assertThat(klijents).hasSize(databaseSizeBeforeDelete - 1);
    }
}
