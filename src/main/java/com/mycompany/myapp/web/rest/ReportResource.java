package com.mycompany.myapp.web.rest;

/**
 * Created by FS-LB on 6/29/2016.
 */

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.report.Report;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * REST controller for generating Report
 */
@RestController
@RequestMapping("/api")
public class ReportResource {

    private final Logger log = LoggerFactory.getLogger(ReportResource.class);

    /**
     * GET  GENERATE REPORT
     *
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/generisiizvestaj",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void generateReport(@RequestParam("racun") String racun, @RequestParam("datum1") String datum1, @RequestParam("datum2") String datum2) {
        log.debug("REST request to generate report");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Report report = new Report(racun, sdf.parse(datum1), sdf.parse(datum2));
            report.generateFirstReport(racun, sdf.parse(datum1), sdf.parse(datum2));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * GET  GENERATE REPORT
     *
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/generisibankaizvod",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void generateBankaIzvod(@RequestParam("sifraBanke") String sifraBanke) {
        log.debug("REST request to generate report");
        Report report = new Report();
        report.generateSecondReport(sifraBanke);
    }

    /**
     * GET  GENERATE REPORT
     *
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/generisixmlizvod",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void generateXMLIzvodRacuna(@RequestParam("racun") String racun, @RequestParam("datum1") String datum1, @RequestParam("datum2") String datum2) {
        log.debug("REST request to generate report");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Report report = new Report(racun, sdf.parse(datum1), sdf.parse(datum2));
            report.generateFirstReport(racun, sdf.parse(datum1), sdf.parse(datum2));
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
