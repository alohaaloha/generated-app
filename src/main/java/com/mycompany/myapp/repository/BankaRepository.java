package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Banka;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Banka entity.
 */
@SuppressWarnings("unused")
public interface BankaRepository extends JpaRepository<Banka,Long> {

}
