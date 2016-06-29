package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Klijent;
import com.mycompany.myapp.repository.KlijentRepository;
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
 * REST controller for managing Klijent.
 */
@RestController
@RequestMapping("/api")
public class KlijentResource {

    private final Logger log = LoggerFactory.getLogger(KlijentResource.class);

    @Inject
    private KlijentRepository klijentRepository;

    /**
     * POST  /klijents : Create a new klijent.
     *
     * @param klijent the klijent to create
     * @return the ResponseEntity with status 201 (Created) and with body the new klijent, or with status 400 (Bad Request) if the klijent has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/klijents",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Klijent> createKlijent(@Valid @RequestBody Klijent klijent) throws URISyntaxException {
        log.debug("REST request to save Klijent : {}", klijent);
        if (klijent.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("klijent", "idexists", "A new klijent cannot already have an ID")).body(null);
        }
        Klijent result = klijentRepository.save(klijent);
        return ResponseEntity.created(new URI("/api/klijents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("klijent", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /klijents : Updates an existing klijent.
     *
     * @param klijent the klijent to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated klijent,
     * or with status 400 (Bad Request) if the klijent is not valid,
     * or with status 500 (Internal Server Error) if the klijent couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/klijents",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Klijent> updateKlijent(@Valid @RequestBody Klijent klijent) throws URISyntaxException {
        log.debug("REST request to update Klijent : {}", klijent);
        if (klijent.getId() == null) {
            return createKlijent(klijent);
        }
        Klijent result = klijentRepository.save(klijent);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("klijent", klijent.getId().toString()))
            .body(result);
    }

    /**
     * GET  /klijents : get all the klijents.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of klijents in body
     */
    @RequestMapping(value = "/klijents",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Klijent> getAllKlijents() {
        log.debug("REST request to get all Klijents");
        List<Klijent> klijents = klijentRepository.findAll();
        return klijents;
    }

    /**
     * GET  /klijents/:id : get the "id" klijent.
     *
     * @param id the id of the klijent to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the klijent, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/klijents/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Klijent> getKlijent(@PathVariable Long id) {
        log.debug("REST request to get Klijent : {}", id);
        Klijent klijent = klijentRepository.findOne(id);
        return Optional.ofNullable(klijent)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /klijents/:id : delete the "id" klijent.
     *
     * @param id the id of the klijent to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/klijents/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteKlijent(@PathVariable Long id) {
        log.debug("REST request to delete Klijent : {}", id);
        klijentRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("klijent", id.toString())).build();
    }








}
