package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.DnevnoStanjeRacuna;
import com.mycompany.myapp.repository.DnevnoStanjeRacunaRepository;
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
 * REST controller for managing DnevnoStanjeRacuna.
 */
@RestController
@RequestMapping("/api")
public class DnevnoStanjeRacunaResource {

    private final Logger log = LoggerFactory.getLogger(DnevnoStanjeRacunaResource.class);
        
    @Inject
    private DnevnoStanjeRacunaRepository dnevnoStanjeRacunaRepository;
    
    /**
     * POST  /dnevno-stanje-racunas : Create a new dnevnoStanjeRacuna.
     *
     * @param dnevnoStanjeRacuna the dnevnoStanjeRacuna to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dnevnoStanjeRacuna, or with status 400 (Bad Request) if the dnevnoStanjeRacuna has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/dnevno-stanje-racunas",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DnevnoStanjeRacuna> createDnevnoStanjeRacuna(@Valid @RequestBody DnevnoStanjeRacuna dnevnoStanjeRacuna) throws URISyntaxException {
        log.debug("REST request to save DnevnoStanjeRacuna : {}", dnevnoStanjeRacuna);
        if (dnevnoStanjeRacuna.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("dnevnoStanjeRacuna", "idexists", "A new dnevnoStanjeRacuna cannot already have an ID")).body(null);
        }
        DnevnoStanjeRacuna result = dnevnoStanjeRacunaRepository.save(dnevnoStanjeRacuna);
        return ResponseEntity.created(new URI("/api/dnevno-stanje-racunas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("dnevnoStanjeRacuna", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dnevno-stanje-racunas : Updates an existing dnevnoStanjeRacuna.
     *
     * @param dnevnoStanjeRacuna the dnevnoStanjeRacuna to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dnevnoStanjeRacuna,
     * or with status 400 (Bad Request) if the dnevnoStanjeRacuna is not valid,
     * or with status 500 (Internal Server Error) if the dnevnoStanjeRacuna couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/dnevno-stanje-racunas",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DnevnoStanjeRacuna> updateDnevnoStanjeRacuna(@Valid @RequestBody DnevnoStanjeRacuna dnevnoStanjeRacuna) throws URISyntaxException {
        log.debug("REST request to update DnevnoStanjeRacuna : {}", dnevnoStanjeRacuna);
        if (dnevnoStanjeRacuna.getId() == null) {
            return createDnevnoStanjeRacuna(dnevnoStanjeRacuna);
        }
        DnevnoStanjeRacuna result = dnevnoStanjeRacunaRepository.save(dnevnoStanjeRacuna);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("dnevnoStanjeRacuna", dnevnoStanjeRacuna.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dnevno-stanje-racunas : get all the dnevnoStanjeRacunas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of dnevnoStanjeRacunas in body
     */
    @RequestMapping(value = "/dnevno-stanje-racunas",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DnevnoStanjeRacuna> getAllDnevnoStanjeRacunas() {
        log.debug("REST request to get all DnevnoStanjeRacunas");
        List<DnevnoStanjeRacuna> dnevnoStanjeRacunas = dnevnoStanjeRacunaRepository.findAll();
        return dnevnoStanjeRacunas;
    }

    /**
     * GET  /dnevno-stanje-racunas/:id : get the "id" dnevnoStanjeRacuna.
     *
     * @param id the id of the dnevnoStanjeRacuna to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dnevnoStanjeRacuna, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/dnevno-stanje-racunas/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DnevnoStanjeRacuna> getDnevnoStanjeRacuna(@PathVariable Long id) {
        log.debug("REST request to get DnevnoStanjeRacuna : {}", id);
        DnevnoStanjeRacuna dnevnoStanjeRacuna = dnevnoStanjeRacunaRepository.findOne(id);
        return Optional.ofNullable(dnevnoStanjeRacuna)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /dnevno-stanje-racunas/:id : delete the "id" dnevnoStanjeRacuna.
     *
     * @param id the id of the dnevnoStanjeRacuna to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/dnevno-stanje-racunas/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDnevnoStanjeRacuna(@PathVariable Long id) {
        log.debug("REST request to delete DnevnoStanjeRacuna : {}", id);
        dnevnoStanjeRacunaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("dnevnoStanjeRacuna", id.toString())).build();
    }

}
