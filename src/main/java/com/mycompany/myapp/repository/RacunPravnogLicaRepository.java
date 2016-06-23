package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.RacunPravnogLica;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the RacunPravnogLica entity.
 */
public interface RacunPravnogLicaRepository extends JpaRepository<RacunPravnogLica,Long> {

}
