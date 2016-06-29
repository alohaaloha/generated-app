package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
 * A NaseljenoMesto.
 */
@Entity
@Table(name = "naseljeno_mesto")
public class NaseljenoMesto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 60)
    @Column(name = "nm_naziv", length = 60, nullable = false)
    private String nm_naziv;

    @NotNull
    @Size(max = 12)
    @Column(name = "nm_pttoznaka", length = 12, nullable = false)
    private String nm_pttoznaka;

    @ManyToOne(fetch = FetchType.EAGER)
    private Drzava drzava;

    @OneToMany(mappedBy = "naseljenoMesto")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AnalitikaIzvoda> analitikaIzvodas = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNm_naziv() {
        return nm_naziv;
    }

    public void setNm_naziv(String nm_naziv) {
        this.nm_naziv = nm_naziv;
    }

    public String getNm_pttoznaka() {
        return nm_pttoznaka;
    }

    public void setNm_pttoznaka(String nm_pttoznaka) {
        this.nm_pttoznaka = nm_pttoznaka;
    }

    public Drzava getDrzava() {
        return drzava;
    }

    public void setDrzava(Drzava drzava) {
        this.drzava = drzava;
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
        NaseljenoMesto naseljenoMesto = (NaseljenoMesto) o;
        if(naseljenoMesto.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, naseljenoMesto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "NaseljenoMesto{" +
            "id=" + id +
            ", nm_naziv='" + nm_naziv + "'" +
            ", nm_pttoznaka='" + nm_pttoznaka + "'" +
            '}';
    }
}
