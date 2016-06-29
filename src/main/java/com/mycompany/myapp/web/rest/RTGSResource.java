package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.RTGS;
import com.mycompany.myapp.repository.RTGSRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import org.bouncycastle.ocsp.Req;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing RTGS.
 */
@RestController
@RequestMapping("/api")
public class RTGSResource {

    private final Logger log = LoggerFactory.getLogger(RTGSResource.class);

    @Inject
    private RTGSRepository rTGSRepository;



    @RequestMapping(value = "/generatexml/{id}",method = RequestMethod.GET,
                 produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @Timed
    public void returntGeneratedXml(HttpServletRequest request,
                                    HttpServletResponse response, @PathVariable String id) {
        RTGS rtgs = rTGSRepository.findOne(Long.parseLong(id));
        try {
            rtgs.exportToXml(new FileOutputStream(new File("tmp.xml"))); // ovde export finish
        } catch (FileNotFoundException e) {
            log.info("RTGS Resource EXEPTION WITH CONVERTING TO XML");
        }
        response.setContentType("applicaton/octet-stream"); //AJD PROBACEMO SA OVIM SAD IZ POSTMENTA MOZE???
        response.setHeader("Content-Disposition","attachment; filename=someFileName.xml");
        try (InputStream is = new FileInputStream(new File("tmp.xml"))) {
            org.apache.commons.io.IOUtils.copy(is, response.getOutputStream()); /// KOJI MI JE TO ENTITET I KOJI JE NJEGOV ID????
            response.flushBuffer();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        /**
     * POST  /r-tgs : Create a new rTGS.
     *
     * @param rTGS the rTGS to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rTGS, or with status 400 (Bad Request) if the rTGS has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/r-tgs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RTGS> createRTGS(@Valid @RequestBody RTGS rTGS) throws URISyntaxException {
        log.debug("REST request to save RTGS : {}", rTGS);
        if (rTGS.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("rTGS", "idexists", "A new rTGS cannot already have an ID")).body(null);
        }
        RTGS result = rTGSRepository.save(rTGS);
        return ResponseEntity.created(new URI("/api/r-tgs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("rTGS", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /r-tgs : Updates an existing rTGS.
     *
     * @param rTGS the rTGS to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rTGS,
     * or with status 400 (Bad Request) if the rTGS is not valid,
     * or with status 500 (Internal Server Error) if the rTGS couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/r-tgs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RTGS> updateRTGS(@Valid @RequestBody RTGS rTGS) throws URISyntaxException {
        log.debug("REST request to update RTGS : {}", rTGS);
        if (rTGS.getId() == null) {
            return createRTGS(rTGS);
        }
        RTGS result = rTGSRepository.save(rTGS);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("rTGS", rTGS.getId().toString()))
            .body(result);
    }

    /**
     * GET  /r-tgs : get all the rTGS.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rTGS in body
     */
    @RequestMapping(value = "/r-tgs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<RTGS> getAllRTGS() {
        log.debug("REST request to get all RTGS");
        List<RTGS> rTGS = rTGSRepository.findAll();
        return rTGS;
    }

    /**
     * GET  /r-tgs/:id : get the "id" rTGS.
     *
     * @param id the id of the rTGS to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rTGS, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/r-tgs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RTGS> getRTGS(@PathVariable Long id) {
        log.debug("REST request to get RTGS : {}", id);
        RTGS rTGS = rTGSRepository.findOne(id);
        return Optional.ofNullable(rTGS)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /r-tgs/:id : delete the "id" rTGS.
     *
     * @param id the id of the rTGS to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/r-tgs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteRTGS(@PathVariable Long id) {
        log.debug("REST request to delete RTGS : {}", id);
        rTGSRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("rTGS", id.toString())).build();
    }

}
