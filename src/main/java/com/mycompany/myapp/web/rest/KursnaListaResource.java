package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.KursnaLista;
import com.mycompany.myapp.repository.KursnaListaRepository;
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
 * REST controller for managing KursnaLista.
 */
@RestController
@RequestMapping("/api")
public class KursnaListaResource {

    private final Logger log = LoggerFactory.getLogger(KursnaListaResource.class);
        
    @Inject
    private KursnaListaRepository kursnaListaRepository;
    
    /**
     * POST  /kursna-listas : Create a new kursnaLista.
     *
     * @param kursnaLista the kursnaLista to create
     * @return the ResponseEntity with status 201 (Created) and with body the new kursnaLista, or with status 400 (Bad Request) if the kursnaLista has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/kursna-listas",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<KursnaLista> createKursnaLista(@Valid @RequestBody KursnaLista kursnaLista) throws URISyntaxException {
        log.debug("REST request to save KursnaLista : {}", kursnaLista);
        if (kursnaLista.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("kursnaLista", "idexists", "A new kursnaLista cannot already have an ID")).body(null);
        }
        KursnaLista result = kursnaListaRepository.save(kursnaLista);
        return ResponseEntity.created(new URI("/api/kursna-listas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("kursnaLista", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /kursna-listas : Updates an existing kursnaLista.
     *
     * @param kursnaLista the kursnaLista to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated kursnaLista,
     * or with status 400 (Bad Request) if the kursnaLista is not valid,
     * or with status 500 (Internal Server Error) if the kursnaLista couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/kursna-listas",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<KursnaLista> updateKursnaLista(@Valid @RequestBody KursnaLista kursnaLista) throws URISyntaxException {
        log.debug("REST request to update KursnaLista : {}", kursnaLista);
        if (kursnaLista.getId() == null) {
            return createKursnaLista(kursnaLista);
        }
        KursnaLista result = kursnaListaRepository.save(kursnaLista);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("kursnaLista", kursnaLista.getId().toString()))
            .body(result);
    }

    /**
     * GET  /kursna-listas : get all the kursnaListas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of kursnaListas in body
     */
    @RequestMapping(value = "/kursna-listas",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<KursnaLista> getAllKursnaListas() {
        log.debug("REST request to get all KursnaListas");
        List<KursnaLista> kursnaListas = kursnaListaRepository.findAll();
        return kursnaListas;
    }

    /**
     * GET  /kursna-listas/:id : get the "id" kursnaLista.
     *
     * @param id the id of the kursnaLista to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the kursnaLista, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/kursna-listas/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<KursnaLista> getKursnaLista(@PathVariable Long id) {
        log.debug("REST request to get KursnaLista : {}", id);
        KursnaLista kursnaLista = kursnaListaRepository.findOne(id);
        return Optional.ofNullable(kursnaLista)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /kursna-listas/:id : delete the "id" kursnaLista.
     *
     * @param id the id of the kursnaLista to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/kursna-listas/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteKursnaLista(@PathVariable Long id) {
        log.debug("REST request to delete KursnaLista : {}", id);
        kursnaListaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("kursnaLista", id.toString())).build();
    }

}
