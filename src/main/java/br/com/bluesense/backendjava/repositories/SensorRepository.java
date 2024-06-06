package br.com.bluesense.backendjava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bluesense.backendjava.entities.sensor.Sensor;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long>{
  
}
