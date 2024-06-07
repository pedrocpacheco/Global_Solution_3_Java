package br.com.bluesense.backendjava.controllers;

import br.com.bluesense.backendjava.dtos.navio.NavioRotaDto;
import br.com.bluesense.backendjava.entities.navio.NavioRota;
import br.com.bluesense.backendjava.services.NavioRotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/navio-rotas")
public class NavioRotaController {
  
    @Autowired
    private NavioRotaService navioRotaService;

    @GetMapping
    public List<NavioRota> getAllNavioRotas() {
        return navioRotaService.getAllNavioRotas();
    }

    @GetMapping("/{id}")
    public NavioRota getNavioRotaById(@PathVariable Long id) {
        return navioRotaService.getNavioRotaById(id);
    }

    @PostMapping
    public NavioRota createNavioRota(@RequestBody NavioRotaDto navioRota) {
        return navioRotaService.saveNavioRota(navioRota);
    }

    @DeleteMapping("/{id}")
    public void deleteNavioRota(@PathVariable Long id) {
        navioRotaService.deleteNavioRota(id);
    }
}
