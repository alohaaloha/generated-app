package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Ukidanje;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Ukidanje entity.
 */
public interface UkidanjeRepository extends JpaRepository<Ukidanje,Long> {

}
