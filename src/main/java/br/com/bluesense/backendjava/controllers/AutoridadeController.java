package br.com.bluesense.backendjava.controllers;

import br.com.bluesense.backendjava.entities.Autoridade;
import br.com.bluesense.backendjava.services.AutoridadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/autoridades")
public class AutoridadeController {

    @Autowired
    private AutoridadeService autoridadeService;

    @GetMapping
    public List<Autoridade> getAllAutoridades() {
        return autoridadeService.getAllAutoridades();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Autoridade> getAutoridadeById(@PathVariable Long id) {
        Optional<Autoridade> autoridade = autoridadeService.getAutoridadeById(id);
        return autoridade.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Autoridade createAutoridade(@RequestBody Autoridade autoridade) {
        return autoridadeService.createAutoridade(autoridade);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Autoridade> updateAutoridade(@PathVariable Long id, @RequestBody Autoridade autoridadeDetails) {
        Optional<Autoridade> autoridadeOptional = autoridadeService.updateAutoridade(id, autoridadeDetails);
        return autoridadeOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAutoridade(@PathVariable Long id) {
        if (autoridadeService.deleteAutoridade(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
