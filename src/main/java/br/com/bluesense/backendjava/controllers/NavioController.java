package br.com.bluesense.backendjava.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.bluesense.backendjava.dtos.navio.NavioRequestDto;
import br.com.bluesense.backendjava.dtos.navio.NavioResponseDto;
import br.com.bluesense.backendjava.entities.navio.Navio;
import br.com.bluesense.backendjava.services.NavioService;

@RestController
@RequestMapping("/navios")
public class NavioController {  

    @Autowired
    private NavioService navioService;

    @GetMapping
    public ResponseEntity<Page<NavioResponseDto>> getAllNavios(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        if (sortOrder.equalsIgnoreCase("desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        }
        Page<NavioResponseDto> navios = navioService.getAllNavios(pageable);
        return ResponseEntity.ok(navios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Navio> getNavioById(@PathVariable Long id) {
        Optional<Navio> navio = navioService.getNavioById(id);
        return navio.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Navio createNavio(@RequestBody NavioRequestDto navio) {
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
