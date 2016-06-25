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
            /*THIS IS DOCUMENT THAT IS SENT*/
            Document docThatIsSent = null;
            try {
                 docThatIsSent = builder.parse(new ByteArrayInputStream(bytes));
            } catch (SAXException e) {
                e.printStackTrace();
            }

            String xml = new XMLDocument(docThatIsSent).toString();
            System.out.println("toString():"+xml);
            //--------------------
        }

        System.out.println("KRAJ UPLOAD");
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
