package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Valuta;
import com.mycompany.myapp.repository.ValutaRepository;
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
 * REST controller for managing Valuta.
 */
@RestController
@RequestMapping("/api")
public class ValutaResource {

    private final Logger log = LoggerFactory.getLogger(ValutaResource.class);
        
    @Inject
    private ValutaRepository valutaRepository;
    
    /**
     * POST  /valutas : Create a new valuta.
     *
     * @param valuta the valuta to create
     * @return the ResponseEntity with status 201 (Created) and with body the new valuta, or with status 400 (Bad Request) if the valuta has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/valutas",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Valuta> createValuta(@Valid @RequestBody Valuta valuta) throws URISyntaxException {
        log.debug("REST request to save Valuta : {}", valuta);
        if (valuta.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("valuta", "idexists", "A new valuta cannot already have an ID")).body(null);
        }
        Valuta result = valutaRepository.save(valuta);
        return ResponseEntity.created(new URI("/api/valutas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("valuta", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /valutas : Updates an existing valuta.
     *
     * @param valuta the valuta to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated valuta,
     * or with status 400 (Bad Request) if the valuta is not valid,
     * or with status 500 (Internal Server Error) if the valuta couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/valutas",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Valuta> updateValuta(@Valid @RequestBody Valuta valuta) throws URISyntaxException {
        log.debug("REST request to update Valuta : {}", valuta);
        if (valuta.getId() == null) {
            return createValuta(valuta);
        }
        Valuta result = valutaRepository.save(valuta);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("valuta", valuta.getId().toString()))
            .body(result);
    }

    /**
     * GET  /valutas : get all the valutas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of valutas in body
     */
    @RequestMapping(value = "/valutas",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Valuta> getAllValutas() {
        log.debug("REST request to get all Valutas");
        List<Valuta> valutas = valutaRepository.findAll();
        return valutas;
    }

    /**
     * GET  /valutas/:id : get the "id" valuta.
     *
     * @param id the id of the valuta to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the valuta, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/valutas/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Valuta> getValuta(@PathVariable Long id) {
        log.debug("REST request to get Valuta : {}", id);
        Valuta valuta = valutaRepository.findOne(id);
        return Optional.ofNullable(valuta)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /valutas/:id : delete the "id" valuta.
     *
     * @param id the id of the valuta to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/valutas/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteValuta(@PathVariable Long id) {
        log.debug("REST request to delete Valuta : {}", id);
        valutaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("valuta", id.toString())).build();
    }

}
