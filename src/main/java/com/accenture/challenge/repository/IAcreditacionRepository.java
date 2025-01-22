package com.accenture.challenge.repository;

import com.accenture.challenge.model.Acreditacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAcreditacionRepository extends JpaRepository<Acreditacion,Long> {
    
}
