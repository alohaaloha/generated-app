package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.AnalitikaIzvoda;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the AnalitikaIzvoda entity.
 */
public interface AnalitikaIzvodaRepository extends JpaRepository<AnalitikaIzvoda,Long> {

}
