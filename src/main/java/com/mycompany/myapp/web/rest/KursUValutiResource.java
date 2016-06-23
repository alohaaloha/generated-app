package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.KursUValuti;
import com.mycompany.myapp.repository.KursUValutiRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing KursUValuti.
 */
@RestController
@RequestMapping("/api")
public class KursUValutiResource {

    private final Logger log = LoggerFactory.getLogger(KursUValutiResource.class);
        
    @Inject
    private KursUValutiRepository kursUValutiRepository;
    
    /**
     * POST  /kurs-u-valutis : Create a new kursUValuti.
     *
     * @param kursUValuti the kursUValuti to create
     * @return the ResponseEntity with status 201 (Created) and with body the new kursUValuti, or with status 400 (Bad Request) if the kursUValuti has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/kurs-u-valutis",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<KursUValuti> createKursUValuti(@Valid @RequestBody KursUValuti kursUValuti) throws URISyntaxException {
        log.debug("REST request to save KursUValuti : {}", kursUValuti);
        if (kursUValuti.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("kursUValuti", "idexists", "A new kursUValuti cannot already have an ID")).body(null);
        }
        KursUValuti result = kursUValutiRepository.save(kursUValuti);
        return ResponseEntity.created(new URI("/api/kurs-u-valutis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("kursUValuti", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /kurs-u-valutis : Updates an existing kursUValuti.
     *
     * @param kursUValuti the kursUValuti to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated kursUValuti,
     * or with status 400 (Bad Request) if the kursUValuti is not valid,
     * or with status 500 (Internal Server Error) if the kursUValuti couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/kurs-u-valutis",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<KursUValuti> updateKursUValuti(@Valid @RequestBody KursUValuti kursUValuti) throws URISyntaxException {
        log.debug("REST request to update KursUValuti : {}", kursUValuti);
        if (kursUValuti.getId() == null) {
            return createKursUValuti(kursUValuti);
        }
        KursUValuti result = kursUValutiRepository.save(kursUValuti);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("kursUValuti", kursUValuti.getId().toString()))
            .body(result);
    }

    /**
     * GET  /kurs-u-valutis : get all the kursUValutis.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of kursUValutis in body
     */
    @RequestMapping(value = "/kurs-u-valutis",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<KursUValuti> getAllKursUValutis() {
        log.debug("REST request to get all KursUValutis");
        List<KursUValuti> kursUValutis = kursUValutiRepository.findAll();
        return kursUValutis;
    }

    /**
     * GET  /kurs-u-valutis/:id : get the "id" kursUValuti.
     *
     * @param id the id of the kursUValuti to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the kursUValuti, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/kurs-u-valutis/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<KursUValuti> getKursUValuti(@PathVariable Long id) {
        log.debug("REST request to get KursUValuti : {}", id);
        KursUValuti kursUValuti = kursUValutiRepository.findOne(id);
        return Optional.ofNullable(kursUValuti)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /kurs-u-valutis/:id : delete the "id" kursUValuti.
     *
     * @param id the id of the kursUValuti to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/kurs-u-valutis/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteKursUValuti(@PathVariable Long id) {
        log.debug("REST request to delete KursUValuti : {}", id);
        kursUValutiRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("kursUValuti", id.toString())).build();
    }

}
