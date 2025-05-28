package com.proyecto.FormAndWork.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.proyecto.FormAndWork.entity.AlumnoEntity;
import com.proyecto.FormAndWork.repository.AlumnoRepository;
import com.proyecto.FormAndWork.repository.CandidaturaRepository;
import com.proyecto.FormAndWork.exception.*;

@Service
public class AlumnoService implements ServiceInterface<AlumnoEntity> {

    @Autowired
    AlumnoRepository oAlumnoRepository;

    @Autowired
    RandomService oRandomService;

    @Autowired
    SectorService oSectorService;

    @Autowired
    AuthService oAuthService;

    @Autowired
    CandidaturaRepository oCandidaturaRepository;

    private String[] arrNombres = {"Pepe", "Laura", "Ignacio", "Maria", "Lorenzo", "Carmen", "Rosa", "Paco", "Luis",
        "Ana", "Rafa", "Manolo", "Lucia", "Marta", "Sara", "Rocio"};

    private String[] arrApellidos = {"Sancho", "Gomez", "Pérez", "Rodriguez", "Garcia", "Fernandez", "Lopez",
        "Martinez", "Sanchez", "Gonzalez", "Gimenez", "Feliu", "Gonzalez", "Hermoso", "Vidal", "Escriche",
        "Moreno"};

    public Page<AlumnoEntity> getPage(Pageable oPageable, Optional<String> filter) {
        if (oAuthService.isAdmin()) {
            if (filter.isPresent()) {
                return oAlumnoRepository.findByNombreContainingOrApe1ContainingOrApe2ContainingOrEmailContaining(
                        filter.get(), filter.get(), filter.get(), filter.get(), oPageable);
            } else {
                return oAlumnoRepository.findAll(oPageable);
            }

        } else if (oAuthService.isAlumno()) {
            AlumnoEntity alumno = oAuthService.getAlumnoFromToken();
            return oAlumnoRepository.findById(alumno.getId())
                    .map(a -> new PageImpl<>(List.of(a), oPageable, 1))
                    .orElse(new PageImpl<>(List.of(), oPageable, 0));

        } else if (oAuthService.isEmpresa()) {
            Long empresaId = oAuthService.getEmpresaFromToken().getId();
            List<Long> idsAlumnos = oCandidaturaRepository.findAlumnoIdsByOfertaEmpresaId(empresaId);

            if (idsAlumnos.isEmpty()) {
                return new PageImpl<>(List.of(), oPageable, 0);
            }

            if (filter.isPresent()) {
                return oAlumnoRepository.findByIdInAndFilter(idsAlumnos, filter.get(), oPageable);
            } else {
                return oAlumnoRepository.findByIdIn(idsAlumnos, oPageable);
            }

        } else {
            throw new UnauthorizedAccessException("No tienes permisos para acceder a la lista de alumnos");
        }
    }


    /* public Page<AlumnoEntity> getPageXsector(Pageable oPageable, Optional<String> filter, Long id_sector) {
        if (filter.isPresent()) {
            return oAlumnoRepository.findByNombreContainingOrApe1ContainingOrApe2ContainingOrEmailContaining(
                    filter.get(), filter.get(), filter.get(), filter.get(), oPageable);
        } else {
            return oAlumnoRepository.findBySectorId(oPageable, id_sector);
        }
    }
     */
    public Page<AlumnoEntity> getPageXsector(Pageable oPageable, Optional<String> filter, Long id_sector) {

        if (oAuthService.isAdmin()) {
            if (filter.isPresent()) {
                return oAlumnoRepository.findByNombreContainingOrApe1ContainingOrApe2ContainingOrEmailContaining(
                        filter.get(), filter.get(), filter.get(), filter.get(), oPageable);
            } else {
                return oAlumnoRepository.findBySectorId( id_sector, oPageable);
            }

        } else if (oAuthService.isAlumno()) {
            AlumnoEntity alumno = oAuthService.getAlumnoFromToken();
            if (alumno.getSector().getId().equals(id_sector)) {
                return new PageImpl<>(List.of(alumno), oPageable, 1);
            } else {
                return new PageImpl<>(List.of(), oPageable, 0);
            }

        } else if (oAuthService.isEmpresa()) {
            Long empresaId = oAuthService.getEmpresaFromToken().getId();
            List<Long> idsAlumnos = oCandidaturaRepository.findAlumnoIdsByOfertaEmpresaId(empresaId);

            if (idsAlumnos.isEmpty()) {
                return new PageImpl<>(List.of(), oPageable, 0);
            }

            // Filtramos los alumnos por sector
            List<AlumnoEntity> alumnosFiltrados = oAlumnoRepository.findAllById(idsAlumnos).stream()
                    .filter(a -> a.getSector().getId().equals(id_sector))
                    .toList();

            // Convertimos la lista filtrada en página
            int start = (int) oPageable.getOffset();
            int end = Math.min((start + oPageable.getPageSize()), alumnosFiltrados.size());
            List<AlumnoEntity> content = alumnosFiltrados.subList(start, end);

            return new PageImpl<>(content, oPageable, alumnosFiltrados.size());

        } else {
            throw new UnauthorizedAccessException("No tienes permisos para acceder a los alumnos de este sector");
        }
    }

    public AlumnoEntity get(Long id) {
        AlumnoEntity alumno = oAlumnoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alumno no encontrado"));

        if (oAuthService.isAdmin()) {
            // Admin puede ver cualquier alumno
            return alumno;
        } else if (oAuthService.isAlumno()) {
            AlumnoEntity alumnoAutenticado = oAuthService.getAlumnoFromToken();

            if (alumnoAutenticado.getId().equals(id)) {
                // El alumno puede verse a sí mismo
                return alumno;
            } else {
                throw new UnauthorizedAccessException("No tienes permiso para ver a este alumno");
            }

        } else if (oAuthService.isEmpresa()) {
            Long empresaId = oAuthService.getEmpresaFromToken().getId();

            // Verificamos si el alumno tiene alguna candidatura en alguna oferta de esta empresa
            boolean tieneCandidatura = oCandidaturaRepository.existsByAlumnoIdAndOfertaEmpresaId(id, empresaId);

            if (tieneCandidatura) {
                return alumno;
            } else {
                throw new UnauthorizedAccessException("No tienes permiso para ver a este alumno");
            }

        } else {
            throw new UnauthorizedAccessException("No tienes permisos para acceder a esta información");
        }
    }

    public Long count() {
        if (!oAuthService.isAdmin()) {
            throw new UnauthorizedAccessException("No tienes permisos para contar los usuarios");
        } else {
            // Solo los administradores pueden contar los usuarios
            return oAlumnoRepository.count();
        }
    }

    public Long delete(Long id) {
        if (oAuthService.isAdmin()) {
            oAlumnoRepository.deleteById(id);
            return 1L;
        } else {
            throw new UnauthorizedAccessException("No tienes permisos para borrar el usuario");
        }
    }

    public AlumnoEntity create(AlumnoEntity oAlumnoEntity) {
        if (oAuthService.isAdmin()) {
            return oAlumnoRepository.save(oAlumnoEntity);
        } else {
            throw new UnauthorizedAccessException("No tienes permisos para crear el usuario");
        }
    }

    public AlumnoEntity update(AlumnoEntity oAlumnoEntity) {
        if (oAuthService.isAdmin() || oAuthService.isAlumnoWithItsOwnData(oAlumnoEntity.getId())) {

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

        } else {
            throw new UnauthorizedAccessException("No tienes permisos para actualizar el usuario");
        }
    }

    public Long deleteAll() {
        if (!oAuthService.isAdmin()) {
            throw new UnauthorizedAccessException("No tienes permisos para borrar todos los usuarios");
        } else {
            oAlumnoRepository.deleteAll();
            return this.count();
        }

    }

    public Long randomCreate(Long cantidad) {
        for (int i = 0; i < cantidad; i++) {
            AlumnoEntity oAlumnoEntity = new AlumnoEntity();
            oAlumnoEntity.setNombre(arrNombres[oRandomService.getRandomInt(0, arrNombres.length - 1)]);
            oAlumnoEntity.setApe1(arrApellidos[oRandomService.getRandomInt(0, arrApellidos.length - 1)]);
            oAlumnoEntity.setApe2(arrApellidos[oRandomService.getRandomInt(0, arrApellidos.length - 1)]);
            oAlumnoEntity.setSector(oSectorService.randomSelection());
            oAlumnoEntity.setEmail("email" + oAlumnoEntity.getNombre() + oAlumnoEntity.getApe1()
                    + oRandomService.getRandomInt(999, 9999) + "@gmail.com");
            oAlumnoRepository.save(oAlumnoEntity);
        }
        return oAlumnoRepository.count();
    }

    public AlumnoEntity randomSelection() {
        List<Long> listaIds = oAlumnoRepository.findAllIds();
        if (listaIds.isEmpty()) {
            throw new ResourceNotFoundException("No hay alumnos disponibles para selección aleatoria");
        }
        Long idAleatorio = listaIds.get(oRandomService.getRandomInt(0, listaIds.size() - 1));
        return oAlumnoRepository.findById(idAleatorio)
                .orElseThrow(() -> new ResourceNotFoundException("Alumno no encontrado"));
    }

}
