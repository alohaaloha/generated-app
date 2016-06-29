package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A StavkaKliringa.
 */
@XmlRootElement(name="StavkaKliringa")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "stavka_kliringa")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class StavkaKliringa implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlTransient
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @XmlTransient
    @ManyToOne
    private Kliring kliring;

    @ManyToOne
    @XmlElement
    private AnalitikaIzvoda analitikaIzvoda;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Kliring getKliring() {
        return kliring;
    }

    public void setKliring(Kliring kliring) {
        this.kliring = kliring;
    }

    public AnalitikaIzvoda getAnalitikaIzvoda() {
        return analitikaIzvoda;
    }

    public void setAnalitikaIzvoda(AnalitikaIzvoda analitikaIzvoda) {
        this.analitikaIzvoda = analitikaIzvoda;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StavkaKliringa stavkaKliringa = (StavkaKliringa) o;
        if(stavkaKliringa.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, stavkaKliringa.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "StavkaKliringa{" +
            "id=" + id +
            '}';
    }
}
