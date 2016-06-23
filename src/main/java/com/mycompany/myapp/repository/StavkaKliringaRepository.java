package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.StavkaKliringa;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the StavkaKliringa entity.
 */
public interface StavkaKliringaRepository extends JpaRepository<StavkaKliringa,Long> {

}
