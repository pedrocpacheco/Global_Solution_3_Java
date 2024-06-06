package br.com.bluesense.backendjava.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bluesense.backendjava.dtos.navio.NavioRequestDto;
import br.com.bluesense.backendjava.dtos.navio.NavioResponseDto;
import br.com.bluesense.backendjava.entities.navio.Navio;
import br.com.bluesense.backendjava.services.NavioService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/navios")
public class NavioController {  

    @Autowired
    private NavioService navioService;

    @GetMapping
    public ResponseEntity<List<NavioResponseDto>> getAllNavios() {
        return ResponseEntity.ok(navioService.getAllNavios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Navio> getNavioById(@PathVariable Long id) {
        Optional<Navio> navio = navioService.getNavioById(id);
        return navio.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Navio createNavio(@RequestBody @Valid NavioRequestDto navio) {
        return navioService.createNavio(navio);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Navio> updateNavio(@PathVariable Long id, @RequestBody Navio navioDetails) {
        Optional<Navio> navioOptional = navioService.updateNavio(id, navioDetails);
        return navioOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNavio(@PathVariable Long id) {
        if (navioService.deleteNavio(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
