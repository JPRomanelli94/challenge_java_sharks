package com.accenture.challenge.service;

import com.accenture.challenge.model.Acreditacion;
import com.accenture.challenge.repository.IAcreditacionRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AcreditacionService {
    
    @Autowired
    private IAcreditacionRepository acreditacionRepository;
    
    public Acreditacion crearAcreditacion(Acreditacion acreditacion) {
        return acreditacionRepository.save(acreditacion);
    }

    public List<Acreditacion> buscarAcreditaciones() {
        return acreditacionRepository.findAll();
    }
    
}
