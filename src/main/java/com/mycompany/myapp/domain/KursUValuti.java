package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A KursUValuti.
 */
@Entity
@Table(name = "kurs_u_valuti")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class KursUValuti implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "redni_broj", nullable = false)
    private Integer redniBroj;

    @NotNull
    @Column(name = "kurs_kupovni", nullable = false)
    private Double kursKupovni;

    @NotNull
    @Column(name = "kurs_srednji", nullable = false)
    private Double kursSrednji;

    @NotNull
    @Column(name = "kurs_prodajni", nullable = false)
    private Double kursProdajni;

    @ManyToOne
    private Valuta osnovnaValuta;

    @ManyToOne
    private Valuta premaValuti;

    @ManyToOne
    private KursnaLista kursnaLista;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRedniBroj() {
        return redniBroj;
    }

    public void setRedniBroj(Integer redniBroj) {
        this.redniBroj = redniBroj;
    }

    public Double getKursKupovni() {
        return kursKupovni;
    }

    public void setKursKupovni(Double kursKupovni) {
        this.kursKupovni = kursKupovni;
    }

    public Double getKursSrednji() {
        return kursSrednji;
    }

    public void setKursSrednji(Double kursSrednji) {
        this.kursSrednji = kursSrednji;
    }

    public Double getKursProdajni() {
        return kursProdajni;
    }

    public void setKursProdajni(Double kursProdajni) {
        this.kursProdajni = kursProdajni;
    }

    public Valuta getOsnovnaValuta() {
        return osnovnaValuta;
    }

    public void setOsnovnaValuta(Valuta valuta) {
        this.osnovnaValuta = valuta;
    }

    public Valuta getPremaValuti() {
        return premaValuti;
    }

    public void setPremaValuti(Valuta valuta) {
        this.premaValuti = valuta;
    }

    public KursnaLista getKursnaLista() {
        return kursnaLista;
    }

    public void setKursnaLista(KursnaLista kursnaLista) {
        this.kursnaLista = kursnaLista;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        KursUValuti kursUValuti = (KursUValuti) o;
        if(kursUValuti.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, kursUValuti.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "KursUValuti{" +
            "id=" + id +
            ", redniBroj='" + redniBroj + "'" +
            ", kursKupovni='" + kursKupovni + "'" +
            ", kursSrednji='" + kursSrednji + "'" +
            ", kursProdajni='" + kursProdajni + "'" +
            '}';
    }
}
