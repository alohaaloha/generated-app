package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Ukidanje.
 */
@Entity
@Table(name = "ukidanje")
public class Ukidanje implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "datum_ukidanja", nullable = false)
    private ZonedDateTime datumUkidanja;

    @NotNull
    @Size(max = 20)
    @Column(name = "prenos_na_racun", length = 20, nullable = false)
    private String prenosNaRacun;

    @ManyToOne
    private RacunPravnogLica racunPravnogLica;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDatumUkidanja() {
        return datumUkidanja;
    }

    public void setDatumUkidanja(ZonedDateTime datumUkidanja) {
        this.datumUkidanja = datumUkidanja;
    }

    public String getPrenosNaRacun() {
        return prenosNaRacun;
    }

    public void setPrenosNaRacun(String prenosNaRacun) {
        this.prenosNaRacun = prenosNaRacun;
    }

    public RacunPravnogLica getRacunPravnogLica() {
        return racunPravnogLica;
    }

    public void setRacunPravnogLica(RacunPravnogLica racunPravnogLica) {
        this.racunPravnogLica = racunPravnogLica;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ukidanje ukidanje = (Ukidanje) o;
        if(ukidanje.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, ukidanje.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Ukidanje{" +
            "id=" + id +
            ", datumUkidanja='" + datumUkidanja + "'" +
            ", prenosNaRacun='" + prenosNaRacun + "'" +
            '}';
    }
}
