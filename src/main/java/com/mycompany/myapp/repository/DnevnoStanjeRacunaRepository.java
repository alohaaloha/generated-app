package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.DnevnoStanjeRacuna;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the DnevnoStanjeRacuna entity.
 */
public interface DnevnoStanjeRacunaRepository extends JpaRepository<DnevnoStanjeRacuna,Long> {

}
