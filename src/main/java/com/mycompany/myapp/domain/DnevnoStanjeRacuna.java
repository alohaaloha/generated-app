package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DnevnoStanjeRacuna.
 */
@Entity
@Table(name = "dnevno_stanje_racuna")
public class DnevnoStanjeRacuna implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "broj_izvoda", nullable = false)
    private Long brojIzvoda;

    @NotNull
    @Column(name = "datum", nullable = false)
    private ZonedDateTime datum;

    @NotNull
    @Column(name = "prethodno_stanje", nullable = false)
    private Double prethodnoStanje;

    @NotNull
    @Column(name = "promet_u_korist", nullable = false)
    private Double prometUKorist;

    @NotNull
    @Column(name = "promet_na_teret", nullable = false)
    private Double prometNaTeret;

    @NotNull
    @Column(name = "novo_stanje", nullable = false)
    private Double novoStanje;

    @OneToMany(mappedBy = "dnevnoStanjeRacuna",fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<AnalitikaIzvoda> analitikaIzvodas = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    private RacunPravnogLica dnevniIzvodBanke;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBrojIzvoda() {
        return brojIzvoda;
    }

    public void setBrojIzvoda(Long brojIzvoda) {
        this.brojIzvoda = brojIzvoda;
    }

    public ZonedDateTime getDatum() {
        return datum;
    }

    public void setDatum(ZonedDateTime datum) {
        this.datum = datum;
    }

    public Double getPrethodnoStanje() {
        return prethodnoStanje;
    }

    public void setPrethodnoStanje(Double prethodnoStanje) {
        this.prethodnoStanje = prethodnoStanje;
    }

    public Double getPrometUKorist() {
        return prometUKorist;
    }

    public void setPrometUKorist(Double prometUKorist) {
        this.prometUKorist = prometUKorist;
    }

    public Double getPrometNaTeret() {
        return prometNaTeret;
    }

    public void setPrometNaTeret(Double prometNaTeret) {
        this.prometNaTeret = prometNaTeret;
    }

    public Double getNovoStanje() {
        return novoStanje;
    }

    public void setNovoStanje(Double novoStanje) {
        this.novoStanje = novoStanje;
    }

    public Set<AnalitikaIzvoda> getAnalitikaIzvodas() {
        return analitikaIzvodas;
    }

    public void setAnalitikaIzvodas(Set<AnalitikaIzvoda> analitikaIzvodas) {
        this.analitikaIzvodas = analitikaIzvodas;
    }

    public RacunPravnogLica getDnevniIzvodBanke() {
        return dnevniIzvodBanke;
    }

    public void setDnevniIzvodBanke(RacunPravnogLica racunPravnogLica) {
        this.dnevniIzvodBanke = racunPravnogLica;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DnevnoStanjeRacuna dnevnoStanjeRacuna = (DnevnoStanjeRacuna) o;
        if(dnevnoStanjeRacuna.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, dnevnoStanjeRacuna.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DnevnoStanjeRacuna{" +
            "id=" + id +
            ", brojIzvoda='" + brojIzvoda + "'" +
            ", datum='" + datum + "'" +
            ", prethodnoStanje='" + prethodnoStanje + "'" +
            ", prometUKorist='" + prometUKorist + "'" +
            ", prometNaTeret='" + prometNaTeret + "'" +
            ", novoStanje='" + novoStanje + "'" +
            '}';
    }
}
