package com.mycompany.myapp.report;

import com.mycompany.myapp.domain.AnalitikaIzvoda;
import com.mycompany.myapp.repository.AnalitikaIzvodaRepository;
import net.sf.jasperreports.engine.*;

import javax.inject.Inject;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by milan on 28.6.2016..
 */
public class Report {

    @Inject
    private AnalitikaIzvodaRepository analitikaIzvodaRepository;

    public  void generateFirstReport(String racun, Date pocetak, Date kraj){
        JasperPrint jp = null;
        try {
            Map params = new HashMap(1);
            params.put("racun", racun );
            params.put("pocetak", pocetak);
            params.put("kraj", kraj);


            String kobaja = "jdbc:mysql://localhost:3306/pinf_pro?"+"user=root&password=basepass&useSSL=false";
            Connection connection = null;

            BufferedInputStream bufferedInputStream = new BufferedInputStream( new FileInputStream("./jasper/izvodiPoKlijentu.jasper"));

            JasperReport jasperReport = JasperCompileManager.compileReport("./jasper/izvodiPoKlijentu.jrxml");
            connection = DriverManager.getConnection(kobaja);
            jp = JasperFillManager.fillReport(jasperReport
                ,
                params, connection);
            JasperExportManager.exportReportToHtmlFile(jp,"JasperReport1.html");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void generateSecondReport(String sifraBanke){
        JasperPrint jp = null;
        try{
            Map params = new HashMap(1);
            params.put("sifraBanke",sifraBanke);


            String kobaja = "jdbc:mysql://localhost:3306/pinf_pro?"+"user=root&password=admin&useSSL=false";
            Connection connection = null;
            JasperReport jasperReport = JasperCompileManager.compileReport("./jasper/racuniBanke.jrxml");
            connection = DriverManager.getConnection(kobaja);
            jp = JasperFillManager.fillReport(jasperReport
                ,
                params, connection);
            JasperExportManager.exportReportToHtmlFile(jp,"JasperReport2.html");
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void generateFirstReportXml(String racun, OutputStream outputStream, ArrayList<AnalitikaIzvoda> analitike){
        ArrayList<AnalitikaIzvoda> output = new ArrayList<>();

        for(AnalitikaIzvoda input : analitike){
            if ((input.getRacunDuznika() != null &&
                input.getRacunDuznika().equals(racun))
                || (input.getRacunPoverioca() != null && input.getRacunPoverioca().equals(racun)) ){
                output.add(input);
            }
        }

        for(AnalitikaIzvoda analitika: output){
            analitika.exportToXml(outputStream);
        }
    }

}
