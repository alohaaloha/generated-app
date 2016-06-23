package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Klijent;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Klijent entity.
 */
public interface KlijentRepository extends JpaRepository<Klijent,Long> {

}
