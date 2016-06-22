package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.NaseljenoMesto;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the NaseljenoMesto entity.
 */
public interface NaseljenoMestoRepository extends JpaRepository<NaseljenoMesto,Long> {

}
