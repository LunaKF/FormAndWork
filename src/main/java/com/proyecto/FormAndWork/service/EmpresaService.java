package com.proyecto.FormAndWork.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.proyecto.FormAndWork.entity.AlumnoEntity;
import com.proyecto.FormAndWork.entity.EmpresaEntity;
import com.proyecto.FormAndWork.entity.SectorEntity;
import com.proyecto.FormAndWork.repository.EmpresaRepository;
import com.proyecto.FormAndWork.exception.*;


@Service
public class EmpresaService implements ServiceInterface<EmpresaEntity> {

    @Autowired
    EmpresaRepository oEmpresaRepository;

    @Autowired
    RandomService oRandomService;

    private String[] arrNombres = {"Pepe", "Laura", "Ignacio", "Maria", "Lorenzo", "Carmen", "Rosa", "Paco", "Luis",
        "Ana", "Rafa", "Manolo", "Lucia", "Marta", "Sara", "Rocio"};


        @Autowired
        SectorService oSectorService;

        public Page<EmpresaEntity> getPage(Pageable oPageable, Optional<String> filter) {

            if (filter.isPresent()) {
                return oEmpresaRepository.findByNombreContainingOrSectorContainingOrEmailContaining(
                        filter.get(), filter.get(), filter.get(), oPageable);
            } else {
                return oEmpresaRepository.findAll(oPageable);
            }
        }
    
        public Page<EmpresaEntity> getPageXsector(Pageable oPageable, Optional<String> filter, Long id_sector) {
        if (filter.isPresent()) {
            return oEmpresaRepository.findByNombreContainingOrSectorContainingOrEmailContaining(
                    filter.get(), filter.get(), filter.get(), oPageable);
        } else {
            return oEmpresaRepository.findBySectorId(oPageable, id_sector);
        }
    }

        public List<EmpresaEntity> getAll() {
            return oEmpresaRepository.findAll(); 
        }
    
    
    public EmpresaEntity get(Long id) {
        return oEmpresaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa no encontrada"));
        // return oEmpresaRepository.findById(id).get();
    }

    public List<EmpresaEntity> getAllOrdered() {
        return oEmpresaRepository.findAllByOrderByIdAsc();
    }

    public Long count() {
        return oEmpresaRepository.count();
    }

    public Long delete(Long id) {
        oEmpresaRepository.deleteById(id);
        return 1L;
    }

    public EmpresaEntity create(EmpresaEntity oEmpresaEntity) {
        return oEmpresaRepository.save(oEmpresaEntity);
    }

    public EmpresaEntity update(EmpresaEntity oEmpresaEntity) {
        EmpresaEntity oEmpresaEntityFromDatabase = oEmpresaRepository.findById(oEmpresaEntity.getId()).get();
        if (oEmpresaEntity.getNombre() != null) {
            oEmpresaEntityFromDatabase.setNombre(oEmpresaEntity.getNombre());
        }
        if (oEmpresaEntity.getEmail() != null) {
            oEmpresaEntityFromDatabase.setEmail(oEmpresaEntity.getEmail());
        }
        return oEmpresaRepository.save(oEmpresaEntityFromDatabase);
    }

    public Long deleteAll() {
        oEmpresaRepository.deleteAll();
        return this.count();
    }

    public EmpresaEntity randomSelection() {
        List<Long> listaIds = oEmpresaRepository.findAllIds(); //  método para obtener los IDs añadido en el repository
        if (listaIds.isEmpty()) {
            throw new ResourceNotFoundException("No hay empresas disponibles para selección aleatoria");
        }
        Long idAleatorio = listaIds.get(oRandomService.getRandomInt(0, listaIds.size() - 1));
        return oEmpresaRepository.findById(idAleatorio).orElseThrow(() -> new ResourceNotFoundException("Empresa no encontrada"));
    }
    
    public Long randomCreate(Long cantidad) {
        for (int i = 0; i < cantidad; i++) {
            EmpresaEntity oEmpresaEntity = new EmpresaEntity();
            oEmpresaEntity.setNombre(arrNombres[oRandomService.getRandomInt(0, arrNombres.length - 1)]);
            oEmpresaEntity.setSector(oSectorService.randomSelection());
            oEmpresaEntity.setEmail("email" + oEmpresaEntity.getNombre() + oRandomService.getRandomInt(999, 9999) + "@gmail.com");
            oEmpresaRepository.save(oEmpresaEntity);
        }
        return oEmpresaRepository.count();
    }

   
}