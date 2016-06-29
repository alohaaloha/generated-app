package com.mycompany.myapp.web.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mycompany.myapp.domain.Klijent;
import com.mycompany.myapp.domain.NaseljenoMesto;
import com.mycompany.myapp.domain.PravnoLice;
import com.mycompany.myapp.domain.RacunPravnogLica;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class KlijentDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(max = 120)
    @Column(name = "naziv_pravnog_lica", length = 120)
    private String nazivPravnogLica;

    @NotNull
    @Size(max = 30)
    @Column(name = "ime", length = 30, nullable = false)
    private String ime;

    @NotNull
    @Size(max = 30)
    @Column(name = "prezime", length = 30, nullable = false)
    private String prezime;

    @NotNull
    @Size(min = 13, max = 13)
    @Column(name = "jmbg", length = 13, nullable = false)
    private String jmbg;

    @NotNull
    @Size(max = 50)
    @Column(name = "adresa", length = 30, nullable = false)
    private String adresa;

    @Size(max = 20)
    @Column(name = "telefon", length = 20)
    private String telefon;

    @Size(max = 20)
    @Column(name = "fax", length = 20)
    private String fax;

    @Size(max = 50)
    @Column(name = "email", length = 20)
    private String email;

    @Size(max = 9)
    @Column(name = "pib", length = 9, nullable = false)
    private String pib;

    @Column(name = "sifra_delatnosti")
    private Integer sifraDelatnosti;


    private Set<RacunPravnogLica> racunis = new HashSet<>();

    @ManyToOne
    private PravnoLice pravnoLice;

    @ManyToOne
    private NaseljenoMesto naseljenoMesto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNazivPravnogLica() {
        return nazivPravnogLica;
    }

    public void setNazivPravnogLica(String nazivPravnogLica) {
        this.nazivPravnogLica = nazivPravnogLica;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPib() {
        return pib;
    }

    public void setPib(String pib) {
        this.pib = pib;
    }

    public Integer getSifraDelatnosti() {
        return sifraDelatnosti;
    }

    public void setSifraDelatnosti(Integer sifraDelatnosti) {
        this.sifraDelatnosti = sifraDelatnosti;
    }

    public Set<RacunPravnogLica> getRacunis() {
        return racunis;
    }

    public void setRacunis(Set<RacunPravnogLica> racunPravnogLicas) {
        this.racunis = racunPravnogLicas;
    }

    public PravnoLice getPravnoLice() {
        return pravnoLice;
    }

    public void setPravnoLice(PravnoLice pravnoLice) {
        this.pravnoLice = pravnoLice;
    }

    public NaseljenoMesto getNaseljenoMesto() {
        return naseljenoMesto;
    }

    public void setNaseljenoMesto(NaseljenoMesto naseljenoMesto) {
        this.naseljenoMesto = naseljenoMesto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        KlijentDTO klijent = (KlijentDTO) o;
        if(klijent.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, klijent.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Klijent{" +
            "id=" + id +
            ", nazivPravnogLica='" + nazivPravnogLica + "'" +
            ", ime='" + ime + "'" +
            ", prezime='" + prezime + "'" +
            ", jmbg='" + jmbg + "'" +
            ", adresa='" + adresa + "'" +
            ", telefon='" + telefon + "'" +
            ", fax='" + fax + "'" +
            ", email='" + email + "'" +
            ", pib='" + pib + "'" +
            ", sifraDelatnosti='" + sifraDelatnosti + "'" +
            '}';
    }


    public KlijentDTO(Klijent k) {
        this.id = k.getId();
        this.nazivPravnogLica = k.getNazivPravnogLica();
        this.ime = k.getIme();
        this.prezime = k.getPrezime();
        this.jmbg = k.getJmbg();
        this.adresa = k.getAdresa();
        this.telefon = k.getTelefon();
        this.fax = k.getFax();
        this.email = k.getEmail();
        this.pib = k.getPib();
        this.sifraDelatnosti = k.getSifraDelatnosti();
        this.racunis = k.getRacunis();
        this.pravnoLice = k.getPravnoLice();
        this.naseljenoMesto = k.getNaseljenoMesto();
    }
}
