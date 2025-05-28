package com.proyecto.FormAndWork.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.proyecto.FormAndWork.entity.AlumnoEntity;

public interface AlumnoRepository extends JpaRepository<AlumnoEntity, Long> {

    Page<AlumnoEntity> findByNombreContainingOrApe1ContainingOrApe2ContainingOrEmailContaining(
            String filter2, String filter3, String filter4, String filter5, Pageable oPageable);

Page<AlumnoEntity> findBySectorId(Long id_sector, Pageable oPageable); 

    @Query("SELECT a.id FROM AlumnoEntity a")
    List<Long> findAllIds();

    Optional<AlumnoEntity> findByEmail(String email);

    Optional<AlumnoEntity> findByEmailAndPassword(String email, String password);

    // -----------------------------------------------------------------------------------------
    Page<AlumnoEntity> findByIdIn(List<Long> ids, Pageable pageable);

    @Query("SELECT a FROM AlumnoEntity a WHERE a.id IN :ids AND "
            + "(a.nombre LIKE %:filter% OR a.ape1 LIKE %:filter% OR a.ape2 LIKE %:filter% OR a.email LIKE %:filter%)")
    Page<AlumnoEntity> findByIdInAndFilter(@Param("ids") List<Long> ids, @Param("filter") String filter, Pageable pageable);

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
