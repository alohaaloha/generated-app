package com.mycompany.myapp.web.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mycompany.myapp.domain.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Cache;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Created by FS-LB on 6/29/2016.
 */
public class RacunPravnogLicaDTO {

    private Long id;

    @NotNull
    @Size(max = 18)
    private String brojRacuna;

    @NotNull
    private ZonedDateTime datumOtvaranja;

    @NotNull
    private Integer vazenje;

    private Banka banka;

    private Klijent vlasnik;

    private Set<DnevnoStanjeRacuna> dnevnoStanjeRacunas = new HashSet<>();

    private Valuta valuta;

    private Set<Ukidanje> ukidanjes = new HashSet<>();

    public RacunPravnogLicaDTO(RacunPravnogLica rpl){
        this.id = rpl.getId();
        this.brojRacuna = rpl.getBrojRacuna();
        this.datumOtvaranja = rpl.getDatumOtvaranja();
        this.vazenje = rpl.getVazenje();
        this.banka = rpl.getBanka();
        this.vlasnik = rpl.getVlasnik();
        this.dnevnoStanjeRacunas = rpl.getDnevnoStanjeRacunas();
        this.valuta = rpl.getValuta();
        this.ukidanjes = rpl.getUkidanjes();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrojRacuna() {
        return brojRacuna;
    }

    public void setBrojRacuna(String brojRacuna) {
        this.brojRacuna = brojRacuna;
    }

    public ZonedDateTime getDatumOtvaranja() {
        return datumOtvaranja;
    }

    public void setDatumOtvaranja(ZonedDateTime datumOtvaranja) {
        this.datumOtvaranja = datumOtvaranja;
    }

    public Integer getVazenje() {
        return vazenje;
    }

    public void setVazenje(Integer vazenje) {
        this.vazenje = vazenje;
    }

    public Banka getBanka() {
        return banka;
    }

    public void setBanka(Banka banka) {
        this.banka = banka;
    }

    public Klijent getVlasnik() {
        return vlasnik;
    }

    public void setVlasnik(Klijent klijent) {
        this.vlasnik = klijent;
    }

    public Set<DnevnoStanjeRacuna> getDnevnoStanjeRacunas() {
        return dnevnoStanjeRacunas;
    }

    public void setDnevnoStanjeRacunas(Set<DnevnoStanjeRacuna> dnevnoStanjeRacunas) {
        this.dnevnoStanjeRacunas = dnevnoStanjeRacunas;
    }

    public Valuta getValuta() {
        return valuta;
    }

    public void setValuta(Valuta valuta) {
        this.valuta = valuta;
    }

    public Set<Ukidanje> getUkidanjes() {
        return ukidanjes;
    }

    public void setUkidanjes(Set<Ukidanje> ukidanjes) {
        this.ukidanjes = ukidanjes;
    }

    @Override
    public String toString() {
        return "RacunPravnogLica{" +
            "id=" + id +
            ", brojRacuna='" + brojRacuna + "'" +
            ", datumOtvaranja='" + datumOtvaranja + "'" +
            ", vazenje='" + vazenje + "'" +
            '}';
    }
}
