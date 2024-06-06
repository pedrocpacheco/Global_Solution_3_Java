package br.com.bluesense.backendjava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bluesense.backendjava.entities.Leitura;

@Repository
public interface LeituraRepository extends JpaRepository<Leitura, Long>{
  
}
