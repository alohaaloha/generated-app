package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.RTGS;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the RTGS entity.
 */
public interface RTGSRepository extends JpaRepository<RTGS,Long> {

}
