package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A RacunPravnogLica.
 */
@Entity
@Table(name = "racun_pravnog_lica")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RacunPravnogLica implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 18)
    @Column(name = "broj_racuna", length = 18, nullable = false)
    private String brojRacuna;

    @NotNull
    @Column(name = "datum_otvaranja", nullable = false)
    private ZonedDateTime datumOtvaranja;

    @NotNull
    @Column(name = "vazenje", nullable = false)
    private Integer vazenje;

    @ManyToOne
    private Banka banka;

    @ManyToOne
    private Klijent vlasnik;

    @OneToMany(mappedBy = "dnevniIzvodBanke")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DnevnoStanjeRacuna> dnevnoStanjeRacunas = new HashSet<>();

    @ManyToOne
    private Valuta valuta;

    @OneToMany(mappedBy = "racunPravnogLica")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Ukidanje> ukidanjes = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrojRacuna() {
        return brojRacuna;
    }

    public void setBrojRacuna(String brojRacuna) {
        this.brojRacuna = brojRacuna;
    }

    public ZonedDateTime getDatumOtvaranja() {
        return datumOtvaranja;
    }

    public void setDatumOtvaranja(ZonedDateTime datumOtvaranja) {
        this.datumOtvaranja = datumOtvaranja;
    }

    public Integer getVazenje() {
        return vazenje;
    }

    public void setVazenje(Integer vazenje) {
        this.vazenje = vazenje;
    }

    public Banka getBanka() {
        return banka;
    }

    public void setBanka(Banka banka) {
        this.banka = banka;
    }

    public Klijent getVlasnik() {
        return vlasnik;
    }

    public void setVlasnik(Klijent klijent) {
        this.vlasnik = klijent;
    }

    public Set<DnevnoStanjeRacuna> getDnevnoStanjeRacunas() {
        return dnevnoStanjeRacunas;
    }

    public void setDnevnoStanjeRacunas(Set<DnevnoStanjeRacuna> dnevnoStanjeRacunas) {
        this.dnevnoStanjeRacunas = dnevnoStanjeRacunas;
    }

    public Valuta getValuta() {
        return valuta;
    }

    public void setValuta(Valuta valuta) {
        this.valuta = valuta;
    }

    public Set<Ukidanje> getUkidanjes() {
        return ukidanjes;
    }

    public void setUkidanjes(Set<Ukidanje> ukidanjes) {
        this.ukidanjes = ukidanjes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RacunPravnogLica racunPravnogLica = (RacunPravnogLica) o;
        if(racunPravnogLica.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, racunPravnogLica.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RacunPravnogLica{" +
            "id=" + id +
            ", brojRacuna='" + brojRacuna + "'" +
            ", datumOtvaranja='" + datumOtvaranja + "'" +
            ", vazenje='" + vazenje + "'" +
            '}';
    }
}
