package br.com.bluesense.backendjava.controllers;

import br.com.bluesense.backendjava.dtos.leitura.LeituraResponseDto;
import br.com.bluesense.backendjava.entities.Leitura;
import br.com.bluesense.backendjava.services.LeituraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/leituras")
public class LeituraController {

    @Autowired
    private LeituraService leituraService;

    @GetMapping
    public List<Leitura> getAllLeituras() {
        return leituraService.getAllLeituras();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Leitura> getLeituraById(@PathVariable Long id) {
        Optional<Leitura> leitura = leituraService.getLeituraById(id);
        return leitura.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public LeituraResponseDto createLeitura(@RequestBody Leitura leitura) {
        return leituraService.createLeitura(leitura);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Leitura> updateLeitura(@PathVariable Long id, @RequestBody Leitura leituraDetails) {
        Optional<Leitura> leituraOptional = leituraService.updateLeitura(id, leituraDetails);
        return leituraOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLeitura(@PathVariable Long id) {
        if (leituraService.deleteLeitura(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

