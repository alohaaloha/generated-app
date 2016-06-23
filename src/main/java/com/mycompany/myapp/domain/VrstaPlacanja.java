package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A VrstaPlacanja.
 */
@Entity
@Table(name = "vrsta_placanja")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class VrstaPlacanja implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "oznaka_vrste_placanja", nullable = false)
    private Integer oznakaVrstePlacanja;

    @NotNull
    @Size(max = 120)
    @Column(name = "naziv_vrste_placanja", length = 120, nullable = false)
    private String nazivVrstePlacanja;

    @OneToMany(mappedBy = "vrstaPlacanja")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AnalitikaIzvoda> analitikaIzvodas = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOznakaVrstePlacanja() {
        return oznakaVrstePlacanja;
    }

    public void setOznakaVrstePlacanja(Integer oznakaVrstePlacanja) {
        this.oznakaVrstePlacanja = oznakaVrstePlacanja;
    }

    public String getNazivVrstePlacanja() {
        return nazivVrstePlacanja;
    }

    public void setNazivVrstePlacanja(String nazivVrstePlacanja) {
        this.nazivVrstePlacanja = nazivVrstePlacanja;
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
        VrstaPlacanja vrstaPlacanja = (VrstaPlacanja) o;
        if(vrstaPlacanja.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, vrstaPlacanja.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "VrstaPlacanja{" +
            "id=" + id +
            ", oznakaVrstePlacanja='" + oznakaVrstePlacanja + "'" +
            ", nazivVrstePlacanja='" + nazivVrstePlacanja + "'" +
            '}';
    }
}
