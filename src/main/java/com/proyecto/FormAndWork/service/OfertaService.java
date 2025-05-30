package com.proyecto.FormAndWork.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.proyecto.FormAndWork.api.empresa;
import com.proyecto.FormAndWork.entity.AlumnoEntity;
import com.proyecto.FormAndWork.entity.EmpresaEntity;
import com.proyecto.FormAndWork.entity.OfertaEntity;
import com.proyecto.FormAndWork.entity.SectorEntity;
import com.proyecto.FormAndWork.repository.AlumnoRepository;
import com.proyecto.FormAndWork.repository.OfertaRepository;
import com.proyecto.FormAndWork.exception.*;

@Service
public class OfertaService implements ServiceInterface<OfertaEntity> {

    @Autowired
    OfertaRepository oOfertaRepository;

    @Autowired
    AlumnoRepository oAlumnoRepository;

    @Autowired
    RandomService oRandomService;

    @Autowired
    SectorService oSectorService;

    @Autowired
    EmpresaService oEmpresaService;

    @Autowired
    AuthService oAuthService;

    String[] arrDescripciones = {
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
        "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
        "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.",
        "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
        "Curabitur pretium tincidunt lacus. Nulla gravida orci a odio. Nullam varius, turpis et commodo pharetra.",
        "Maecenas suscipit, mauris nec venenatis commodo, est erat pretium ante, id molestie eros magna at orci."
    };

    Map<String, String[]> ofertasEmpleo = new HashMap<>();

    {
        ofertasEmpleo.put("Administración y gestión",
                new String[]{"Asistente administrativo", "Contable", "Secretario/a"});
        ofertasEmpleo.put("Agraria", new String[]{"Agricultor/a", "Ganadero/a", "Técnico agrícola"});
        ofertasEmpleo.put("Artes gráficas",
                new String[]{"Diseñador gráfico", "Impresor/a", "Técnico de preimpresión"});
        ofertasEmpleo.put("Artes y artesanías",
                new String[]{"Artista plástico", "Artesano/a", "Diseñador de joyas"});
        ofertasEmpleo.put("Comercio y marketing",
                new String[]{"Vendedor/a", "Gestor de marketing", "Community Manager"});
        ofertasEmpleo.put("Electricidad y electrónica",
                new String[]{"Técnico electricista", "Ingeniero electrónico", "Operador de sistemas"});
        ofertasEmpleo.put("Energía y agua",
                new String[]{"Técnico de energía solar", "Ingeniero de energía eólica", "Operador de planta"});
        ofertasEmpleo.put("Fabricación mecánica",
                new String[]{"Técnico de mantenimiento", "Ingeniero mecánico", "Operador de maquinaria"});
        ofertasEmpleo.put("Hostelería y turismo", new String[]{"Camarero/a", "Recepcionista de hotel", "Chef"});
        ofertasEmpleo.put("Imagen personal", new String[]{"Estilista", "Maquillador/a", "Peluquero/a"});
        ofertasEmpleo.put("Imagen y sonido", new String[]{"Camarógrafo", "Sonidista", "Editor de video"});
        ofertasEmpleo.put("Informática y comunicaciones",
                new String[]{"Desarrollador Java", "Analista de datos", "Técnico en redes"});
        ofertasEmpleo.put("Instalación y mantenimiento", new String[]{"Técnico de climatización",
            "Instalador de sistemas de seguridad", "Mecánico de electrodomésticos"});
        ofertasEmpleo.put("Madera, mueble y corcho",
                new String[]{"Carpintero/a", "Diseñador de interiores", "Ebanista"});
        ofertasEmpleo.put("Marítimo-pesquera",
                new String[]{"Pescador/a", "Técnico de acuicultura", "Capitán de barco"});
        ofertasEmpleo.put("Química",
                new String[]{"Técnico de laboratorio", "Químico/a analista", "Investigador/a químico"});
        ofertasEmpleo.put("Sanidad", new String[]{"Enfermero/a", "Médico general", "Técnico de laboratorio"});
        ofertasEmpleo.put("Seguridad y medio ambiente",
                new String[]{"Técnico de seguridad", "Inspector ambiental", "Gestor de residuos"});
        ofertasEmpleo.put("Servicios socioculturales y a la comunidad",
                new String[]{"Educador/a social", "Trabajador/a social", "Psicólogo/a"});
        ofertasEmpleo.put("Textil, confección y piel",
                new String[]{"Diseñador de moda", "Sastre/a", "Operador de máquina de coser"});
        ofertasEmpleo.put("Transporte y mantenimiento de vehículos",
                new String[]{"Conductor de camión", "Mecánico de automóviles", "Técnico de logística"});
        ofertasEmpleo.put("Vidrio y cerámica",
                new String[]{"Cristalero/a", "Ceramista", "Técnico de calidad en vidrio"});
    }

    public Long randomCreate(Long cantidad) {
        for (int i = 0; i < cantidad; i++) {
            OfertaEntity oOfertaEntity = new OfertaEntity();
            String sectorClave = (String) ofertasEmpleo.keySet().toArray()[oRandomService.getRandomInt(0,
                    ofertasEmpleo.size() - 1)];
            String titulo = ofertasEmpleo.get(sectorClave)[oRandomService.getRandomInt(0,
                    ofertasEmpleo.get(sectorClave).length - 1)];

            Optional<EmpresaEntity> empresaOpt = Optional.ofNullable(oEmpresaService.randomSelection());
            Optional<SectorEntity> sectorOpt = Optional.ofNullable(oSectorService.randomSelection());

            if (empresaOpt.isEmpty() || sectorOpt.isEmpty()) {
                throw new RuntimeException("No hay empresas o sectores disponibles para asignar a la oferta.");
            }

            oOfertaEntity.setTitulo(titulo);
            oOfertaEntity.setDescripcion("Oferta de empleo para " + titulo + " en el sector de " + sectorClave + ".");
            oOfertaEntity.setSector(sectorOpt.get());
            oOfertaEntity.setEmpresa(empresaOpt.get());

            oOfertaRepository.save(oOfertaEntity);
        }
        return oOfertaRepository.count();
    }

    public Page<OfertaEntity> getPage(Pageable oPageable, Optional<String> filter) {

        if (oAuthService.isSessionActive()) {
            //FALTA EL admin
            if (oAuthService.isEmpresa()) {
                EmpresaEntity oEmpresaAutenticada = oAuthService.getEmpresaFromToken();
                if (filter.isPresent()) {
                    return oOfertaRepository
                            .findByTituloContainingOrDescripcionContainingAndEmpresaId(filter.get(),
                                    oEmpresaAutenticada.getId(), oPageable);
                } else {
                    return oOfertaRepository.findByEmpresaId(oPageable, oEmpresaAutenticada.getId());
                }
            } else if (oAuthService.isAlumno()) {
                AlumnoEntity oAlumnoAutenticado = oAuthService.getAlumnoFromToken();
                return oOfertaRepository.findByAlumnoCandidatado(oAlumnoAutenticado.getId(), oPageable);
            } else {
                return Page.empty(); // por si hay un tipo de usuario no contemplado
            }
        } else {
            if (filter.isPresent()) {
                return oOfertaRepository
                        .findByTituloContainingOrDescripcionContainingOrEmpresa_NombreContainingOrSector_NombreContaining(
                                filter.get(), filter.get(), filter.get(), filter.get(), oPageable);
            } else {
                return oOfertaRepository.findAll(oPageable);
            }
        }
    }

    public Page<OfertaEntity> getPageXsector(Pageable oPageable, Optional<String> filter, Long id_sector) {
        if (filter.isPresent()) {
            return oOfertaRepository
                    .findByTituloContainingOrDescripcionContainingOrEmpresa_NombreContainingOrSector_NombreContaining(
                            filter.get(), filter.get(), filter.get(), filter.get(), oPageable);
        } else {
            return oOfertaRepository.findBySectorId(oPageable, id_sector);
        }
    }

    public Page<OfertaEntity> getPageXempresa(Pageable oPageable, Optional<String> filter, Long id_empresa) {
        if (filter.isPresent()) {
            return oOfertaRepository
                    .findByTituloContainingOrDescripcionContainingOrEmpresa_NombreContainingOrSector_NombreContaining(
                            filter.get(), filter.get(), filter.get(), filter.get(), oPageable);
        } else {
            return oOfertaRepository.findByEmpresaId(oPageable, id_empresa);
        }
    }

    public OfertaEntity get(Long id) {

        return oOfertaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Oferta no encontrada"));
        // return oOfertaRepository.findById(id).get();
    }

    public Long count() {
        return oOfertaRepository.count();
    }

    public Long delete(Long id) {
        oOfertaRepository.deleteById(id);
        return 1L;
    }

    public OfertaEntity create(OfertaEntity oOfertaEntity) {
        return oOfertaRepository.save(oOfertaEntity);
    }

    public OfertaEntity update(OfertaEntity oOfertaEntity) {
        OfertaEntity oOfertaEntityFromDatabase = oOfertaRepository.findById(oOfertaEntity.getId()).get();
        if (oOfertaEntity.getTitulo() != null) {
            oOfertaEntityFromDatabase.setTitulo(oOfertaEntity.getTitulo());
        }
        if (oOfertaEntity.getDescripcion() != null) {
            oOfertaEntityFromDatabase.setDescripcion(oOfertaEntity.getDescripcion());
        }
        if (oOfertaEntity.getSector() != null) {
            oOfertaEntityFromDatabase.setSector(oOfertaEntity.getSector());
        }
        if (oOfertaEntity.getEmpresa() != null) {
            oOfertaEntityFromDatabase.setEmpresa(oOfertaEntity.getEmpresa());
        }
        return oOfertaRepository.save(oOfertaEntityFromDatabase);
    }

    public Long deleteAll() {
        oOfertaRepository.deleteAll();
        return this.count();
    }

    public OfertaEntity randomSelection() {
        List<Long> listaIds = oOfertaRepository.findAllIds();
        if (listaIds.isEmpty()) {
            throw new ResourceNotFoundException("No hay ofertas disponibles para selección aleatoria");
        }
        Long idAleatorio = listaIds.get(oRandomService.getRandomInt(0, listaIds.size() - 1));
        return oOfertaRepository.findById(idAleatorio)
                .orElseThrow(() -> new ResourceNotFoundException("Oferta no encontrada"));
    }

}
