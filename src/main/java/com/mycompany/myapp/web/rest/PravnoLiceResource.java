package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.PravnoLice;
import com.mycompany.myapp.repository.PravnoLiceRepository;
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
 * REST controller for managing PravnoLice.
 */
@RestController
@RequestMapping("/api")
public class PravnoLiceResource {

    private final Logger log = LoggerFactory.getLogger(PravnoLiceResource.class);
        
    @Inject
    private PravnoLiceRepository pravnoLiceRepository;
    
    /**
     * POST  /pravno-lice : Create a new pravnoLice.
     *
     * @param pravnoLice the pravnoLice to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pravnoLice, or with status 400 (Bad Request) if the pravnoLice has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/pravno-lice",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PravnoLice> createPravnoLice(@Valid @RequestBody PravnoLice pravnoLice) throws URISyntaxException {
        log.debug("REST request to save PravnoLice : {}", pravnoLice);
        if (pravnoLice.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("pravnoLice", "idexists", "A new pravnoLice cannot already have an ID")).body(null);
        }
        PravnoLice result = pravnoLiceRepository.save(pravnoLice);
        return ResponseEntity.created(new URI("/api/pravno-lice/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pravnoLice", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pravno-lice : Updates an existing pravnoLice.
     *
     * @param pravnoLice the pravnoLice to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pravnoLice,
     * or with status 400 (Bad Request) if the pravnoLice is not valid,
     * or with status 500 (Internal Server Error) if the pravnoLice couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/pravno-lice",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PravnoLice> updatePravnoLice(@Valid @RequestBody PravnoLice pravnoLice) throws URISyntaxException {
        log.debug("REST request to update PravnoLice : {}", pravnoLice);
        if (pravnoLice.getId() == null) {
            return createPravnoLice(pravnoLice);
        }
        PravnoLice result = pravnoLiceRepository.save(pravnoLice);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pravnoLice", pravnoLice.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pravno-lice : get all the pravnoLice.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pravnoLice in body
     */
    @RequestMapping(value = "/pravno-lice",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PravnoLice> getAllPravnoLice() {
        log.debug("REST request to get all PravnoLice");
        List<PravnoLice> pravnoLice = pravnoLiceRepository.findAll();
        return pravnoLice;
    }

    /**
     * GET  /pravno-lice/:id : get the "id" pravnoLice.
     *
     * @param id the id of the pravnoLice to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pravnoLice, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/pravno-lice/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PravnoLice> getPravnoLice(@PathVariable Long id) {
        log.debug("REST request to get PravnoLice : {}", id);
        PravnoLice pravnoLice = pravnoLiceRepository.findOne(id);
        return Optional.ofNullable(pravnoLice)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /pravno-lice/:id : delete the "id" pravnoLice.
     *
     * @param id the id of the pravnoLice to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/pravno-lice/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePravnoLice(@PathVariable Long id) {
        log.debug("REST request to delete PravnoLice : {}", id);
        pravnoLiceRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pravnoLice", id.toString())).build();
    }

}
