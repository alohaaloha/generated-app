package com.mycompany.myapp.report;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by milan on 28.6.2016..
 */
public class Report {

    public  void generateFirstReport(){
        try {
            Map params = new HashMap(1);
            params.put("racun", "001900000000000101" );
            params.put("pocetak", new SimpleDateFormat("yyyy-MM-dd").parse("2008-01-01"));
            params.put("kraj", new SimpleDateFormat("yyyy-MM-dd").parse("2017-01-01"));


            String kobaja = "jdbc:mysql://localhost:3306/pinf_pro?"+"user=root&password=admin&useSSL=false";
            Connection connection = null;
            connection = DriverManager.getConnection(kobaja);
            JasperPrint jp = JasperFillManager.fillReport(
                new FileInputStream(new File("./jasper/izvodiPoKlijentu.jasper")),
                params, connection);
            JasperViewer.viewReport(jp, false);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
