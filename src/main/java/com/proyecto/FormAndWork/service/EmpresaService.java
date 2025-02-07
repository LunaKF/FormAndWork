package com.proyecto.FormAndWork.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.proyecto.FormAndWork.entity.EmpresaEntity;
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


    private String[] arrSectores = {"Administración y gestión", "Agraria", "Artes gráficas", "Artes y artesanías", 
        "Comercio y marketing", "Electricidad y electrónica", "Energía y agua", "Fabricación mecánica", 
        "Hostelería y turismo", "Imagen personal", "Imagen y sonido", "Informática y comunicaciones", 
        "Instalación y mantenimiento", "Madera, mueble y corcho", "Marítimo-pesquera", "Química", 
        "Sanidad", "Seguridad y medio ambiente", "Servicios socioculturales y a la comunidad", 
        "Textil, confección y piel", "Transporte y mantenimiento de vehículos", "Vidrio y cerámica"};


    public Long randomCreate(Long cantidad) {
        for (int i = 0; i < cantidad; i++) {
            EmpresaEntity oEmpresaEntity = new EmpresaEntity();
            oEmpresaEntity.setNombre(arrNombres[oRandomService.getRandomInt(0, arrNombres.length - 1)]);
            oEmpresaEntity.setSector(arrSectores[oRandomService.getRandomInt(0, arrSectores.length - 1)]);
            oEmpresaEntity.setEmail("email" + oEmpresaEntity.getNombre() + oRandomService.getRandomInt(999, 9999) + "@gmail.com");
            oEmpresaRepository.save(oEmpresaEntity);
        }
        return oEmpresaRepository.count();
    }

    public Page<EmpresaEntity> getPage(Pageable oPageable, Optional<String> filter) {

        if (filter.isPresent()) {
            return oEmpresaRepository.findByNombreContainingOrSectorContainingOrEmailContaining(
                    filter.get(), filter.get(), filter.get(), oPageable);
        } else {
            return oEmpresaRepository.findAll(oPageable);
        }
    }

    public EmpresaEntity get(Long id) {
        return oEmpresaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa no encontrado"));
        // return oEmpresaRepository.findById(id).get();
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
        if (oEmpresaEntity.getSector() != null) {
            oEmpresaEntityFromDatabase.setSector(oEmpresaEntity.getSector());
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
        return oEmpresaRepository.findById((long) oRandomService.getRandomInt(1, (int) (long) this.count())).get();
    }

   
}