package com.mycompany.myapp.web.rest;

/**
 * Created by FS-LB on 6/29/2016.
 */

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.AnalitikaIzvoda;
import com.mycompany.myapp.domain.DnevnoStanjeRacuna;
import com.mycompany.myapp.domain.RacunPravnogLica;
import com.mycompany.myapp.report.Report;
import com.mycompany.myapp.repository.AnalitikaIzvodaRepository;
import com.mycompany.myapp.repository.DnevnoStanjeRacunaRepository;
import com.mycompany.myapp.repository.RacunPravnogLicaRepository;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * REST controller for generating Report
 */
@RestController
@RequestMapping("/api")
public class ReportResource {

    private final Logger log = LoggerFactory.getLogger(ReportResource.class);

    @Inject
    AnalitikaIzvodaRepository analitikaIzvodaRepository;

    @Inject
    RacunPravnogLicaRepository racunPravnogLicaRepository;

    @Inject
    DnevnoStanjeRacunaRepository dnevnoStanjeRacunaRepository;


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





    @RequestMapping(value = "/generisiizvestajmitric/{racID}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public HttpStatus generateReportXML(@PathVariable String racID) {
        log.debug("REST request to mitricev izvestaj");

        Report report=new Report();
        ArrayList<AnalitikaIzvoda> analitike = new ArrayList<>();
        RacunPravnogLica racun = racunPravnogLicaRepository.findOne(Long.parseLong(racID));
        System.out.println("ZA RACUN OD: "+ racun.getVlasnik().getIme());
        ArrayList<DnevnoStanjeRacuna> dnevnoStanjeRacunas = (ArrayList<DnevnoStanjeRacuna>) dnevnoStanjeRacunaRepository.findAll();
        System.out.println("BROJ DNEVNIH STANJA UKUPNO: " +dnevnoStanjeRacunas.size());
        for(DnevnoStanjeRacuna dnevnoStanjeRacuna:dnevnoStanjeRacunas){
            if(dnevnoStanjeRacuna.getDnevniIzvodBanke().equals(racun)){
                //moja.add(dnevnoStanjeRacuna);
               analitike.addAll(dnevnoStanjeRacuna.getAnalitikaIzvodas());
                System.out.println("PRONADJENO ANALITIKA: " +dnevnoStanjeRacuna.getAnalitikaIzvodas().size());
            }
        }

        System.out.println("UKUPNO MOJI ANALITIKA: " +analitike.size());

        try {
            report.generateFirstReportXml(racID, new FileOutputStream(new File("output/tmp.xml")), analitike);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("SVE KUL RADI LUDILO ZJUU");
        return HttpStatus.OK;
    }





    @RequestMapping(value = "/dwn1",
        method = RequestMethod.GET,
        produces = "application/octet-stream")
    public ResponseEntity<InputStreamResource> dwn1()
        throws IOException {

        ClassPathResource pdfFile = new ClassPathResource("tmp.xml");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity
            .ok()
            .headers(headers)
            .contentLength(pdfFile.contentLength())
            .contentType(MediaType.parseMediaType("application/octet-stream"))
            .body(new InputStreamResource(pdfFile.getInputStream()));
    }





}
