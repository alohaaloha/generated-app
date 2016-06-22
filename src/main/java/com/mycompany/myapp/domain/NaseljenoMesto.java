package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A NaseljenoMesto.
 */
@Entity
@Table(name = "naseljeno_mesto")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
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

    @ManyToOne
    private Drzava drzava;

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
