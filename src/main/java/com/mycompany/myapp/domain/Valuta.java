package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Valuta.
 */
@XmlRootElement(name="Valuta")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "valuta")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Valuta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @XmlAttribute
    @NotNull
    @Size(max = 3)
    @Column(name = "zvanicna_sifra", length = 3, nullable = false)
    private String zvanicnaSifra;

    @NotNull
    @Size(max = 30)
    @Column(name = "naziv_valute", length = 30, nullable = false)
    private String nazivValute;

    @NotNull
    @Column(name = "domicilna", nullable = false)
    private Integer domicilna;

    @ManyToOne
    private Drzava drzava;

    @OneToMany(mappedBy = "valuta")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Kliring> klirings = new HashSet<>();

    @OneToMany(mappedBy = "osnovnaValuta")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<KursUValuti> osnovnaValutas = new HashSet<>();

    @OneToMany(mappedBy = "premaValuti")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<KursUValuti> premaValutis = new HashSet<>();

    @OneToMany(mappedBy = "valuta")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<RacunPravnogLica> racunPravnogLicas = new HashSet<>();

    @OneToMany(mappedBy = "valutaPlacanja")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AnalitikaIzvoda> analitikaIzvodas = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getZvanicnaSifra() {
        return zvanicnaSifra;
    }

    public void setZvanicnaSifra(String zvanicnaSifra) {
        this.zvanicnaSifra = zvanicnaSifra;
    }

    public String getNazivValute() {
        return nazivValute;
    }

    public void setNazivValute(String nazivValute) {
        this.nazivValute = nazivValute;
    }

    public Integer getDomicilna() {
        return domicilna;
    }

    public void setDomicilna(Integer domicilna) {
        this.domicilna = domicilna;
    }

    public Drzava getDrzava() {
        return drzava;
    }

    public void setDrzava(Drzava drzava) {
        this.drzava = drzava;
    }

    public Set<Kliring> getKlirings() {
        return klirings;
    }

    public void setKlirings(Set<Kliring> klirings) {
        this.klirings = klirings;
    }

    public Set<KursUValuti> getOsnovnaValutas() {
        return osnovnaValutas;
    }

    public void setOsnovnaValutas(Set<KursUValuti> kursUValutis) {
        this.osnovnaValutas = kursUValutis;
    }

    public Set<KursUValuti> getPremaValutis() {
        return premaValutis;
    }

    public void setPremaValutis(Set<KursUValuti> kursUValutis) {
        this.premaValutis = kursUValutis;
    }

    public Set<RacunPravnogLica> getRacunPravnogLicas() {
        return racunPravnogLicas;
    }

    public void setRacunPravnogLicas(Set<RacunPravnogLica> racunPravnogLicas) {
        this.racunPravnogLicas = racunPravnogLicas;
    }

    public Set<AnalitikaIzvoda> getAnalitikaIzvodas() {
        return analitikaIzvodas;
    }

    public void setAnalitikaIzvodas(Set<AnalitikaIzvoda> analitikaIzvodas) {
        this.analitikaIzvodas = analitikaIzvodas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Valuta valuta = (Valuta) o;
        if(valuta.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, valuta.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Valuta{" +
            "id=" + id +
            ", zvanicnaSifra='" + zvanicnaSifra + "'" +
            ", nazivValute='" + nazivValute + "'" +
            ", domicilna='" + domicilna + "'" +
            '}';
    }
}
