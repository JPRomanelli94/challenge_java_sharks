package com.accenture.challenge.controller;

import com.accenture.challenge.model.Acreditacion;
import com.accenture.challenge.model.PuntoVenta;
import com.accenture.challenge.service.AcreditacionService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/acreditaciones")
public class AcreditacionController {
    
    @Autowired
    private AcreditacionService acreditacionService;
    
    @Autowired
    private PuntoVentaController puntoDeVentaController;
    
    // Recibo y enriquezco una acreditación
    @PostMapping("/crear")
    public Acreditacion crearAcreditacion(@RequestBody Map<String, Object> request) {
        double importe = (double) request.get("importe");
        int idPuntoDeVenta = (int) request.get("idPuntoDeVenta");
        
        String nombrePuntoDeVenta = puntoDeVentaController.obtenerUnPuntoDeVenta(idPuntoDeVenta).getNombre();
    
        
        if (nombrePuntoDeVenta == null) {
            throw new RuntimeException("Punto de venta no encontrado");
        }
    
        Acreditacion acreditacion = new Acreditacion();
        acreditacion.setImporte(importe);
        acreditacion.setIdPuntoDeVenta(idPuntoDeVenta);
        acreditacion.setFechaRecepcion(LocalDateTime.now());
        acreditacion.setNombrePuntoDeVenta(nombrePuntoDeVenta);

        return acreditacionService.crearAcreditacion(acreditacion);
    
    }
    
    // Consulto todas las acreditaciones
    @GetMapping("/buscar")
    public List<Acreditacion> obtenerAcreditaciones() {
        return acreditacionService.buscarAcreditaciones();
    }

}
