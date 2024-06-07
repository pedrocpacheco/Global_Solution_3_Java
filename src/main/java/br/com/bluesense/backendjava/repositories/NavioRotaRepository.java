package br.com.bluesense.backendjava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bluesense.backendjava.entities.navio.NavioRota;

@Repository
public interface NavioRotaRepository extends JpaRepository<NavioRota, Long>{
  
}
