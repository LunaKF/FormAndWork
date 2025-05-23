package com.proyecto.FormAndWork.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.proyecto.FormAndWork.entity.AlumnoEntity;

public interface AlumnoRepository extends JpaRepository<AlumnoEntity, Long> {

        Page<AlumnoEntity> findByNombreContainingOrApe1ContainingOrApe2ContainingOrEmailContaining(
                        String filter2, String filter3, String filter4, String filter5, Pageable oPageable);

        Page<AlumnoEntity> findBySectorId(Pageable oPageable, Long id_sector);

        @Query("SELECT a.id FROM AlumnoEntity a")
        List<Long> findAllIds();

        // -----------------------------------------------------------------------------------------

        Optional<AlumnoEntity> findByEmail(String email);

        Optional<AlumnoEntity> findByEmailAndPassword(String email, String password);

}

/*
 * PARA FILTRAR EN LA LISTA DE ALUMNOS POR SECTOR TENGO QUE CREAR UN
 * Page<AlumnoEntity>
 * findByNombreContainingOrApe1ContainingOrApe2ContainingOrEmailContaining(
 * String filter2, String filter3, String filter4, String filter5, Pageable
 * oPageable); PERO CON SECTORID CONTANING
 * 
 * 
 * 
 * 
 * @Query(value =
 * "SELECT COUNT(*) FROM asiento, apunte WHERE asiento.id_usuario=:id AND apunte.id_asiento=asiento.id"
 * , nativeQuery = true)
 * Long getApuntes(Long id);
 * 
 * @Query(value =
 * "SELECT COUNT(*) FROM asiento, apunte, periodo WHERE asiento.id_usuario=:id AND apunte.id_asiento=asiento.id AND periodo.id=asiento.id_periodo AND periodo.cerrado=0"
 * , nativeQuery = true)
 * Long getApuntesAbiertos(Long id);
 */
