package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.VrstaPlacanja;
import com.mycompany.myapp.repository.VrstaPlacanjaRepository;
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
 * REST controller for managing VrstaPlacanja.
 */
@RestController
@RequestMapping("/api")
public class VrstaPlacanjaResource {

    private final Logger log = LoggerFactory.getLogger(VrstaPlacanjaResource.class);
        
    @Inject
    private VrstaPlacanjaRepository vrstaPlacanjaRepository;
    
    /**
     * POST  /vrsta-placanjas : Create a new vrstaPlacanja.
     *
     * @param vrstaPlacanja the vrstaPlacanja to create
     * @return the ResponseEntity with status 201 (Created) and with body the new vrstaPlacanja, or with status 400 (Bad Request) if the vrstaPlacanja has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/vrsta-placanjas",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<VrstaPlacanja> createVrstaPlacanja(@Valid @RequestBody VrstaPlacanja vrstaPlacanja) throws URISyntaxException {
        log.debug("REST request to save VrstaPlacanja : {}", vrstaPlacanja);
        if (vrstaPlacanja.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("vrstaPlacanja", "idexists", "A new vrstaPlacanja cannot already have an ID")).body(null);
        }
        VrstaPlacanja result = vrstaPlacanjaRepository.save(vrstaPlacanja);
        return ResponseEntity.created(new URI("/api/vrsta-placanjas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("vrstaPlacanja", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vrsta-placanjas : Updates an existing vrstaPlacanja.
     *
     * @param vrstaPlacanja the vrstaPlacanja to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated vrstaPlacanja,
     * or with status 400 (Bad Request) if the vrstaPlacanja is not valid,
     * or with status 500 (Internal Server Error) if the vrstaPlacanja couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/vrsta-placanjas",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<VrstaPlacanja> updateVrstaPlacanja(@Valid @RequestBody VrstaPlacanja vrstaPlacanja) throws URISyntaxException {
        log.debug("REST request to update VrstaPlacanja : {}", vrstaPlacanja);
        if (vrstaPlacanja.getId() == null) {
            return createVrstaPlacanja(vrstaPlacanja);
        }
        VrstaPlacanja result = vrstaPlacanjaRepository.save(vrstaPlacanja);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("vrstaPlacanja", vrstaPlacanja.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vrsta-placanjas : get all the vrstaPlacanjas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of vrstaPlacanjas in body
     */
    @RequestMapping(value = "/vrsta-placanjas",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<VrstaPlacanja> getAllVrstaPlacanjas() {
        log.debug("REST request to get all VrstaPlacanjas");
        List<VrstaPlacanja> vrstaPlacanjas = vrstaPlacanjaRepository.findAll();
        return vrstaPlacanjas;
    }

    /**
     * GET  /vrsta-placanjas/:id : get the "id" vrstaPlacanja.
     *
     * @param id the id of the vrstaPlacanja to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vrstaPlacanja, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/vrsta-placanjas/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<VrstaPlacanja> getVrstaPlacanja(@PathVariable Long id) {
        log.debug("REST request to get VrstaPlacanja : {}", id);
        VrstaPlacanja vrstaPlacanja = vrstaPlacanjaRepository.findOne(id);
        return Optional.ofNullable(vrstaPlacanja)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /vrsta-placanjas/:id : delete the "id" vrstaPlacanja.
     *
     * @param id the id of the vrstaPlacanja to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/vrsta-placanjas/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteVrstaPlacanja(@PathVariable Long id) {
        log.debug("REST request to delete VrstaPlacanja : {}", id);
        vrstaPlacanjaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("vrstaPlacanja", id.toString())).build();
    }

}
