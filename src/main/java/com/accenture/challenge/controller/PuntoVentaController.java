package com.accenture.challenge.controller;

import com.accenture.challenge.model.PuntoVenta;
import com.accenture.challenge.service.PuntoVentaService;
import java.util.List;
import java.util.Map;
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

@RestController
@RequestMapping("/api")
public class PuntoVentaController {
    
    @Autowired
    private PuntoVentaService puntoDeVentaService;

    // Endpoint para recuperar todos los puntos de venta
    @GetMapping("/buscar/pventa")
    public List<PuntoVenta> getAllPuntosDeVenta() {
        return puntoDeVentaService.obtenerPuntoDeVenta();
    }
    
    // Endpoint para dar de alta a nuevo punto de venta
    @PostMapping("/crear/pventa")
    public String crearPuntoDeVenta(@RequestBody PuntoVenta puntV) {
        puntoDeVentaService.agregarPuntoDeVenta(puntV);
        return "El punto de venta fue creado correctamente";
    }
    
    //Endpoint para recuperar un punto de venta
    @GetMapping("/buscar/pventa/{id}")
    public PuntoVenta obtenerUnPuntoDeVenta(@PathVariable Integer id) {
        return puntoDeVentaService.obtenerPuntoVentaId(id);
    }
    
    // Endpoint para actualizar un punto de venta
    @PutMapping("/actualizar/pventa/{id}")
    public String updatePuntoDeVenta(@PathVariable Integer id, @RequestBody PuntoVenta nuevoNombre) {
        
        puntoDeVentaService.actualizarPuntoDeVenta(id, nuevoNombre);
        
        return "El punto de venta fue actualizado correctamente";
    }
    
    @DeleteMapping("/eliminar/pventa/{id}")
    public String deletePuntoDeVenta(@PathVariable Integer id){
        puntoDeVentaService.eliminarPuntoDeVenta(id);
        return "El punto de venta fue eliminado correctamente";
    }
}
