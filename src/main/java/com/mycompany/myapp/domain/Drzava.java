package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Drzava.
 */
@Entity
@Table(name = "drzava")
public class Drzava implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 40)
    @Column(name = "dr_naziv", length = 40, nullable = false)
    private String dr_naziv;

    @OneToMany(mappedBy = "drzava", fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<NaseljenoMesto> naseljenoMestos = new HashSet<>();

    @OneToMany(mappedBy = "drzava", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Valuta> drzavnaValutas = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDr_naziv() {
        return dr_naziv;
    }

    public void setDr_naziv(String dr_naziv) {
        this.dr_naziv = dr_naziv;
    }

    public Set<NaseljenoMesto> getNaseljenoMestos() {
        return naseljenoMestos;
    }

    public void setNaseljenoMestos(Set<NaseljenoMesto> naseljenoMestos) {
        this.naseljenoMestos = naseljenoMestos;
    }

    public Set<Valuta> getDrzavnaValutas() {
        return drzavnaValutas;
    }

    public void setDrzavnaValutas(Set<Valuta> valutas) {
        this.drzavnaValutas = valutas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Drzava drzava = (Drzava) o;
        if(drzava.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, drzava.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Drzava{" +
            "id=" + id +
            ", dr_naziv='" + dr_naziv + "'" +
            '}';
    }
}
