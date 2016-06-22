package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Drzava;
import com.mycompany.myapp.repository.DrzavaRepository;
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
 * REST controller for managing Drzava.
 */
@RestController
@RequestMapping("/api")
public class DrzavaResource {

    private final Logger log = LoggerFactory.getLogger(DrzavaResource.class);
        
    @Inject
    private DrzavaRepository drzavaRepository;
    
    /**
     * POST  /drzavas : Create a new drzava.
     *
     * @param drzava the drzava to create
     * @return the ResponseEntity with status 201 (Created) and with body the new drzava, or with status 400 (Bad Request) if the drzava has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/drzavas",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Drzava> createDrzava(@Valid @RequestBody Drzava drzava) throws URISyntaxException {
        log.debug("REST request to save Drzava : {}", drzava);
        if (drzava.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("drzava", "idexists", "A new drzava cannot already have an ID")).body(null);
        }
        Drzava result = drzavaRepository.save(drzava);
        return ResponseEntity.created(new URI("/api/drzavas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("drzava", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /drzavas : Updates an existing drzava.
     *
     * @param drzava the drzava to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated drzava,
     * or with status 400 (Bad Request) if the drzava is not valid,
     * or with status 500 (Internal Server Error) if the drzava couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/drzavas",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Drzava> updateDrzava(@Valid @RequestBody Drzava drzava) throws URISyntaxException {
        log.debug("REST request to update Drzava : {}", drzava);
        if (drzava.getId() == null) {
            return createDrzava(drzava);
        }
        Drzava result = drzavaRepository.save(drzava);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("drzava", drzava.getId().toString()))
            .body(result);
    }

    /**
     * GET  /drzavas : get all the drzavas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of drzavas in body
     */
    @RequestMapping(value = "/drzavas",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Drzava> getAllDrzavas() {
        log.debug("REST request to get all Drzavas");
        List<Drzava> drzavas = drzavaRepository.findAll();
        return drzavas;
    }

    /**
     * GET  /drzavas/:id : get the "id" drzava.
     *
     * @param id the id of the drzava to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the drzava, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/drzavas/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Drzava> getDrzava(@PathVariable Long id) {
        log.debug("REST request to get Drzava : {}", id);
        Drzava drzava = drzavaRepository.findOne(id);
        return Optional.ofNullable(drzava)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /drzavas/:id : delete the "id" drzava.
     *
     * @param id the id of the drzava to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/drzavas/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDrzava(@PathVariable Long id) {
        log.debug("REST request to delete Drzava : {}", id);
        drzavaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("drzava", id.toString())).build();
    }

}
