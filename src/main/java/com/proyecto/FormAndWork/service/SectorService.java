package com.proyecto.FormAndWork.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.proyecto.FormAndWork.entity.SectorEntity;
import com.proyecto.FormAndWork.repository.SectorRepository;
import com.proyecto.FormAndWork.exception.*;

@Service
public class SectorService implements ServiceInterface<SectorEntity> {

    @Autowired
    SectorRepository oSectorRepository;

    @Autowired
    RandomService oRandomService;

    public Page<SectorEntity> getPage(Pageable oPageable, Optional<String> filter) {

        if (filter.isPresent()) {
            return oSectorRepository.findByNombreContaining(
                    filter.get(), oPageable);
        } else {
            return oSectorRepository.findAll(oPageable);
        }
    }

    public SectorEntity get(Long id) {
        return oSectorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sector no encontrado"));
        // return oSectorRepository.findById(id).get();
    }

    public Long count() {
        return oSectorRepository.count();
    }

    public Long delete(Long id) {
        oSectorRepository.deleteById(id);
        return 1L;
    }

    public SectorEntity create(SectorEntity oSectorEntity) {
        return oSectorRepository.save(oSectorEntity);
    }

    public SectorEntity update(SectorEntity oSectorEntity) {
        SectorEntity oSectorEntityFromDatabase = oSectorRepository.findById(oSectorEntity.getId()).get();
        if (oSectorEntity.getNombre() != null) {
            oSectorEntityFromDatabase.setNombre(oSectorEntity.getNombre());
        }
        return oSectorRepository.save(oSectorEntityFromDatabase);
    }

    public Long deleteAll() {
        oSectorRepository.deleteAll();
        return this.count();
    }

    public SectorEntity randomSelection() {
        return oSectorRepository.findById((long) oRandomService.getRandomInt(1, (int) (long) this.count())).get();
    }

}