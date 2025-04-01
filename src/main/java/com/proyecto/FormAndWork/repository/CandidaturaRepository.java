package com.proyecto.FormAndWork.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.proyecto.FormAndWork.entity.CandidaturaEntity;

public interface CandidaturaRepository extends JpaRepository<CandidaturaEntity, Long> {

    Page<CandidaturaEntity> findByFechaContaining(
            String filter, Pageable oPageable);


            @Query("SELECT e.id FROM CandidaturaEntity e")
            List<Long> findAllIds();
            

}