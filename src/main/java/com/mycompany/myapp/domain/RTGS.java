package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.*;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Objects;

/**
 * A RTGS.
 */
@XmlRootElement(name="RTGS")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "rtgs")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RTGS implements Serializable {

    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @XmlAttribute(name="IDPoruke")
    @NotNull
    @Size(max = 50)
    @Column(name = "id_poruke", length = 50, nullable = false)
    private String idPoruke;

    @XmlElement
    @NotNull
    @Size(max = 8)
    @Column(name = "swift_kod_banke_duznika", length = 8, nullable = false)
    private String swiftKodBankeDuznika;

    @XmlElement
    @NotNull
    @Size(max = 18)
    @Column(name = "obracunski_racun_banke_duznika", length = 18, nullable = false)
    private String obracunskiRacunBankeDuznika;

    @XmlElement
    @NotNull
    @Size(max = 8)
    @Column(name = "swift_kod_banke_poverioca", length = 8, nullable = false)
    private String swiftKodBankePoverioca;

    @XmlElement
    @NotNull
    @Size(max = 18)
    @Column(name = "obracunski_racun_banke_poverioca", length = 18, nullable = false)
    private String obracunskiRacunBankePoverioca;

    @XmlElement
    @ManyToOne
    private AnalitikaIzvoda brojStavke;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdPoruke() {
        return idPoruke;
    }

    public void setIdPoruke(String idPoruke) {
        this.idPoruke = idPoruke;
    }

    public String getSwiftKodBankeDuznika() {
        return swiftKodBankeDuznika;
    }

    public void setSwiftKodBankeDuznika(String swiftKodBankeDuznika) {
        this.swiftKodBankeDuznika = swiftKodBankeDuznika;
    }

    public String getObracunskiRacunBankeDuznika() {
        return obracunskiRacunBankeDuznika;
    }

    public void setObracunskiRacunBankeDuznika(String obracunskiRacunBankeDuznika) {
        this.obracunskiRacunBankeDuznika = obracunskiRacunBankeDuznika;
    }

    public String getSwiftKodBankePoverioca() {
        return swiftKodBankePoverioca;
    }

    public void setSwiftKodBankePoverioca(String swiftKodBankePoverioca) {
        this.swiftKodBankePoverioca = swiftKodBankePoverioca;
    }

    public String getObracunskiRacunBankePoverioca() {
        return obracunskiRacunBankePoverioca;
    }

    public void setObracunskiRacunBankePoverioca(String obracunskiRacunBankePoverioca) {
        this.obracunskiRacunBankePoverioca = obracunskiRacunBankePoverioca;
    }

    public AnalitikaIzvoda getBrojStavke() {
        return brojStavke;
    }

    public void setBrojStavke(AnalitikaIzvoda analitikaIzvoda) {
        this.brojStavke = analitikaIzvoda;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RTGS rTGS = (RTGS) o;
        if(rTGS.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, rTGS.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RTGS{" +
            "id=" + id +
            ", idPoruke='" + idPoruke + "'" +
            ", swiftKodBankeDuznika='" + swiftKodBankeDuznika + "'" +
            ", obracunskiRacunBankeDuznika='" + obracunskiRacunBankeDuznika + "'" +
            ", swiftKodBankePoverioca='" + swiftKodBankePoverioca + "'" +
            ", obracunskiRacunBankePoverioca='" + obracunskiRacunBankePoverioca + "'" +
            '}';
    }

    public boolean exportToXml(OutputStream outputStream){
        boolean ret = false;
        try{
            JAXBContext jaxbContext = JAXBContext.newInstance(RTGS.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(this, System.out);
            ret = true;
        } catch (Exception e){

        }
        return ret;
    }
}
