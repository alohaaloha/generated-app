package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Banka;
import com.mycompany.myapp.repository.BankaRepository;
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
 * REST controller for managing Banka.
 */
@RestController
@RequestMapping("/api")
public class BankaResource {

    private final Logger log = LoggerFactory.getLogger(BankaResource.class);
        
    @Inject
    private BankaRepository bankaRepository;
    
    /**
     * POST  /bankas : Create a new banka.
     *
     * @param banka the banka to create
     * @return the ResponseEntity with status 201 (Created) and with body the new banka, or with status 400 (Bad Request) if the banka has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/bankas",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Banka> createBanka(@Valid @RequestBody Banka banka) throws URISyntaxException {
        log.debug("REST request to save Banka : {}", banka);
        if (banka.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("banka", "idexists", "A new banka cannot already have an ID")).body(null);
        }
        Banka result = bankaRepository.save(banka);
        return ResponseEntity.created(new URI("/api/bankas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("banka", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bankas : Updates an existing banka.
     *
     * @param banka the banka to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated banka,
     * or with status 400 (Bad Request) if the banka is not valid,
     * or with status 500 (Internal Server Error) if the banka couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/bankas",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Banka> updateBanka(@Valid @RequestBody Banka banka) throws URISyntaxException {
        log.debug("REST request to update Banka : {}", banka);
        if (banka.getId() == null) {
            return createBanka(banka);
        }
        Banka result = bankaRepository.save(banka);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("banka", banka.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bankas : get all the bankas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bankas in body
     */
    @RequestMapping(value = "/bankas",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Banka> getAllBankas() {
        log.debug("REST request to get all Bankas");
        List<Banka> bankas = bankaRepository.findAll();
        return bankas;
    }

    /**
     * GET  /bankas/:id : get the "id" banka.
     *
     * @param id the id of the banka to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the banka, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/bankas/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Banka> getBanka(@PathVariable Long id) {
        log.debug("REST request to get Banka : {}", id);
        Banka banka = bankaRepository.findOne(id);
        return Optional.ofNullable(banka)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /bankas/:id : delete the "id" banka.
     *
     * @param id the id of the banka to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/bankas/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteBanka(@PathVariable Long id) {
        log.debug("REST request to delete Banka : {}", id);
        bankaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("banka", id.toString())).build();
    }

}
