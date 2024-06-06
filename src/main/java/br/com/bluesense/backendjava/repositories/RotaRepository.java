package br.com.bluesense.backendjava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bluesense.backendjava.entities.Rota;

@Repository
public interface RotaRepository extends JpaRepository<Rota, Long>{
  
}
