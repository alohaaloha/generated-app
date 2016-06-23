package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Valuta;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Valuta entity.
 */
public interface ValutaRepository extends JpaRepository<Valuta,Long> {

}
