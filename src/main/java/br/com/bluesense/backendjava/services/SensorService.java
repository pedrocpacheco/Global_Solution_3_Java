package br.com.bluesense.backendjava.services;

import br.com.bluesense.backendjava.entities.sensor.Sensor;
import br.com.bluesense.backendjava.repositories.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SensorService {

    @Autowired
    private SensorRepository sensorRepository;

    public List<Sensor> getAllSensores() {
        return sensorRepository.findAll();
    }

    public Optional<Sensor> getSensorById(Long id) {
        return sensorRepository.findById(id);
    }

    public Sensor createSensor(Sensor sensor) {
        return sensorRepository.save(sensor);
    }

    public Optional<Sensor> updateSensor(Long id, Sensor sensorDetails) {
        return sensorRepository.findById(id).map(sensor -> {
            sensor.setModelo(sensorDetails.getModelo());
            sensor.setMarca(sensorDetails.getMarca());
            sensor.setTipoSensor(sensorDetails.getTipoSensor());
            return sensorRepository.save(sensor);
        });
    }

    public boolean deleteSensor(Long id) {
        return sensorRepository.findById(id).map(sensor -> {
            sensorRepository.delete(sensor);
            return true;
        }).orElse(false);
    }
}

