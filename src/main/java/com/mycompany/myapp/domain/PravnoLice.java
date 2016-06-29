package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PravnoLice.
 */
@Entity
@Table(name = "pravno_lice")
public class PravnoLice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "ime_odgovornog_lica", nullable = false)
    private String imeOdgovornogLica;

    @NotNull
    @Column(name = "prezime_odgovornog_lica", nullable = false)
    private String prezimeOdgovornogLica;

    @Column(name = "jmbg")
    private String jmbg;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImeOdgovornogLica() {
        return imeOdgovornogLica;
    }

    public void setImeOdgovornogLica(String imeOdgovornogLica) {
        this.imeOdgovornogLica = imeOdgovornogLica;
    }

    public String getPrezimeOdgovornogLica() {
        return prezimeOdgovornogLica;
    }

    public void setPrezimeOdgovornogLica(String prezimeOdgovornogLica) {
        this.prezimeOdgovornogLica = prezimeOdgovornogLica;
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PravnoLice pravnoLice = (PravnoLice) o;
        if(pravnoLice.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, pravnoLice.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PravnoLice{" +
            "id=" + id +
            ", imeOdgovornogLica='" + imeOdgovornogLica + "'" +
            ", prezimeOdgovornogLica='" + prezimeOdgovornogLica + "'" +
            ", jmbg='" + jmbg + "'" +
            '}';
    }
}
