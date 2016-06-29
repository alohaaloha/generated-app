package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.RacunPravnogLica;
import com.mycompany.myapp.repository.RacunPravnogLicaRepository;
import com.mycompany.myapp.web.rest.dto.RacunPravnogLicaDTO;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing RacunPravnogLica.
 */
@RestController
@RequestMapping("/api")
public class RacunPravnogLicaResource {

    private final Logger log = LoggerFactory.getLogger(RacunPravnogLicaResource.class);

    @Inject
    private RacunPravnogLicaRepository racunPravnogLicaRepository;

    /**
     * POST  /racun-pravnog-licas : Create a new racunPravnogLica.
     *
     * @param racunPravnogLica the racunPravnogLica to create
     * @return the ResponseEntity with status 201 (Created) and with body the new racunPravnogLica, or with status 400 (Bad Request) if the racunPravnogLica has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/racun-pravnog-licas",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RacunPravnogLica> createRacunPravnogLica(@Valid @RequestBody RacunPravnogLica racunPravnogLica) throws URISyntaxException {
        log.debug("REST request to save RacunPravnogLica : {}", racunPravnogLica);
        if (racunPravnogLica.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("racunPravnogLica", "idexists", "A new racunPravnogLica cannot already have an ID")).body(null);
        }
        RacunPravnogLica result = racunPravnogLicaRepository.save(racunPravnogLica);
        return ResponseEntity.created(new URI("/api/racun-pravnog-licas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("racunPravnogLica", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /racun-pravnog-licas : Updates an existing racunPravnogLica.
     *
     * @param racunPravnogLica the racunPravnogLica to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated racunPravnogLica,
     * or with status 400 (Bad Request) if the racunPravnogLica is not valid,
     * or with status 500 (Internal Server Error) if the racunPravnogLica couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/racun-pravnog-licas",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RacunPravnogLica> updateRacunPravnogLica(@Valid @RequestBody RacunPravnogLica racunPravnogLica) throws URISyntaxException {
        log.debug("REST request to update RacunPravnogLica : {}", racunPravnogLica);
        if (racunPravnogLica.getId() == null) {
            return createRacunPravnogLica(racunPravnogLica);
        }
        RacunPravnogLica result = racunPravnogLicaRepository.save(racunPravnogLica);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("racunPravnogLica", racunPravnogLica.getId().toString()))
            .body(result);
    }

    /**
     * GET  /racun-pravnog-licas : get all the racunPravnogLicas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of racunPravnogLicas in body
     */
    @RequestMapping(value = "/racun-pravnog-licas",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<RacunPravnogLicaDTO> getAllRacunPravnogLicas() {
        log.debug("REST request to get all RacunPravnogLicas");
        List<RacunPravnogLica> racunPravnogLicas = racunPravnogLicaRepository.findAll();
        List<RacunPravnogLicaDTO> racunPravnogLicaDTOs = new ArrayList<>();
        for(RacunPravnogLica racunPravnogLica : racunPravnogLicas){
            racunPravnogLicaDTOs.add(new RacunPravnogLicaDTO(racunPravnogLica));
        }
        return racunPravnogLicaDTOs;
    }

    /**
     * GET  /racun-pravnog-licas/:id : get the "id" racunPravnogLica.
     *
     * @param id the id of the racunPravnogLica to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the racunPravnogLica, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/racun-pravnog-licas/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RacunPravnogLicaDTO> getRacunPravnogLica(@PathVariable Long id) {
        log.debug("REST request to get RacunPravnogLica : {}", id);
        RacunPravnogLica racunPravnogLica = racunPravnogLicaRepository.findOne(id);
        RacunPravnogLicaDTO racunPravnogLicaDTO = new RacunPravnogLicaDTO(racunPravnogLica);
        return Optional.ofNullable(racunPravnogLicaDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /racun-pravnog-licas/:id : delete the "id" racunPravnogLica.
     *
     * @param id the id of the racunPravnogLica to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/racun-pravnog-licas/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteRacunPravnogLica(@PathVariable Long id) {
        log.debug("REST request to delete RacunPravnogLica : {}", id);
        racunPravnogLicaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("racunPravnogLica", id.toString())).build();
    }

}
