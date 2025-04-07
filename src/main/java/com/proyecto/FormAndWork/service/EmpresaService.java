package com.proyecto.FormAndWork.service;

import java.util.List;
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

 
    private String[] arrNombresEmpresa = {
            "GlobalTech", "Innova Solutions", "NextGen", "SmartSystems", "BlueWave",
            "RedFox", "GreenEnergy", "CyberNet", "FutureVision", "CloudWorks",
            "DataMind", "OpenWay", "QuantumSoft", "NeuralCode", "EcoLogic", "SecureSys"
    };

    private String[] arrNombresPropios = {
            "Antonio López", "María Fernández", "Carlos Ortega", "Laura Martínez",
            "Fernando García", "Sofía Pérez", "Javier Herrera", "Carmen Gutiérrez",
            "Raúl Sánchez", "Beatriz Jiménez", "José Manuel Díaz", "Andrea Torres",
            "Pedro Navarro", "Marta Ruiz", "Alejandro Castro", "Lucía Moreno"
    };

    private String[] arrTiposEmpresa = {
            "S.A.", "S.L.", "Cooperativa", "Group", "Holding", "Corp.", "Partners", "Consulting"
    };

    @Autowired
    SectorService oSectorService;

    /*
     * private String[] arrSectores = {"Administración y gestión", "Agraria",
     * "Artes gráficas", "Artes y artesanías",
     * "Comercio y marketing", "Electricidad y electrónica", "Energía y agua",
     * "Fabricación mecánica",
     * "Hostelería y turismo", "Imagen personal", "Imagen y sonido",
     * "Informática y comunicaciones",
     * "Instalación y mantenimiento", "Madera, mueble y corcho",
     * "Marítimo-pesquera", "Química",
     * "Sanidad", "Seguridad y medio ambiente",
     * "Servicios socioculturales y a la comunidad",
     * "Textil, confección y piel", "Transporte y mantenimiento de vehículos",
     * "Vidrio y cerámica"};
     */

     public Long randomCreate(Long cantidad) {
        for (int i = 0; i < cantidad; i++) {
            EmpresaEntity oEmpresaEntity = new EmpresaEntity();
    
            // Generar un nombre aleatorio de empresa
            if (oRandomService.getRandomInt(0, 1) == 0) {
                oEmpresaEntity.setNombre(arrNombresEmpresa[oRandomService.getRandomInt(0, arrNombresEmpresa.length - 1)] + " " +
                                         arrTiposEmpresa[oRandomService.getRandomInt(0, arrTiposEmpresa.length - 1)]);
            } else {
                oEmpresaEntity.setNombre(arrNombresPropios[oRandomService.getRandomInt(0, arrNombresPropios.length - 1)] + " " +
                                         arrTiposEmpresa[oRandomService.getRandomInt(0, arrTiposEmpresa.length - 1)]);
            }
    
            // Asignar un sector aleatorio
            oEmpresaEntity.setSector(oSectorService.randomSelection());
    
            // Obtener las primeras 3 letras del nombre, asegurando que sean válidas
            String nombreCorto = oEmpresaEntity.getNombre().trim().replaceAll("[^a-zA-Z]", "").toLowerCase();
    
            // Si el nombre tiene menos de 3 caracteres, rellenarlo con "xyz"
            if (nombreCorto.length() < 3) {
                nombreCorto = (nombreCorto + "xyz").substring(0, 3);
            } else {
                nombreCorto = nombreCorto.substring(0, 3);
            }
    
            // Generar un email válido
            oEmpresaEntity.setEmail(
                "email" + nombreCorto + oRandomService.getRandomInt(1000, 9999) + "@gmail.com"
            );
    
            // Guardar la entidad en la base de datos
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
        List<Long> listaIds = oEmpresaRepository.findAllIds(); // método para obtener los IDs añadido en el repository
        if (listaIds.isEmpty()) {
            throw new ResourceNotFoundException("No hay empresas disponibles para selección aleatoria");
        }
        Long idAleatorio = listaIds.get(oRandomService.getRandomInt(0, listaIds.size() - 1));
        return oEmpresaRepository.findById(idAleatorio)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa no encontrada"));
    }

}