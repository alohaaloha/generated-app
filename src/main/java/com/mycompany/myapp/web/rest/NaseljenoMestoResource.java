package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.NaseljenoMesto;
import com.mycompany.myapp.repository.NaseljenoMestoRepository;
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
 * REST controller for managing NaseljenoMesto.
 */
@RestController
@RequestMapping("/api")
public class NaseljenoMestoResource {

    private final Logger log = LoggerFactory.getLogger(NaseljenoMestoResource.class);
        
    @Inject
    private NaseljenoMestoRepository naseljenoMestoRepository;
    
    /**
     * POST  /naseljeno-mestos : Create a new naseljenoMesto.
     *
     * @param naseljenoMesto the naseljenoMesto to create
     * @return the ResponseEntity with status 201 (Created) and with body the new naseljenoMesto, or with status 400 (Bad Request) if the naseljenoMesto has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/naseljeno-mestos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<NaseljenoMesto> createNaseljenoMesto(@Valid @RequestBody NaseljenoMesto naseljenoMesto) throws URISyntaxException {
        log.debug("REST request to save NaseljenoMesto : {}", naseljenoMesto);
        if (naseljenoMesto.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("naseljenoMesto", "idexists", "A new naseljenoMesto cannot already have an ID")).body(null);
        }
        NaseljenoMesto result = naseljenoMestoRepository.save(naseljenoMesto);
        return ResponseEntity.created(new URI("/api/naseljeno-mestos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("naseljenoMesto", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /naseljeno-mestos : Updates an existing naseljenoMesto.
     *
     * @param naseljenoMesto the naseljenoMesto to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated naseljenoMesto,
     * or with status 400 (Bad Request) if the naseljenoMesto is not valid,
     * or with status 500 (Internal Server Error) if the naseljenoMesto couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/naseljeno-mestos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<NaseljenoMesto> updateNaseljenoMesto(@Valid @RequestBody NaseljenoMesto naseljenoMesto) throws URISyntaxException {
        log.debug("REST request to update NaseljenoMesto : {}", naseljenoMesto);
        if (naseljenoMesto.getId() == null) {
            return createNaseljenoMesto(naseljenoMesto);
        }
        NaseljenoMesto result = naseljenoMestoRepository.save(naseljenoMesto);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("naseljenoMesto", naseljenoMesto.getId().toString()))
            .body(result);
    }

    /**
     * GET  /naseljeno-mestos : get all the naseljenoMestos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of naseljenoMestos in body
     */
    @RequestMapping(value = "/naseljeno-mestos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<NaseljenoMesto> getAllNaseljenoMestos() {
        log.debug("REST request to get all NaseljenoMestos");
        List<NaseljenoMesto> naseljenoMestos = naseljenoMestoRepository.findAll();
        return naseljenoMestos;
    }

    /**
     * GET  /naseljeno-mestos/:id : get the "id" naseljenoMesto.
     *
     * @param id the id of the naseljenoMesto to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the naseljenoMesto, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/naseljeno-mestos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<NaseljenoMesto> getNaseljenoMesto(@PathVariable Long id) {
        log.debug("REST request to get NaseljenoMesto : {}", id);
        NaseljenoMesto naseljenoMesto = naseljenoMestoRepository.findOne(id);
        return Optional.ofNullable(naseljenoMesto)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /naseljeno-mestos/:id : delete the "id" naseljenoMesto.
     *
     * @param id the id of the naseljenoMesto to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/naseljeno-mestos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteNaseljenoMesto(@PathVariable Long id) {
        log.debug("REST request to delete NaseljenoMesto : {}", id);
        naseljenoMestoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("naseljenoMesto", id.toString())).build();
    }

}
