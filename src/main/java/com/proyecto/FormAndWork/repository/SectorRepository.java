package com.proyecto.FormAndWork.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.proyecto.FormAndWork.entity.SectorEntity;

public interface SectorRepository extends JpaRepository<SectorEntity, Long> {

    Page<SectorEntity> findByNombreContaining(
            String filter,Pageable oPageable);


    List<SectorEntity> findAllByOrderByIdAsc();
/*
    @Query(value = "SELECT COUNT(*) FROM asiento, apunte WHERE asiento.id_usuario=:id AND apunte.id_asiento=asiento.id", nativeQuery = true)
    Long getApuntes(Long id);

    @Query(value = "SELECT COUNT(*) FROM asiento, apunte, periodo WHERE asiento.id_usuario=:id AND apunte.id_asiento=asiento.id AND periodo.id=asiento.id_periodo AND periodo.cerrado=0", nativeQuery = true)
    Long getApuntesAbiertos(Long id);
*/

}