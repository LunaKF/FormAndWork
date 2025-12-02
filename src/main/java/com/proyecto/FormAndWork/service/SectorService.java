package com.proyecto.FormAndWork.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.proyecto.FormAndWork.entity.SectorEntity;
import com.proyecto.FormAndWork.exception.ResourceNotFoundException;
import com.proyecto.FormAndWork.exception.UnauthorizedAccessException;
import com.proyecto.FormAndWork.repository.SectorRepository;

@Service
public class SectorService implements ServiceInterface<SectorEntity> {

    @Autowired
    SectorRepository oSectorRepository;

    @Autowired
    AuthService oAuthService;

    @Autowired
    RandomService oRandomService; // si no lo usas, puedes quitarlo

    // =========================
    // PLIST (solo admin)
    // =========================
    /* 
    @Override
    public Page<SectorEntity> getPage(Pageable oPageable, Optional<String> filter) {

        // El plist NO debe verse salvo que seas admin
        if (!oAuthService.isSessionActive() || !oAuthService.isAdmin()) {
            throw new UnauthorizedAccessException("Solo el administrador puede ver el listado de sectores");
        }

        if (filter.isPresent()) {
            return oSectorRepository.findByNombreContaining(filter.get(), oPageable);
        } else {
            return oSectorRepository.findAll(oPageable);
        }
    }
*/
@Override
public Page<SectorEntity> getPage(Pageable oPageable, Optional<String> filter) {

    System.out.println(">>> Entrando en SectorService.getPage");

    if (!oAuthService.isSessionActive() || !oAuthService.isAdmin()) {
        throw new UnauthorizedAccessException("Solo el administrador puede ver el listado de sectores");
    }

if (filter.isPresent()) {
            return oSectorRepository.findByNombreContaining(filter.get(), oPageable);
        } else {
            return oSectorRepository.findAll(oPageable);
        }
}


    // =========================
    // DETALLE (visible para todo el mundo)
    // =========================
    @Override
    public SectorEntity get(Long id) {
        // Aquí NO miramos sesión: cualquiera puede ver el detalle de un sector
        return oSectorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sector no encontrado"));
    }

    // Para el endpoint /sector/all
    public List<SectorEntity> getAllOrdered() {
        return oSectorRepository.findAllByOrderByIdAsc();
    }

    // =========================
    // COUNT (solo admin, opcional)
    // =========================
    @Override
    public Long count() {
        if (!oAuthService.isSessionActive() || !oAuthService.isAdmin()) {
            throw new UnauthorizedAccessException("Solo el administrador puede ver el número de sectores");
        }
        return oSectorRepository.count();
    }

    // =========================
    // DELETE (solo admin)
    // =========================
    @Override
    public Long delete(Long id) {
        if (!oAuthService.isSessionActive() || !oAuthService.isAdmin()) {
            throw new UnauthorizedAccessException("Solo el administrador puede eliminar sectores");
        }

        if (!oSectorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Sector no encontrado");
        }

        oSectorRepository.deleteById(id);
        return 1L;
    }

    // =========================
    // CREATE (solo admin)
    // =========================
    @Override
    public SectorEntity create(SectorEntity oSectorEntity) {
        if (!oAuthService.isSessionActive() || !oAuthService.isAdmin()) {
            throw new UnauthorizedAccessException("Solo el administrador puede crear sectores");
        }

        // Por si acaso envían un id desde el frontend
        oSectorEntity.setId(null);
        return oSectorRepository.save(oSectorEntity);
    }

    // =========================
    // UPDATE (solo admin)
    // =========================
    @Override
    public SectorEntity update(SectorEntity oSectorEntity) {
        if (!oAuthService.isSessionActive() || !oAuthService.isAdmin()) {
            throw new UnauthorizedAccessException("Solo el administrador puede modificar sectores");
        }

        SectorEntity oSectorEntityFromDatabase = oSectorRepository.findById(oSectorEntity.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Sector no encontrado"));

        if (oSectorEntity.getNombre() != null) {
            oSectorEntityFromDatabase.setNombre(oSectorEntity.getNombre());
        }

        return oSectorRepository.save(oSectorEntityFromDatabase);
    }

    @Override
    public Long deleteAll() {
        oSectorRepository.deleteAll();
        return this.count();
    }

    // =========================
    // RANDOM (solo admin, si lo usas)
    // =========================
    @Override
    public SectorEntity randomSelection() {
        if (!oAuthService.isSessionActive() || !oAuthService.isAdmin()) {
            throw new UnauthorizedAccessException("Solo el administrador puede usar selección aleatoria");
        }

        long total = oSectorRepository.count();
        if (total == 0) {
            throw new ResourceNotFoundException("No hay sectores disponibles");
        }

        int index = oRandomService.getRandomInt(0, (int) total - 1);
        return oSectorRepository.findAll().get(index);
    }
}
