package com.mycompany.myapp.test;

import com.mycompany.myapp.domain.AnalitikaIzvoda;
import com.mycompany.myapp.domain.RTGS;
import com.mycompany.myapp.domain.Valuta;
import org.w3c.dom.Document;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.net.UnknownHostException;

/**
 * Created by milan on 26.6.2016..
 */
public class TestMain {

    public static void main(String[] args) throws UnknownHostException, JAXBException, ParserConfigurationException {

        RTGS rtgs = new RTGS();
        rtgs.setIdPoruke("NEKI ID");
        rtgs.setSwiftKodBankeDuznika("SWIFT KOD BANKE DUZNIKA");
        rtgs.setObracunskiRacunBankeDuznika("OBRACUNSKI RACUN BANKE DUZNIKA");
        rtgs.setSwiftKodBankePoverioca("SWIFT KOD BANKE POVERIOCA");
        rtgs.setObracunskiRacunBankePoverioca("OBRACUNSKI RACUN BANKE POVERIOCA");

        AnalitikaIzvoda analitikaIzvoda = new AnalitikaIzvoda();
        analitikaIzvoda.setDuznik("Duznik");
        analitikaIzvoda.setSvrha("Svrha placanja");
        analitikaIzvoda.setPoverilac("Poverilac");
        analitikaIzvoda.setRacunDuznika("Racun duznika");
        analitikaIzvoda.setModelZaduzenja(2);
        analitikaIzvoda.setPozivNaBrojZaduzenja("Poziv na broj zaduzenja");
        Valuta v = new Valuta();
        v.setZvanicnaSifra("Sifra valute");
        analitikaIzvoda.setValutaPlacanja(v);
        rtgs.setBrojStavke(analitikaIzvoda);
        Document doc = rtgs.exportToXml();
        System.out.println(doc);
    }
}
