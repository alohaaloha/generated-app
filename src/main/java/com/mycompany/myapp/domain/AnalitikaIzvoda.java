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
 * A AnalitikaIzvoda.
 */
@Entity
@Table(name = "analitika_izvoda")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AnalitikaIzvoda implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 256)
    @Column(name = "duznik", length = 256, nullable = false)
    private String duznik;

    @NotNull
    @Size(max = 256)
    @Column(name = "svrha", length = 256, nullable = false)
    private String svrha;

    @NotNull
    @Size(max = 256)
    @Column(name = "poverilac", length = 256, nullable = false)
    private String poverilac;

    @NotNull
    @Column(name = "datum_prijema", nullable = false)
    private ZonedDateTime datumPrijema;

    @NotNull
    @Column(name = "datum_valute", nullable = false)
    private ZonedDateTime datumValute;

    @NotNull
    @Size(max = 18)
    @Column(name = "racun_duznika", length = 18, nullable = false)
    private String racunDuznika;

    @Column(name = "model_zaduzenja")
    private Integer modelZaduzenja;

    @Size(max = 20)
    @Column(name = "poziv_na_broj_zaduzenja", length = 20)
    private String pozivNaBrojZaduzenja;

    @Size(max = 18)
    @Column(name = "racun_poverioca", length = 18)
    private String racunPoverioca;

    @Column(name = "model_odobrenja")
    private Integer modelOdobrenja;

    @Size(max = 20)
    @Column(name = "poziv_na_broj_odobrenja", length = 20)
    private String pozivNaBrojOdobrenja;

    @NotNull
    @Column(name = "is_hitno", nullable = false)
    private Boolean isHitno;

    @NotNull
    @Column(name = "iznos", nullable = false)
    private Double iznos;

    @NotNull
    @Max(value = 9)
    @Column(name = "tip_greske", nullable = false)
    private Integer tipGreske;

    @Size(max = 1)
    @Column(name = "status", length = 1)
    private String status;

    @ManyToOne
    private DnevnoStanjeRacuna dnevnoStanjeRacuna;

    @ManyToOne
    private NaseljenoMesto naseljenoMesto;

    @ManyToOne
    private VrstaPlacanja vrstaPlacanja;

    @ManyToOne
    private Valuta valutaPlacanja;

    @OneToMany(mappedBy = "brojStavke")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<RTGS> porukaMT103S = new HashSet<>();

    @OneToMany(mappedBy = "analitikaIzvoda")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<StavkaKliringa> porukaMT102S = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDuznik() {
        return duznik;
    }

    public void setDuznik(String duznik) {
        this.duznik = duznik;
    }

    public String getSvrha() {
        return svrha;
    }

    public void setSvrha(String svrha) {
        this.svrha = svrha;
    }

    public String getPoverilac() {
        return poverilac;
    }

    public void setPoverilac(String poverilac) {
        this.poverilac = poverilac;
    }

    public ZonedDateTime getDatumPrijema() {
        return datumPrijema;
    }

    public void setDatumPrijema(ZonedDateTime datumPrijema) {
        this.datumPrijema = datumPrijema;
    }

    public ZonedDateTime getDatumValute() {
        return datumValute;
    }

    public void setDatumValute(ZonedDateTime datumValute) {
        this.datumValute = datumValute;
    }

    public String getRacunDuznika() {
        return racunDuznika;
    }

    public void setRacunDuznika(String racunDuznika) {
        this.racunDuznika = racunDuznika;
    }

    public Integer getModelZaduzenja() {
        return modelZaduzenja;
    }

    public void setModelZaduzenja(Integer modelZaduzenja) {
        this.modelZaduzenja = modelZaduzenja;
    }

    public String getPozivNaBrojZaduzenja() {
        return pozivNaBrojZaduzenja;
    }

    public void setPozivNaBrojZaduzenja(String pozivNaBrojZaduzenja) {
        this.pozivNaBrojZaduzenja = pozivNaBrojZaduzenja;
    }

    public String getRacunPoverioca() {
        return racunPoverioca;
    }

    public void setRacunPoverioca(String racunPoverioca) {
        this.racunPoverioca = racunPoverioca;
    }

    public Integer getModelOdobrenja() {
        return modelOdobrenja;
    }

    public void setModelOdobrenja(Integer modelOdobrenja) {
        this.modelOdobrenja = modelOdobrenja;
    }

    public String getPozivNaBrojOdobrenja() {
        return pozivNaBrojOdobrenja;
    }

    public void setPozivNaBrojOdobrenja(String pozivNaBrojOdobrenja) {
        this.pozivNaBrojOdobrenja = pozivNaBrojOdobrenja;
    }

    public Boolean isIsHitno() {
        return isHitno;
    }

    public void setIsHitno(Boolean isHitno) {
        this.isHitno = isHitno;
    }

    public Double getIznos() {
        return iznos;
    }

    public void setIznos(Double iznos) {
        this.iznos = iznos;
    }

    public Integer getTipGreske() {
        return tipGreske;
    }

    public void setTipGreske(Integer tipGreske) {
        this.tipGreske = tipGreske;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DnevnoStanjeRacuna getDnevnoStanjeRacuna() {
        return dnevnoStanjeRacuna;
    }

    public void setDnevnoStanjeRacuna(DnevnoStanjeRacuna dnevnoStanjeRacuna) {
        this.dnevnoStanjeRacuna = dnevnoStanjeRacuna;
    }

    public NaseljenoMesto getNaseljenoMesto() {
        return naseljenoMesto;
    }

    public void setNaseljenoMesto(NaseljenoMesto naseljenoMesto) {
        this.naseljenoMesto = naseljenoMesto;
    }

    public VrstaPlacanja getVrstaPlacanja() {
        return vrstaPlacanja;
    }

    public void setVrstaPlacanja(VrstaPlacanja vrstaPlacanja) {
        this.vrstaPlacanja = vrstaPlacanja;
    }

    public Valuta getValutaPlacanja() {
        return valutaPlacanja;
    }

    public void setValutaPlacanja(Valuta valuta) {
        this.valutaPlacanja = valuta;
    }

    public Set<RTGS> getPorukaMT103S() {
        return porukaMT103S;
    }

    public void setPorukaMT103S(Set<RTGS> rTGS) {
        this.porukaMT103S = rTGS;
    }

    public Set<StavkaKliringa> getPorukaMT102S() {
        return porukaMT102S;
    }

    public void setPorukaMT102S(Set<StavkaKliringa> stavkaKliringas) {
        this.porukaMT102S = stavkaKliringas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AnalitikaIzvoda analitikaIzvoda = (AnalitikaIzvoda) o;
        if(analitikaIzvoda.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, analitikaIzvoda.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AnalitikaIzvoda{" +
            "id=" + id +
            ", duznik='" + duznik + "'" +
            ", svrha='" + svrha + "'" +
            ", poverilac='" + poverilac + "'" +
            ", datumPrijema='" + datumPrijema + "'" +
            ", datumValute='" + datumValute + "'" +
            ", racunDuznika='" + racunDuznika + "'" +
            ", modelZaduzenja='" + modelZaduzenja + "'" +
            ", pozivNaBrojZaduzenja='" + pozivNaBrojZaduzenja + "'" +
            ", racunPoverioca='" + racunPoverioca + "'" +
            ", modelOdobrenja='" + modelOdobrenja + "'" +
            ", pozivNaBrojOdobrenja='" + pozivNaBrojOdobrenja + "'" +
            ", isHitno='" + isHitno + "'" +
            ", iznos='" + iznos + "'" +
            ", tipGreske='" + tipGreske + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
