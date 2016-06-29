package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Kliring;
import com.mycompany.myapp.repository.KliringRepository;
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
 * REST controller for managing Kliring.
 */
@RestController
@RequestMapping("/api")
public class KliringResource {

    private final Logger log = LoggerFactory.getLogger(KliringResource.class);

    @Inject
    private KliringRepository kliringRepository;

    /**
     * POST  /klirings : Create a new kliring.
     *
     * @param kliring the kliring to create
     * @return the ResponseEntity with status 201 (Created) and with body the new kliring, or with status 400 (Bad Request) if the kliring has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/klirings",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Kliring> createKliring(@Valid @RequestBody Kliring kliring) throws URISyntaxException {
        log.debug("REST request to save Kliring : {}", kliring);
        if (kliring.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("kliring", "idexists", "A new kliring cannot already have an ID")).body(null);
        }
        Kliring result = kliringRepository.save(kliring);
        return ResponseEntity.created(new URI("/api/klirings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("kliring", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /klirings : Updates an existing kliring.
     *
     * @param kliring the kliring to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated kliring,
     * or with status 400 (Bad Request) if the kliring is not valid,
     * or with status 500 (Internal Server Error) if the kliring couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/klirings",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Kliring> updateKliring(@Valid @RequestBody Kliring kliring) throws URISyntaxException {
        log.debug("REST request to update Kliring : {}", kliring);
        if (kliring.getId() == null) {
            return createKliring(kliring);
        }
        Kliring result = kliringRepository.save(kliring);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("kliring", kliring.getId().toString()))
            .body(result);
    }

    /**
     * GET  /klirings : get all the klirings.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of klirings in body
     */
    @RequestMapping(value = "/klirings",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Kliring> getAllKlirings() {
        log.debug("REST request to get all Klirings");
        List<Kliring> klirings = kliringRepository.findAll();
        return klirings;
    }

    /**
     * GET  /klirings/:id : get the "id" kliring.
     *
     * @param id the id of the kliring to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the kliring, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/klirings/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Kliring> getKliring(@PathVariable Long id) {
        log.debug("REST request to get Kliring : {}", id);
        Kliring kliring = kliringRepository.findOne(id);
        return Optional.ofNullable(kliring)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /klirings/:id : delete the "id" kliring.
     *
     * @param id the id of the kliring to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/klirings/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteKliring(@PathVariable Long id) {
        log.debug("REST request to delete Kliring : {}", id);
        kliringRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("kliring", id.toString())).build();
    }

    /**
     * EXECUTES one unsent Kliring period
     *
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/klirings/izvrsi",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Kliring> executeKliring() {
        log.debug("REST request to execute Kliring");
        List<Kliring> klirings = kliringRepository.findAll();
        Kliring kliringToExecute = null;
        for(Kliring kliring : klirings){
            if(!kliring.isPoslat()) {
                kliringToExecute = kliring;
                break;
            }
        }
        kliringToExecute.setPoslat(true);
        kliringToExecute.exportToXml(System.out);
        kliringRepository.save(kliringToExecute);
        return klirings;
    }
}
