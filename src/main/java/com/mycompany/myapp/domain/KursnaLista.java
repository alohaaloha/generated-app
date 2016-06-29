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
 * A KursnaLista.
 */
@Entity
@Table(name = "kursna_lista")
public class KursnaLista implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "datum", nullable = false)
    private ZonedDateTime datum;

    @NotNull
    @Column(name = "broj_kursne_liste", nullable = false)
    private Integer brojKursneListe;

    @NotNull
    @Column(name = "datum_primene", nullable = false)
    private ZonedDateTime datumPrimene;

    @ManyToOne
    private Banka banka;

    @OneToMany(mappedBy = "kursnaLista")
    @JsonIgnore
    private Set<KursUValuti> kursUValutis = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDatum() {
        return datum;
    }

    public void setDatum(ZonedDateTime datum) {
        this.datum = datum;
    }

    public Integer getBrojKursneListe() {
        return brojKursneListe;
    }

    public void setBrojKursneListe(Integer brojKursneListe) {
        this.brojKursneListe = brojKursneListe;
    }

    public ZonedDateTime getDatumPrimene() {
        return datumPrimene;
    }

    public void setDatumPrimene(ZonedDateTime datumPrimene) {
        this.datumPrimene = datumPrimene;
    }

    public Banka getBanka() {
        return banka;
    }

    public void setBanka(Banka banka) {
        this.banka = banka;
    }

    public Set<KursUValuti> getKursUValutis() {
        return kursUValutis;
    }

    public void setKursUValutis(Set<KursUValuti> kursUValutis) {
        this.kursUValutis = kursUValutis;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        KursnaLista kursnaLista = (KursnaLista) o;
        if(kursnaLista.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, kursnaLista.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "KursnaLista{" +
            "id=" + id +
            ", datum='" + datum + "'" +
            ", brojKursneListe='" + brojKursneListe + "'" +
            ", datumPrimene='" + datumPrimene + "'" +
            '}';
    }
}
