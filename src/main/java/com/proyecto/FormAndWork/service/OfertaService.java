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
        OfertaEntity o = new OfertaEntity();

        // 1) Empresa primero → sector coherente
        EmpresaEntity empresa = Optional.ofNullable(oEmpresaService.randomSelection())
            .orElseThrow(() -> new RuntimeException("No hay empresas disponibles"));
        SectorEntity sector = empresa.getSector();

        // 2) Título alineado a tu catálogo
        String sectorClave = (String) ofertasEmpleo.keySet().toArray()
            [oRandomService.getRandomInt(0, ofertasEmpleo.size() - 1)];

        String[] posibles = ofertasEmpleo.get(sectorClave);
        String titulo = posibles[oRandomService.getRandomInt(0, posibles.length - 1)];

        o.setTitulo(titulo);
        o.setEmpresa(empresa);
        o.setSector(sector);

        // 3) Descripción rica (máx ~520 chars para no pasar 555)
        String descripcion = buildDescripcionLarga(sectorClave, titulo, empresa.getNombre());
        if (descripcion.length() > 540) { // margen de seguridad
            descripcion = descripcion.substring(0, 540);
        }
        o.setDescripcion(descripcion);

        oOfertaRepository.save(o);
    }
    return oOfertaRepository.count();
}

/** Genera 3–6 frases: rol, tareas, requisitos y beneficios */
private String buildDescripcionLarga(String sector, String titulo, String empresa) {
    String[] inicios = {
        "Buscamos", "Seleccionamos", "Se requiere", "Nos encantaría incorporar",
        "Ampliamos equipo con", "Abrimos vacante para"
    };
    String[] tareas = {
        "participar en proyectos de alto impacto",
        "colaborar con equipos multidisciplinares",
        "mejorar procesos internos y la calidad del servicio",
        "desarrollar soluciones escalables y mantenibles",
        "garantizar el cumplimiento de estándares y buenas prácticas",
        "dar soporte técnico y funcional a las áreas implicadas"
    };
    String[] requisitos = {
        "al menos 1 año de experiencia", "capacidad de aprendizaje continuo",
        "orientación a resultados y trabajo en equipo", "comunicación efectiva",
        "conocimientos sólidos en el área", "actitud proactiva y resolutiva"
    };
    String[] beneficios = {
        "horario flexible y modalidad híbrida",
        "plan de carrera y formación continua",
        "ambiente colaborativo y tecnología puntera",
        "conciliación y beneficios sociales",
        "participación en proyectos innovadores"
    };

    StringBuilder sb = new StringBuilder(520);
    sb.append(inicios[oRandomService.getRandomInt(0, inicios.length - 1)])
      .append(" un/a ").append(titulo.toLowerCase())
      .append(" para ").append(empresa).append(", dentro del sector de ")
      .append(sector.toLowerCase()).append(". ");

    // 1–2 frases de tareas
    int nt = oRandomService.getRandomInt(1, 2);
    for (int i = 0; i < nt; i++) {
        sb.append("La persona seleccionada deberá ")
          .append(tareas[oRandomService.getRandomInt(0, tareas.length - 1)])
          .append(". ");
    }

    // 1–2 frases de requisitos
    int nr = oRandomService.getRandomInt(1, 2);
    sb.append("Se valora ");
    for (int i = 0; i < nr; i++) {
        if (i > 0) sb.append(", ");
        sb.append(requisitos[oRandomService.getRandomInt(0, requisitos.length - 1)]);
    }
    sb.append(". ");

    // 1 frase de beneficios
    sb.append("Ofrecemos ")
      .append(beneficios[oRandomService.getRandomInt(0, beneficios.length - 1)])
      .append(".");

    return sb.toString();
}


    public Page<OfertaEntity> getPage(Pageable oPageable, Optional<String> filter) {

        if (oAuthService.isSessionActive()) {
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
            } else if (oAuthService.isAdmin()) {
                return oOfertaRepository.findAll(oPageable);

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
