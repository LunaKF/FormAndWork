package com.proyecto.FormAndWork.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.proyecto.FormAndWork.entity.CandidaturaEntity;

public interface CandidaturaRepository extends JpaRepository<CandidaturaEntity, Long> {

    Page<CandidaturaEntity> findByAlumnoNombreContaining(String nombre, Pageable pageable);


            @Query("SELECT e.id FROM CandidaturaEntity e")
            List<Long> findAllIds();
      
            
    Page<CandidaturaEntity> findByOfertaId(Long id_oferta, Pageable oPageable); 

    Page<CandidaturaEntity> findByAlumnoId(Long id_alumno, Pageable oPageable);

    /*El siguiente método usa Spring Data JPA para verificar si existe una candidatura en la que:

        -El alumno tiene el alumnoId, y
        -La oferta está asociada a la empresaId.
    */
    boolean existsByAlumnoIdAndOfertaEmpresaId(Long alumnoId, Long empresaId);


}