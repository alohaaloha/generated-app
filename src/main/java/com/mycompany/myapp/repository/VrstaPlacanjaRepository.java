package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.VrstaPlacanja;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the VrstaPlacanja entity.
 */
public interface VrstaPlacanjaRepository extends JpaRepository<VrstaPlacanja,Long> {

}
