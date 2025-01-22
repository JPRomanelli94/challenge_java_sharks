package com.accenture.challenge.service;

import com.accenture.challenge.model.PuntoVenta;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class PuntoVentaService {

    // Caché en memoria (puntos de venta)
    private Map<Integer, PuntoVenta> puntosDeVentaCache = new HashMap<>();

    // Inicialización con los puntos de venta
    public PuntoVentaService() {
        puntosDeVentaCache.put(1, new PuntoVenta(1, "CABA"));
        puntosDeVentaCache.put(2, new PuntoVenta(2, "GBA_1"));
        puntosDeVentaCache.put(3, new PuntoVenta(3, "GBA_2"));
        puntosDeVentaCache.put(4, new PuntoVenta(4, "Santa Fe"));
        puntosDeVentaCache.put(5, new PuntoVenta(5, "Córdoba"));
        puntosDeVentaCache.put(6, new PuntoVenta(6, "Misiones"));
        puntosDeVentaCache.put(7, new PuntoVenta(7, "Salta"));
        puntosDeVentaCache.put(8, new PuntoVenta(8, "Chubut"));
        puntosDeVentaCache.put(9, new PuntoVenta(9, "Santa Cruz"));
        puntosDeVentaCache.put(10, new PuntoVenta(10, "Catamarca"));
    }

    // Obtener todos los puntos de venta
    public List<PuntoVenta> obtenerPuntoDeVenta() {
        return new ArrayList<>(puntosDeVentaCache.values());
    }

    // Agregar un nuevo punto de venta
    public PuntoVenta agregarPuntoDeVenta(PuntoVenta puntoDeVenta) {
        puntosDeVentaCache.put(puntoDeVenta.getId(), puntoDeVenta);
        return puntoDeVenta;
    }

    // Actualizar un punto de venta existente
    public PuntoVenta actualizarPuntoDeVenta(int id, PuntoVenta nuevoNombre) {
        PuntoVenta punto = puntosDeVentaCache.get(id);
        if (punto != null) {
            punto.setNombre(nuevoNombre.getNombre());
            return punto;
        }
        return null;
    }

    // Eliminar un punto de venta
    public void eliminarPuntoDeVenta(int id) {
        puntosDeVentaCache.remove(id);
    }

    public PuntoVenta obtenerPuntoVentaId(Integer id) {
        return puntosDeVentaCache.get(id);
    }
}