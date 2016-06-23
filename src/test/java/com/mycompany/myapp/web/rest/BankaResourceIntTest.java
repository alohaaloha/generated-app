package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.PinfProApp;
import com.mycompany.myapp.domain.Banka;
import com.mycompany.myapp.repository.BankaRepository;

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
 * Test class for the BankaResource REST controller.
 *
 * @see BankaResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PinfProApp.class)
@WebAppConfiguration
@IntegrationTest
public class BankaResourceIntTest {

    private static final String DEFAULT_SIFRA_BANKE = "AAA";
    private static final String UPDATED_SIFRA_BANKE = "BBB";
    private static final String DEFAULT_PIB = "AAAAAAAAAA";
    private static final String UPDATED_PIB = "BBBBBBBBBB";
    private static final String DEFAULT_NAZIV = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_NAZIV = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_ADRESA = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_ADRESA = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_EMAIL = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_WEB = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_WEB = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_TELEFON = "AAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_TELEFON = "BBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_FAX = "AAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_FAX = "BBBBBBBBBBBBBBBBBBBB";

    private static final Integer DEFAULT_BANKA_INT = 1;
    private static final Integer UPDATED_BANKA_INT = 2;

    @Inject
    private BankaRepository bankaRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restBankaMockMvc;

    private Banka banka;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BankaResource bankaResource = new BankaResource();
        ReflectionTestUtils.setField(bankaResource, "bankaRepository", bankaRepository);
        this.restBankaMockMvc = MockMvcBuilders.standaloneSetup(bankaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        banka = new Banka();
        banka.setSifraBanke(DEFAULT_SIFRA_BANKE);
        banka.setPib(DEFAULT_PIB);
        banka.setNaziv(DEFAULT_NAZIV);
        banka.setAdresa(DEFAULT_ADRESA);
        banka.setEmail(DEFAULT_EMAIL);
        banka.setWeb(DEFAULT_WEB);
        banka.setTelefon(DEFAULT_TELEFON);
        banka.setFax(DEFAULT_FAX);
        banka.setBankaInt(DEFAULT_BANKA_INT);
    }

    @Test
    @Transactional
    public void createBanka() throws Exception {
        int databaseSizeBeforeCreate = bankaRepository.findAll().size();

        // Create the Banka

        restBankaMockMvc.perform(post("/api/bankas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(banka)))
                .andExpect(status().isCreated());

        // Validate the Banka in the database
        List<Banka> bankas = bankaRepository.findAll();
        assertThat(bankas).hasSize(databaseSizeBeforeCreate + 1);
        Banka testBanka = bankas.get(bankas.size() - 1);
        assertThat(testBanka.getSifraBanke()).isEqualTo(DEFAULT_SIFRA_BANKE);
        assertThat(testBanka.getPib()).isEqualTo(DEFAULT_PIB);
        assertThat(testBanka.getNaziv()).isEqualTo(DEFAULT_NAZIV);
        assertThat(testBanka.getAdresa()).isEqualTo(DEFAULT_ADRESA);
        assertThat(testBanka.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testBanka.getWeb()).isEqualTo(DEFAULT_WEB);
        assertThat(testBanka.getTelefon()).isEqualTo(DEFAULT_TELEFON);
        assertThat(testBanka.getFax()).isEqualTo(DEFAULT_FAX);
        assertThat(testBanka.getBankaInt()).isEqualTo(DEFAULT_BANKA_INT);
    }

    @Test
    @Transactional
    public void checkSifraBankeIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankaRepository.findAll().size();
        // set the field null
        banka.setSifraBanke(null);

        // Create the Banka, which fails.

        restBankaMockMvc.perform(post("/api/bankas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(banka)))
                .andExpect(status().isBadRequest());

        List<Banka> bankas = bankaRepository.findAll();
        assertThat(bankas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPibIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankaRepository.findAll().size();
        // set the field null
        banka.setPib(null);

        // Create the Banka, which fails.

        restBankaMockMvc.perform(post("/api/bankas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(banka)))
                .andExpect(status().isBadRequest());

        List<Banka> bankas = bankaRepository.findAll();
        assertThat(bankas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNazivIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankaRepository.findAll().size();
        // set the field null
        banka.setNaziv(null);

        // Create the Banka, which fails.

        restBankaMockMvc.perform(post("/api/bankas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(banka)))
                .andExpect(status().isBadRequest());

        List<Banka> bankas = bankaRepository.findAll();
        assertThat(bankas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAdresaIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankaRepository.findAll().size();
        // set the field null
        banka.setAdresa(null);

        // Create the Banka, which fails.

        restBankaMockMvc.perform(post("/api/bankas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(banka)))
                .andExpect(status().isBadRequest());

        List<Banka> bankas = bankaRepository.findAll();
        assertThat(bankas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBankaIntIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankaRepository.findAll().size();
        // set the field null
        banka.setBankaInt(null);

        // Create the Banka, which fails.

        restBankaMockMvc.perform(post("/api/bankas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(banka)))
                .andExpect(status().isBadRequest());

        List<Banka> bankas = bankaRepository.findAll();
        assertThat(bankas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBankas() throws Exception {
        // Initialize the database
        bankaRepository.saveAndFlush(banka);

        // Get all the bankas
        restBankaMockMvc.perform(get("/api/bankas?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(banka.getId().intValue())))
                .andExpect(jsonPath("$.[*].sifraBanke").value(hasItem(DEFAULT_SIFRA_BANKE.toString())))
                .andExpect(jsonPath("$.[*].pib").value(hasItem(DEFAULT_PIB.toString())))
                .andExpect(jsonPath("$.[*].naziv").value(hasItem(DEFAULT_NAZIV.toString())))
                .andExpect(jsonPath("$.[*].adresa").value(hasItem(DEFAULT_ADRESA.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].web").value(hasItem(DEFAULT_WEB.toString())))
                .andExpect(jsonPath("$.[*].telefon").value(hasItem(DEFAULT_TELEFON.toString())))
                .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX.toString())))
                .andExpect(jsonPath("$.[*].bankaInt").value(hasItem(DEFAULT_BANKA_INT)));
    }

    @Test
    @Transactional
    public void getBanka() throws Exception {
        // Initialize the database
        bankaRepository.saveAndFlush(banka);

        // Get the banka
        restBankaMockMvc.perform(get("/api/bankas/{id}", banka.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(banka.getId().intValue()))
            .andExpect(jsonPath("$.sifraBanke").value(DEFAULT_SIFRA_BANKE.toString()))
            .andExpect(jsonPath("$.pib").value(DEFAULT_PIB.toString()))
            .andExpect(jsonPath("$.naziv").value(DEFAULT_NAZIV.toString()))
            .andExpect(jsonPath("$.adresa").value(DEFAULT_ADRESA.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.web").value(DEFAULT_WEB.toString()))
            .andExpect(jsonPath("$.telefon").value(DEFAULT_TELEFON.toString()))
            .andExpect(jsonPath("$.fax").value(DEFAULT_FAX.toString()))
            .andExpect(jsonPath("$.bankaInt").value(DEFAULT_BANKA_INT));
    }

    @Test
    @Transactional
    public void getNonExistingBanka() throws Exception {
        // Get the banka
        restBankaMockMvc.perform(get("/api/bankas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBanka() throws Exception {
        // Initialize the database
        bankaRepository.saveAndFlush(banka);
        int databaseSizeBeforeUpdate = bankaRepository.findAll().size();

        // Update the banka
        Banka updatedBanka = new Banka();
        updatedBanka.setId(banka.getId());
        updatedBanka.setSifraBanke(UPDATED_SIFRA_BANKE);
        updatedBanka.setPib(UPDATED_PIB);
        updatedBanka.setNaziv(UPDATED_NAZIV);
        updatedBanka.setAdresa(UPDATED_ADRESA);
        updatedBanka.setEmail(UPDATED_EMAIL);
        updatedBanka.setWeb(UPDATED_WEB);
        updatedBanka.setTelefon(UPDATED_TELEFON);
        updatedBanka.setFax(UPDATED_FAX);
        updatedBanka.setBankaInt(UPDATED_BANKA_INT);

        restBankaMockMvc.perform(put("/api/bankas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedBanka)))
                .andExpect(status().isOk());

        // Validate the Banka in the database
        List<Banka> bankas = bankaRepository.findAll();
        assertThat(bankas).hasSize(databaseSizeBeforeUpdate);
        Banka testBanka = bankas.get(bankas.size() - 1);
        assertThat(testBanka.getSifraBanke()).isEqualTo(UPDATED_SIFRA_BANKE);
        assertThat(testBanka.getPib()).isEqualTo(UPDATED_PIB);
        assertThat(testBanka.getNaziv()).isEqualTo(UPDATED_NAZIV);
        assertThat(testBanka.getAdresa()).isEqualTo(UPDATED_ADRESA);
        assertThat(testBanka.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testBanka.getWeb()).isEqualTo(UPDATED_WEB);
        assertThat(testBanka.getTelefon()).isEqualTo(UPDATED_TELEFON);
        assertThat(testBanka.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testBanka.getBankaInt()).isEqualTo(UPDATED_BANKA_INT);
    }

    @Test
    @Transactional
    public void deleteBanka() throws Exception {
        // Initialize the database
        bankaRepository.saveAndFlush(banka);
        int databaseSizeBeforeDelete = bankaRepository.findAll().size();

        // Get the banka
        restBankaMockMvc.perform(delete("/api/bankas/{id}", banka.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Banka> bankas = bankaRepository.findAll();
        assertThat(bankas).hasSize(databaseSizeBeforeDelete - 1);
    }
}
