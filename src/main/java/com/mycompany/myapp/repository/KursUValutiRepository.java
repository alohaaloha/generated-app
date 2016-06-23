package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.KursUValuti;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the KursUValuti entity.
 */
public interface KursUValutiRepository extends JpaRepository<KursUValuti,Long> {

}
