package com.accenture.challenge.controller;

import com.accenture.challenge.service.CostoService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/costos")
public class CostoController {
    
    @Autowired
    private CostoService costoService;
    
    @Autowired
    private PuntoVentaController puntoDeVentaController;

    // Endpoint para agregar un costo entre dos puntos de venta
    @PostMapping("/crear")
    public ResponseEntity<String> addCosto(@RequestParam int idA, @RequestParam int idB, @RequestParam int costo) {
        costoService.agregarCosto(idA, idB, costo);
        return ResponseEntity.ok("Costo agregado correctamente");
    }

    // Endpoint para eliminar un costo entre dos puntos de venta
    @DeleteMapping("/eliminar")
    public ResponseEntity<String> removeCosto(@RequestParam int idA, @RequestParam int idB) {
        costoService.eliminarCosto(idA, idB);
        return ResponseEntity.ok("Costo eliminado correctamente");
    }
    
    // Endpoint para obtener los costos desde puntos de venta directos hacia otro
    @GetMapping("/to/{id}")
    public Map<String, Integer> getCostosTo(@PathVariable int id) {
        return costoService.obtenerCostosHasta(id);
    }
    
    // Endpoint para obtener el camino de costo minimo entre dos puntos de venta
    @GetMapping("/camino/minimo")
    public String getCaminoMinimo(@RequestParam int idA, @RequestParam int idB) {
        return costoService.getCaminoMinimo(idA, idB);
    }
}