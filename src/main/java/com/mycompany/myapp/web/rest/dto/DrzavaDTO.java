package com.mycompany.myapp.web.rest.dto;

import com.mycompany.myapp.domain.Drzava;
import com.mycompany.myapp.domain.NaseljenoMesto;
import com.mycompany.myapp.domain.Valuta;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by FS-LB on 6/29/2016.
 */
public class DrzavaDTO {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    @Size(max = 40)
    private String dr_naziv;

    private Set<NaseljenoMesto> naseljenoMestos = new HashSet<>();

    private Set<Valuta> drzavnaValutas = new HashSet<>();

    public DrzavaDTO(Drzava drzava){
        this.id = drzava.getId();
        this.dr_naziv = drzava.getDr_naziv();
        this.naseljenoMestos = drzava.getNaseljenoMestos();
        this.drzavnaValutas = drzava.getDrzavnaValutas();
    }

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
    public String toString() {
        return "Drzava{" +
            "id=" + id +
            ", dr_naziv='" + dr_naziv + "'" +
            '}';
    }
}
