package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.StavkaKliringa;
import com.mycompany.myapp.repository.StavkaKliringaRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing StavkaKliringa.
 */
@RestController
@RequestMapping("/api")
public class StavkaKliringaResource {

    private final Logger log = LoggerFactory.getLogger(StavkaKliringaResource.class);
        
    @Inject
    private StavkaKliringaRepository stavkaKliringaRepository;
    
    /**
     * POST  /stavka-kliringas : Create a new stavkaKliringa.
     *
     * @param stavkaKliringa the stavkaKliringa to create
     * @return the ResponseEntity with status 201 (Created) and with body the new stavkaKliringa, or with status 400 (Bad Request) if the stavkaKliringa has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/stavka-kliringas",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<StavkaKliringa> createStavkaKliringa(@RequestBody StavkaKliringa stavkaKliringa) throws URISyntaxException {
        log.debug("REST request to save StavkaKliringa : {}", stavkaKliringa);
        if (stavkaKliringa.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("stavkaKliringa", "idexists", "A new stavkaKliringa cannot already have an ID")).body(null);
        }
        StavkaKliringa result = stavkaKliringaRepository.save(stavkaKliringa);
        return ResponseEntity.created(new URI("/api/stavka-kliringas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("stavkaKliringa", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /stavka-kliringas : Updates an existing stavkaKliringa.
     *
     * @param stavkaKliringa the stavkaKliringa to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated stavkaKliringa,
     * or with status 400 (Bad Request) if the stavkaKliringa is not valid,
     * or with status 500 (Internal Server Error) if the stavkaKliringa couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/stavka-kliringas",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<StavkaKliringa> updateStavkaKliringa(@RequestBody StavkaKliringa stavkaKliringa) throws URISyntaxException {
        log.debug("REST request to update StavkaKliringa : {}", stavkaKliringa);
        if (stavkaKliringa.getId() == null) {
            return createStavkaKliringa(stavkaKliringa);
        }
        StavkaKliringa result = stavkaKliringaRepository.save(stavkaKliringa);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("stavkaKliringa", stavkaKliringa.getId().toString()))
            .body(result);
    }

    /**
     * GET  /stavka-kliringas : get all the stavkaKliringas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of stavkaKliringas in body
     */
    @RequestMapping(value = "/stavka-kliringas",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<StavkaKliringa> getAllStavkaKliringas() {
        log.debug("REST request to get all StavkaKliringas");
        List<StavkaKliringa> stavkaKliringas = stavkaKliringaRepository.findAll();
        return stavkaKliringas;
    }

    /**
     * GET  /stavka-kliringas/:id : get the "id" stavkaKliringa.
     *
     * @param id the id of the stavkaKliringa to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the stavkaKliringa, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/stavka-kliringas/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<StavkaKliringa> getStavkaKliringa(@PathVariable Long id) {
        log.debug("REST request to get StavkaKliringa : {}", id);
        StavkaKliringa stavkaKliringa = stavkaKliringaRepository.findOne(id);
        return Optional.ofNullable(stavkaKliringa)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /stavka-kliringas/:id : delete the "id" stavkaKliringa.
     *
     * @param id the id of the stavkaKliringa to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/stavka-kliringas/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteStavkaKliringa(@PathVariable Long id) {
        log.debug("REST request to delete StavkaKliringa : {}", id);
        stavkaKliringaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("stavkaKliringa", id.toString())).build();
    }

}
