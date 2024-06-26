package br.com.bluesense.backendjava.repositories;

import br.com.bluesense.backendjava.entities.navio.Navio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NavioRepository extends JpaRepository<Navio, Long> {
}
