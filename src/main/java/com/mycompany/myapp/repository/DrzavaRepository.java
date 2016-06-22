package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Drzava;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Drzava entity.
 */
public interface DrzavaRepository extends JpaRepository<Drzava,Long> {

}
