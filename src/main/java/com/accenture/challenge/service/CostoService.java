package com.accenture.challenge.service;

import com.accenture.challenge.model.PuntoVenta;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CostoService {
    
    // Caché en memoria (costos)
    private final Map<String, Integer> costosCache = new HashMap<>();
    
    @Autowired
    private PuntoVentaService puntoVentaService;
    
    // Constructor donde inicializamos los costos de los puntos de venta
    public CostoService() {
        cargarCostosIniciales();
    }

    // Método que carga los costos iniciales entre los puntos de venta
    private void cargarCostosIniciales() {
        agregarCosto(1, 2, 2);
        agregarCosto(1, 3, 3);
        agregarCosto(2, 3, 5);
        agregarCosto(2, 4, 10);
        agregarCosto(1, 4, 11);
        agregarCosto(4, 5, 5);
        agregarCosto(2, 5, 14);
        agregarCosto(6, 7, 32);
        agregarCosto(8, 9, 11);
        agregarCosto(10, 7, 5);
        agregarCosto(3, 8, 10);
        agregarCosto(5, 8, 30);
        agregarCosto(10, 5, 5);
        agregarCosto(4, 6, 6);
    }

    // Método para agregar un costo entre dos puntos de venta
    public void agregarCosto(int idA, int idB, int costo) {
        String clave = generarClave(idA, idB);
        costosCache.put(clave, costo);

        // También agrego la relación inversa, ya que el costo es el mismo
        String claveInversa = generarClave(idB, idA);
        costosCache.put(claveInversa, costo);
    }

    // Método para generar la clave compuesta en formato "idA-idB"
    private String generarClave(int idA, int idB) {
        return idA < idB ? idA + "-" + idB : idB + "-" + idA;
    }

    // Eliminar un costo entre dos puntos de venta
    public void eliminarCosto(int idA, int idB) {
        String clave = generarClave(idA, idB);
        costosCache.remove(clave);

        // También elimino la relación inversa
        String claveInversa = generarClave(idB, idA);
        costosCache.remove(claveInversa);
    }

    // Obtener el costo entre dos puntos de venta
    public Integer obtenerCosto(int idA, int idB) {
        String clave = generarClave(idA, idB);
        return costosCache.get(clave);
    }

    // Obtener los costos hasta un punto desde otro
    public Map<String, Integer> obtenerCostosHasta(int idA) {
        Map<String, Integer> costosHastaA = new HashMap<>();
        for (String clave : costosCache.keySet()) {
            if (clave.endsWith("-" + idA)) {
                costosHastaA.put(clave, costosCache.get(clave));
            }
        }
        return costosHastaA;
    }
    
    //Aplico algoritmo Dijkstra
    public String getCaminoMinimo(int idA, int idB) {
        
        Map<Integer, Integer> distancias = new HashMap<>();
        Map<Integer, Integer> anteriores = new HashMap<>();
        Set<Integer> visitados = new HashSet<>();
        PriorityQueue<Map.Entry<Integer, Integer>> pq = new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue));

        // Inicialización: pongo distancia a infinito para todos los puntos, excepto el punto de inicio
        for (PuntoVenta punto : puntoVentaService.obtenerPuntoDeVenta()) {
            distancias.put(punto.getId(), Integer.MAX_VALUE);
        }
        distancias.put(idA, 0);

        pq.add(new AbstractMap.SimpleEntry<>(idA, 0));

        while (!pq.isEmpty()) {
            
            // Saco el punto con la menor distancia
            Map.Entry<Integer, Integer> current = pq.poll();
            int actual = current.getKey();

            if (visitados.contains(actual)) {
                continue;
            }
            
            visitados.add(actual);

            //Verifico los puntos vecinos
            for (Integer vecino : obtenerPuntosConCostoDe(actual)) {
                int costo = obtenerCosto(actual, vecino);
                if (distancias.get(actual) + costo < distancias.get(vecino)) {
                    distancias.put(vecino, distancias.get(actual) + costo);
                    anteriores.put(vecino, actual);
                    pq.add(new AbstractMap.SimpleEntry<>(vecino, distancias.get(vecino)));
                }
            }
        }

        if (distancias.get(idB) == Integer.MAX_VALUE) {
            return "No hay camino disponible entre " + idA + " y " + idB;
        }

        List<Integer> camino = new ArrayList<>();
        for (Integer at = idB; at != null; at = anteriores.get(at)) {
            camino.add(at);
        }
        
        
        Collections.reverse(camino);

        //Muestro el camino
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < camino.size(); i++) {
            if (i > 0) {
                sb.append(" -> ");
            }
            sb.append(puntoVentaService.obtenerPuntoVentaId(camino.get(i)).getNombre());
        }

        return "Costo mínimo: " + distancias.get(idB) + ", Camino: " + sb.toString();
    }
    
    // Método auxiliar para obtener los puntos de venta conectados
    public Set<Integer> obtenerPuntosConCostoDe(int id) {
        Set<Integer> vecinos = new HashSet<>();
        for (String key : costosCache.keySet()) {
            String[] partes = key.split("-");
            if (Integer.parseInt(partes[0]) == id) {
                vecinos.add(Integer.parseInt(partes[1]));
            }
        }
        return vecinos;
    }
}
