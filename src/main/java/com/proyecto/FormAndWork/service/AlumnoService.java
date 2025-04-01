package com.proyecto.FormAndWork.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.proyecto.FormAndWork.entity.AlumnoEntity;
import com.proyecto.FormAndWork.repository.AlumnoRepository;
import com.proyecto.FormAndWork.exception.*;


@Service
public class AlumnoService implements ServiceInterface<AlumnoEntity> {

    @Autowired
    AlumnoRepository oAlumnoRepository;

    @Autowired
    RandomService oRandomService;

    private String[] arrNombres = {"Pepe", "Laura", "Ignacio", "Maria", "Lorenzo", "Carmen", "Rosa", "Paco", "Luis",
        "Ana", "Rafa", "Manolo", "Lucia", "Marta", "Sara", "Rocio"};

        private String[] arrApellidos = {"Sancho", "Gomez", "Pérez", "Rodriguez", "Garcia", "Fernandez", "Lopez",
        "Martinez", "Sanchez", "Gonzalez", "Gimenez", "Feliu", "Gonzalez", "Hermoso", "Vidal", "Escriche", "Moreno"};
        @Autowired
        SectorService oSectorService;

    public Long randomCreate(Long cantidad) {
        for (int i = 0; i < cantidad; i++) {
            AlumnoEntity oAlumnoEntity = new AlumnoEntity();
            oAlumnoEntity.setNombre(arrNombres[oRandomService.getRandomInt(0, arrNombres.length - 1)]);
            oAlumnoEntity.setApe1(arrApellidos[oRandomService.getRandomInt(0, arrApellidos.length - 1)]);
            oAlumnoEntity.setApe2(arrApellidos[oRandomService.getRandomInt(0, arrApellidos.length - 1)]);
            oAlumnoEntity.setSector(oSectorService.randomSelection());
            oAlumnoEntity.setEmail("email" + oAlumnoEntity.getNombre() + oAlumnoEntity.getApe1()+oRandomService.getRandomInt(999, 9999) + "@gmail.com");
            oAlumnoRepository.save(oAlumnoEntity);
        }
        return oAlumnoRepository.count();
    }

    public Page<AlumnoEntity> getPage(Pageable oPageable, Optional<String> filter) {

        if (filter.isPresent()) {
            return oAlumnoRepository.findByNombreContainingOrApe1ContainingOrApe2ContainingOrEmailContaining(
                    filter.get(), filter.get(), filter.get(), filter.get(), oPageable);
        } else {
            return oAlumnoRepository.findAll(oPageable);
        }
    }

    public AlumnoEntity get(Long id) {
        return oAlumnoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alumno no encontrado"));
        // return oAlumnoRepository.findById(id).get();
    }

    public Long count() {
        return oAlumnoRepository.count();
    }

    public Long delete(Long id) {
        oAlumnoRepository.deleteById(id);
        return 1L;
    }

    public AlumnoEntity create(AlumnoEntity oAlumnoEntity) {
        return oAlumnoRepository.save(oAlumnoEntity);
    }

    public AlumnoEntity update(AlumnoEntity oAlumnoEntity) {
        AlumnoEntity oAlumnoEntityFromDatabase = oAlumnoRepository.findById(oAlumnoEntity.getId()).get();
        if (oAlumnoEntity.getNombre() != null) {
            oAlumnoEntityFromDatabase.setNombre(oAlumnoEntity.getNombre());
        }
        if (oAlumnoEntity.getApe1() != null) {
            oAlumnoEntityFromDatabase.setApe1(oAlumnoEntity.getApe1());
        }
        if (oAlumnoEntity.getApe2() != null) {
            oAlumnoEntityFromDatabase.setApe2(oAlumnoEntity.getApe2());
        }
        if (oAlumnoEntity.getEmail() != null) {
            oAlumnoEntityFromDatabase.setEmail(oAlumnoEntity.getEmail());
        }
        return oAlumnoRepository.save(oAlumnoEntityFromDatabase);
    }

    public Long deleteAll() {
        oAlumnoRepository.deleteAll();
        return this.count();
    }

    public AlumnoEntity randomSelection() {
        return oAlumnoRepository.findById((long) oRandomService.getRandomInt(1, (int) (long) this.count())).get();
    }

   
}