package com.proyecto.FormAndWork.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.proyecto.FormAndWork.entity.AlumnoEntity;
import com.proyecto.FormAndWork.entity.EmpresaEntity;

public interface EmpresaRepository extends JpaRepository<EmpresaEntity, Long> {

    Page<EmpresaEntity> findByNombreContainingOrSectorContainingOrEmailContaining(
            String filter2, String filter3, String filter4, Pageable oPageable);


            @Query("SELECT e.id FROM EmpresaEntity e")
            List<Long> findAllIds();
            
            List<EmpresaEntity> findAllByOrderByIdAsc();

            Page<EmpresaEntity> findBySectorId(Pageable oPageable, Long id_sector);

            /*
    @Query(value = "SELECT COUNT(*) FROM asiento, apunte WHERE asiento.id_usuario=:id AND apunte.id_asiento=asiento.id", nativeQuery = true)
    Long getApuntes(Long id);

    @Query(value = "SELECT COUNT(*) FROM asiento, apunte, periodo WHERE asiento.id_usuario=:id AND apunte.id_asiento=asiento.id AND periodo.id=asiento.id_periodo AND periodo.cerrado=0", nativeQuery = true)
    Long getApuntesAbiertos(Long id);
*/

}