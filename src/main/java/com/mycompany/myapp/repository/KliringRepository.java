package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Kliring;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Kliring entity.
 */
@SuppressWarnings("unused")
public interface KliringRepository extends JpaRepository<Kliring,Long> {

}
