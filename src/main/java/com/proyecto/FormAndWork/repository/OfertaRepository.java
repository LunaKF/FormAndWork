package com.proyecto.FormAndWork.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.proyecto.FormAndWork.entity.OfertaEntity;

public interface OfertaRepository extends JpaRepository<OfertaEntity, Long> {

        Page<OfertaEntity> findByTituloContainingOrDescripcionContainingOrEmpresa_NombreContainingOrSector_NombreContaining(
                        String titulo, String descripcion, String nombreEmpresa, String nombreSector,
                        Pageable pageable);

    /*     @Query(value = "SELECT * FROM oferta WHERE (titulo LIKE %:strFiltro% OR descripcion LIKE %:strFiltro%) AND id_empresa=:idempresa", nativeQuery = true)
        Page<OfertaEntity> findByTituloContainingOrDescripcionContainingAndEmpresaId(String strFiltro, Long idempresa,
                        Pageable pageable);*/

        @Query(value = "SELECT * FROM oferta WHERE (titulo LIKE CONCAT('%', :strFiltro, '%') OR descripcion LIKE CONCAT('%', :strFiltro, '%')) AND id_empresa = :idempresa",
       countQuery = "SELECT COUNT(*) FROM oferta WHERE (titulo LIKE CONCAT('%', :strFiltro, '%') OR descripcion LIKE CONCAT('%', :strFiltro, '%')) AND id_empresa = :idempresa",
       nativeQuery = true)
        Page<OfertaEntity> findByTituloContainingOrDescripcionContainingAndEmpresaId(@Param("strFiltro") String strFiltro,
                                                                             @Param("idempresa") Long idempresa,
                                                                             Pageable pageable);

        @Query("SELECT DISTINCT o FROM OfertaEntity o JOIN o.candidaturas c WHERE c.alumno.id = :alumnoId")
        Page<OfertaEntity> findByAlumnoCandidatado(@Param("alumnoId") Long alumnoId, Pageable pageable);


        Page<OfertaEntity> findBySectorId(Pageable oPageable, Long id_sector);

        Page<OfertaEntity> findByEmpresaId(Pageable oPageable, Long id_empresa);

        @Query("SELECT a.id FROM OfertaEntity a")
        List<Long> findAllIds();


}