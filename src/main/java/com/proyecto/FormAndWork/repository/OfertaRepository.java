package com.proyecto.FormAndWork.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.proyecto.FormAndWork.entity.OfertaEntity;

public interface OfertaRepository extends JpaRepository<OfertaEntity, Long> {

        Page<OfertaEntity> findByTituloContainingOrDescripcionContainingOrEmpresa_NombreContainingOrSector_NombreContaining(
                String titulo, String descripcion, String nombreEmpresa, String nombreSector, Pageable pageable);
            

    Page<OfertaEntity> findBySectorId(Pageable oPageable, Long id_sector);

    Page<OfertaEntity> findByEmpresaId(Pageable oPageable, Long id_empresa);

    @Query("SELECT a.id FROM OfertaEntity a")
    List<Long> findAllIds();

    /*
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

}