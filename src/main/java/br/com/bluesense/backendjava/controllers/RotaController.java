package br.com.bluesense.backendjava.controllers;

import br.com.bluesense.backendjava.entities.Rota;
import br.com.bluesense.backendjava.services.RotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rotas")
public class RotaController {

    @Autowired
    private RotaService rotaService;

    @GetMapping
    public List<Rota> getAllRotas() {
        return rotaService.getAllRotas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rota> getRotaById(@PathVariable Long id) {
        Optional<Rota> rota = rotaService.getRotaById(id);
        return rota.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Rota createRota(@RequestBody Rota rota) {
        return rotaService.createRota(rota);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rota> updateRota(@PathVariable Long id, @RequestBody Rota rotaDetails) {
        Optional<Rota> rotaOptional = rotaService.updateRota(id, rotaDetails);
        return rotaOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRota(@PathVariable Long id) {
        if (rotaService.deleteRota(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

