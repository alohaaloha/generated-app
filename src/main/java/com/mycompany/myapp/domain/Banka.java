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
 * A Banka.
 */
@Entity
@Table(name = "banka")
public class Banka implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 3)
    @Column(name = "sifra_banke", length = 3, nullable = false)
    private String sifraBanke;

    @NotNull
    @Size(max = 10)
    @Column(name = "pib", length = 10, nullable = false)
    private String pib;

    @NotNull
    @Size(max = 8)
    @Column(name = "swift_kod", length = 8, nullable = false)
    private String swiftKod;

    @NotNull
    @Size(max = 18)
    @Column(name = "obracunski_racun", length = 18, nullable = false)
    private String obracunskiRacun;

    @NotNull
    @Size(max = 120)
    @Column(name = "naziv", length = 120, nullable = false)
    private String naziv;

    @NotNull
    @Size(max = 120)
    @Column(name = "adresa", length = 120, nullable = false)
    private String adresa;

    @Size(max = 128)
    @Column(name = "email", length = 128)
    private String email;

    @Size(max = 128)
    @Column(name = "web", length = 128)
    private String web;

    @Size(max = 20)
    @Column(name = "telefon", length = 20)
    private String telefon;

    @Size(max = 20)
    @Column(name = "fax", length = 20)
    private String fax;

    @NotNull
    @Column(name = "banka_int", nullable = false)
    private Integer bankaInt;

    @OneToMany(mappedBy = "banka")
    @JsonIgnore
    private Set<KursnaLista> kursPoslovneBankes = new HashSet<>();

    @OneToMany(mappedBy = "banka")
    @JsonIgnore
    private Set<RacunPravnogLica> racunPravnogLicas = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSifraBanke() {
        return sifraBanke;
    }

    public void setSifraBanke(String sifraBanke) {
        this.sifraBanke = sifraBanke;
    }

    public String getPib() {
        return pib;
    }

    public void setPib(String pib) {
        this.pib = pib;
    }

    public String getSwiftKod() {
        return swiftKod;
    }

    public void setSwiftKod(String swiftKod) {
        this.swiftKod = swiftKod;
    }

    public String getObracunskiRacun() {
        return obracunskiRacun;
    }

    public void setObracunskiRacun(String obracunskiRacun) {
        this.obracunskiRacun = obracunskiRacun;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public Integer getBankaInt() {
        return bankaInt;
    }

    public void setBankaInt(Integer bankaInt) {
        this.bankaInt = bankaInt;
    }

    public Set<KursnaLista> getKursPoslovneBankes() {
        return kursPoslovneBankes;
    }

    public void setKursPoslovneBankes(Set<KursnaLista> kursnaListas) {
        this.kursPoslovneBankes = kursnaListas;
    }

    public Set<RacunPravnogLica> getRacunPravnogLicas() {
        return racunPravnogLicas;
    }

    public void setRacunPravnogLicas(Set<RacunPravnogLica> racunPravnogLicas) {
        this.racunPravnogLicas = racunPravnogLicas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Banka banka = (Banka) o;
        if(banka.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, banka.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Banka{" +
            "id=" + id +
            ", sifraBanke='" + sifraBanke + "'" +
            ", pib='" + pib + "'" +
            ", swiftKod='" + swiftKod + "'" +
            ", obracunskiRacun='" + obracunskiRacun + "'" +
            ", naziv='" + naziv + "'" +
            ", adresa='" + adresa + "'" +
            ", email='" + email + "'" +
            ", web='" + web + "'" +
            ", telefon='" + telefon + "'" +
            ", fax='" + fax + "'" +
            ", bankaInt='" + bankaInt + "'" +
            '}';
    }
}
