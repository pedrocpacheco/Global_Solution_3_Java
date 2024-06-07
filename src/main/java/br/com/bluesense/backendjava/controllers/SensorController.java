package br.com.bluesense.backendjava.controllers;

import br.com.bluesense.backendjava.entities.sensor.Sensor;
import br.com.bluesense.backendjava.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.Optional;

@RestController
@RequestMapping("/sensores")
@Api(tags = "Sensores")
public class SensorController {

    @Autowired
    private SensorService sensorService;

    @GetMapping
    @ApiOperation(value = "Obter todos os sensores", response = Page.class)
    public ResponseEntity<Page<Sensor>> getAllSensores(
            @ApiParam(value = "Número da página", defaultValue = "0") @RequestParam(defaultValue = "0") int page,
            @ApiParam(value = "Tamanho da página", defaultValue = "10") @RequestParam(defaultValue = "10") int size,
            @ApiParam(value = "Campo para ordenação", defaultValue = "id") @RequestParam(defaultValue = "id") String sortBy,
            @ApiParam(value = "Ordem de classificação (asc ou desc)", defaultValue = "asc") @RequestParam(defaultValue = "asc") String sortOrder
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        if (sortOrder.equalsIgnoreCase("desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        }
        Page<Sensor> sensors = sensorService.getAllSensores(pageable);
        return ResponseEntity.ok(sensors);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Obter um sensor pelo ID", response = Sensor.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sensor encontrado com sucesso"),
            @ApiResponse(code = 404, message = "Sensor não encontrado")
    })
    public ResponseEntity<Sensor> getSensorById(@ApiParam(value = "ID do sensor") @PathVariable Long id) {
        Optional<Sensor> sensor = sensorService.getSensorById(id);
        return sensor.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @ApiOperation(value = "Criar um novo sensor", response = Sensor.class)
    public Sensor createSensor(@ApiParam(value = "Detalhes do novo sensor a ser criado") @RequestBody Sensor sensor) {
        return sensorService.createSensor(sensor);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Atualizar um sensor existente", response = Sensor.class)
    public ResponseEntity<Sensor> updateSensor(@ApiParam(value = "ID do sensor a ser atualizado") @PathVariable Long id, 
                                                @ApiParam(value = "Detalhes do sensor atualizado") @RequestBody Sensor sensorDetails) {
        Optional<Sensor> sensorOptional = sensorService.updateSensor(id, sensorDetails);
        return sensorOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Excluir um sensor pelo ID")
    public ResponseEntity<Void> deleteSensor(@ApiParam(value = "ID do sensor a ser excluído") @PathVariable Long id) {
        if (sensorService.deleteSensor(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
