package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.KursnaLista;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the KursnaLista entity.
 */
public interface KursnaListaRepository extends JpaRepository<KursnaLista,Long> {

}
