package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.jcabi.xml.XMLDocument;
import com.mycompany.myapp.domain.AnalitikaIzvoda;
import com.mycompany.myapp.repository.AnalitikaIzvodaRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing AnalitikaIzvoda.
 */
@RestController
@RequestMapping("/api")
public class AnalitikaIzvodaResource {

    private final Logger log = LoggerFactory.getLogger(AnalitikaIzvodaResource.class);

    @Inject
    private AnalitikaIzvodaRepository analitikaIzvodaRepository;


     /* ANALITIKA IZVODA UPLOAD */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/upload")
    public void upload2(@RequestParam("file") MultipartFile file) throws IOException {



        System.out.println("UPLOAD USAO");
        byte[] bytes;
        Document docThatIsSent = null;

        if (!file.isEmpty()) {
            bytes = file.getBytes();
            //store file in storage
            //--------------------
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = null;
            try {
                builder = factory.newDocumentBuilder();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
            try {
                 docThatIsSent = builder.parse(new ByteArrayInputStream(bytes));
            } catch (SAXException e) {
                e.printStackTrace();
            }

            String xml = new XMLDocument(docThatIsSent).toString();
            System.out.println("toString():"+xml);
            //--------------------
        }
        String kobaja = "jcbc:jtds:sqlserver//localhost:3306/pinf_pro";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(kobaja, "root", "basepass");
            CallableStatement callStatement = connection.prepareCall("{call placanje()}");
            callStatement.registerOutParameter("rtgs_id", Types.BIGINT);
            callStatement.setString("duznik", docThatIsSent.getElementsByTagName("duznik").item(0).getNodeValue());
            callStatement.setString("svrha", docThatIsSent.getElementsByTagName("svrha-placanja").item(0).getNodeValue());
            callStatement.setString("poverilac", docThatIsSent.getElementsByTagName("poverilac").item(0).getNodeValue());
            ZonedDateTime datumPrijema = ZonedDateTime.parse(docThatIsSent.getElementsByTagName("datum-prijema").item(0).getNodeValue());
            callStatement.setTimestamp("datum_prijema", new Timestamp(datumPrijema.toEpochSecond()*1000));
            ZonedDateTime datumValute = ZonedDateTime.parse(docThatIsSent.getElementsByTagName("datum-valute").item(0).getNodeValue());
            callStatement.setTimestamp("datum_valute", new Timestamp(datumValute.toEpochSecond()*1000));
            callStatement.setString("racun_duznika", docThatIsSent.getElementsByTagName("racun-duznika").item(0).getNodeValue());
            callStatement.setInt("model_zaduzenja", Integer.parseInt(docThatIsSent.getElementsByTagName("model-zaduzenja").item(0).getNodeValue()));
            callStatement.setString("poziv_na_broj_zaduzenja", docThatIsSent.getElementsByTagName("poziv-na-broj-zaduzenja").item(0).getNodeValue());
            callStatement.setString("racun_poverioca", docThatIsSent.getElementsByTagName("racun-poverioca").item(0).getNodeValue());
            callStatement.setInt("model_odobrenja", Integer.parseInt(docThatIsSent.getElementsByTagName("model-odobrenja").item(0).getNodeValue()));
            callStatement.setString("poziv_na_broj_odobrenja", docThatIsSent.getElementsByTagName("poziv-na-broj-odobrenja").item(0).getNodeValue());
            callStatement.setBoolean("is_hitno", Boolean.parseBoolean(docThatIsSent.getElementsByTagName("hitno").item(0).getNodeValue()));
            callStatement.setDouble("iznos", Double.parseDouble(docThatIsSent.getElementsByTagName("iznos").item(0).getNodeValue()));
            callStatement.setInt("tip_greske", Integer.parseInt(docThatIsSent.getElementsByTagName("tip-greske").item(0).getNodeValue()));
            callStatement.setString("status_naloga", docThatIsSent.getElementsByTagName("status").item(0).getNodeValue());
            callStatement.setString("naziv_mesta", docThatIsSent.getElementsByTagName("mesto-prijema").item(0).getNodeValue());
            callStatement.setInt("oznaka_vrste_placanja", Integer.parseInt(docThatIsSent.getElementsByTagName("vrsta-placanja").item(0).getNodeValue()));
            callStatement.setString("oznaka_valute_placanja", docThatIsSent.getElementsByTagName("valuta-placanja").item(0).getNodeValue());

            boolean imaRezultata = callStatement.execute();
            int outputValue = callStatement.getInt("inOutParam");

            log.info("Got RTGS set with an ID: " + outputValue);

        } catch (SQLException e) {
            log.error("Not able to connect to the database pinf_pro. Check if database service is running and URL and credentials are valid");
            e.printStackTrace();
        }


        log.info("Ended import of AnalitikaIzvoda from XML file");
    }

    /**
     * POST  /analitika-izvodas : Create a new analitikaIzvoda.
     *
     * @param analitikaIzvoda the analitikaIzvoda to create
     * @return the ResponseEntity with status 201 (Created) and with body the new analitikaIzvoda, or with status 400 (Bad Request) if the analitikaIzvoda has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/analitika-izvodas",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AnalitikaIzvoda> createAnalitikaIzvoda(@Valid @RequestBody AnalitikaIzvoda analitikaIzvoda) throws URISyntaxException {
        log.debug("REST request to save AnalitikaIzvoda : {}", analitikaIzvoda);
        if (analitikaIzvoda.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("analitikaIzvoda", "idexists", "A new analitikaIzvoda cannot already have an ID")).body(null);
        }
        AnalitikaIzvoda result = analitikaIzvodaRepository.save(analitikaIzvoda);
        return ResponseEntity.created(new URI("/api/analitika-izvodas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("analitikaIzvoda", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /analitika-izvodas : Updates an existing analitikaIzvoda.
     *
     * @param analitikaIzvoda the analitikaIzvoda to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated analitikaIzvoda,
     * or with status 400 (Bad Request) if the analitikaIzvoda is not valid,
     * or with status 500 (Internal Server Error) if the analitikaIzvoda couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/analitika-izvodas",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AnalitikaIzvoda> updateAnalitikaIzvoda(@Valid @RequestBody AnalitikaIzvoda analitikaIzvoda) throws URISyntaxException {
        log.debug("REST request to update AnalitikaIzvoda : {}", analitikaIzvoda);
        if (analitikaIzvoda.getId() == null) {
            return createAnalitikaIzvoda(analitikaIzvoda);
        }
        AnalitikaIzvoda result = analitikaIzvodaRepository.save(analitikaIzvoda);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("analitikaIzvoda", analitikaIzvoda.getId().toString()))
            .body(result);
    }

    /**
     * GET  /analitika-izvodas : get all the analitikaIzvodas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of analitikaIzvodas in body
     */
    @RequestMapping(value = "/analitika-izvodas",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AnalitikaIzvoda> getAllAnalitikaIzvodas() {
        log.debug("REST request to get all AnalitikaIzvodas");
        List<AnalitikaIzvoda> analitikaIzvodas = analitikaIzvodaRepository.findAll();
        return analitikaIzvodas;
    }

    /**
     * GET  /analitika-izvodas/:id : get the "id" analitikaIzvoda.
     *
     * @param id the id of the analitikaIzvoda to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the analitikaIzvoda, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/analitika-izvodas/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AnalitikaIzvoda> getAnalitikaIzvoda(@PathVariable Long id) {
        log.debug("REST request to get AnalitikaIzvoda : {}", id);
        AnalitikaIzvoda analitikaIzvoda = analitikaIzvodaRepository.findOne(id);
        return Optional.ofNullable(analitikaIzvoda)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /analitika-izvodas/:id : delete the "id" analitikaIzvoda.
     *
     * @param id the id of the analitikaIzvoda to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/analitika-izvodas/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAnalitikaIzvoda(@PathVariable Long id) {
        log.debug("REST request to delete AnalitikaIzvoda : {}", id);
        analitikaIzvodaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("analitikaIzvoda", id.toString())).build();
    }

}
