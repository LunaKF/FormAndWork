package com.proyecto.FormAndWork.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.proyecto.FormAndWork.entity.AlumnoEntity;
import com.proyecto.FormAndWork.entity.CandidaturaEntity;
import com.proyecto.FormAndWork.entity.EmpresaEntity;
import com.proyecto.FormAndWork.entity.OfertaEntity;
import com.proyecto.FormAndWork.repository.CandidaturaRepository;
import com.proyecto.FormAndWork.exception.*;

@Service
public class CandidaturaService implements ServiceInterface<CandidaturaEntity> {

    @Autowired
    CandidaturaRepository oCandidaturaRepository;

    @Autowired
    AlumnoService oAlumnoService;

    @Autowired
    OfertaService oOfertaService;

    @Autowired
    RandomService oRandomService;

    @Autowired
    AuthService oAuthService;


    @Autowired
    SectorService oSectorService;

    public Page<CandidaturaEntity> getPage(Pageable oPageable, Optional<String> filter) {
        if (!oAuthService.isSessionActive()) {
            throw new UnauthorizedAccessException("No tienes permisos para acceder a esta información");
        } else if (oAuthService.isAdmin()) {
            return oCandidaturaRepository.findAll(oPageable);
        } else if (oAuthService.isEmpresa()) {
            // aqui una empresa ve las candidaturas de sus ofertas
            EmpresaEntity oEmpresaEntity = oAuthService.getEmpresaFromToken();
            return oCandidaturaRepository.findByOfertaEmpresaId(oEmpresaEntity.getId(), oPageable);

        } else if (oAuthService.isAlumno()) {
            // aqui un alumno ve las candidaturas de sus ofertas
            AlumnoEntity oAlumnoEntity = oAuthService.getAlumnoFromToken();
            return oCandidaturaRepository.findByAlumnoId(oAlumnoEntity.getId(), oPageable);

        }
        if (filter.isPresent()) {
            return oCandidaturaRepository.findByAlumnoNombreContaining(
                    filter.get(), oPageable);
        } else {
            return oCandidaturaRepository.findAll(oPageable);
        }
    }

    public Page<CandidaturaEntity> getPageXoferta(Pageable oPageable, Optional<String> filter, Long id_oferta) {
        if (!oAuthService.isSessionActive()) {
            throw new UnauthorizedAccessException("No tienes permisos para acceder a esta información");
        }
    OfertaEntity oOfertaEntity = oOfertaService.get(id_oferta);

    if (oAuthService.isAdmin()) {
        if (filter.isPresent()) {
            return oCandidaturaRepository.findByAlumnoNombreContaining(filter.get(), oPageable);
        } else {
            return oCandidaturaRepository.findByOfertaId(id_oferta, oPageable);
        }
    }

    if (oAuthService.isEmpresa()) {
        EmpresaEntity oEmpresaEntity = oAuthService.getEmpresaFromToken();

        if (!oEmpresaEntity.getId().equals(oOfertaEntity.getEmpresa().getId())) {
            throw new UnauthorizedAccessException("No tienes permisos para acceder a esta información");
        }

        if (filter.isPresent()) {
            return oCandidaturaRepository.findByAlumnoNombreContaining(filter.get(), oPageable);
        } else {
            return oCandidaturaRepository.findByOfertaId(id_oferta, oPageable);
        }
    }

    if (oAuthService.isAlumno()) {
        AlumnoEntity oAlumnoEntity = oAuthService.getAlumnoFromToken();

        // Verificamos si el alumno tiene una candidatura para esa oferta
        boolean existe = oCandidaturaRepository.existsByAlumnoIdAndOfertaEmpresaId(
                oAlumnoEntity.getId(), oOfertaEntity.getEmpresa().getId());

        if (!existe) {
            throw new UnauthorizedAccessException("No tienes permisos para acceder a esta información");
        }

        // Mostramos solo las candidaturas de ese alumno en esa oferta
        return oCandidaturaRepository.findByOfertaIdAndAlumnoId(id_oferta, oAlumnoEntity.getId(), oPageable);
    }

    throw new UnauthorizedAccessException("No tienes permisos para acceder a esta información");
}

public Page<CandidaturaEntity> getPageXalumno(Pageable oPageable, Optional<String> filter, Long id_alumno) {

    if (!oAuthService.isSessionActive()) {
        throw new UnauthorizedAccessException("No tienes permisos para acceder a esta información");
    }

    if (oAuthService.isAdmin()) {
        // El admin puede ver todas las candidaturas de cualquier alumno
        return oCandidaturaRepository.findByAlumnoId(id_alumno, oPageable);
    }

    if (oAuthService.isAlumno()) {
        AlumnoEntity oAlumnoEntity = oAuthService.getAlumnoFromToken();

        // Solo puede ver sus propias candidaturas
        if (!oAlumnoEntity.getId().equals(id_alumno)) {
            throw new UnauthorizedAccessException("No tienes permisos para acceder a esta información");
        }

        return oCandidaturaRepository.findByAlumnoId(id_alumno, oPageable);
    }

    throw new UnauthorizedAccessException("No tienes permisos para acceder a esta información");
}


    //Empresas: solo pueden ver las candidaturas de sus ofertas
    //Alumnos: solo pueden ver sus candidaturas
    //Admin: puede ver todas las candidaturas
    /*  public CandidaturaEntity get(Long id) {

        // sacar el email
        

        if (oAuthService.isAdmin()) {
            return oCandidaturaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Candidatura no encontrado"));

        } else {
            if (oAuthService.isAlumno()){
                AlumnoEntity oAlumnoEntity= oAuthService.getAlumnoFromToken();
                // ¿la candidatura es de él ? si-> devoelver la candidatura no-> dar error

            } else{
                if (oAuthService.isEmpresa()){
                    EmpresaEntity oEmpresaEntity= oAuthService.getEmpresaFromToken();

                    //¿la candidatura es para la empresa?
                }
            }


        }

        // return oCandidaturaRepository.findById(id).get();
    } */
    public CandidaturaEntity get(Long id) {
        // Obtener la candidatura desde la base de datos
        CandidaturaEntity candidatura = oCandidaturaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidatura no encontrada"));

        if (oAuthService.isAdmin()) {
            // Si es admin, tiene acceso completo
            return candidatura;
        } else if (oAuthService.isAlumno()) {
            AlumnoEntity alumnoAutenticado = oAuthService.getAlumnoFromToken();

            // Verificar si la candidatura pertenece al alumno autenticado
            if (candidatura.getAlumno().getId().equals(alumnoAutenticado.getId())) {
                return candidatura;
            } else {
                throw new UnauthorizedAccessException("No puedes acceder a esta candidatura");
            }

        } else if (oAuthService.isEmpresa()) {
            EmpresaEntity empresaAutenticada = oAuthService.getEmpresaFromToken();

            // Verificar si la oferta de la candidatura pertenece a la empresa autenticada
            if (candidatura.getOferta().getEmpresa().getId().equals(empresaAutenticada.getId())) {
                return candidatura;
            } else {
                throw new UnauthorizedAccessException("No puedes acceder a esta candidatura");
            }

        } else {
            throw new UnauthorizedAccessException("No tienes permisos para acceder a esta información");
        }
    }

    public Long count() {
        return oCandidaturaRepository.count();
    }

    public Long delete(Long id) {
        oCandidaturaRepository.deleteById(id);
        return 1L;
    }

    public CandidaturaEntity create(CandidaturaEntity oCandidaturaEntity) {
        return oCandidaturaRepository.save(oCandidaturaEntity);
    }

    public CandidaturaEntity update(CandidaturaEntity oCandidaturaEntity) {
        CandidaturaEntity oCandidaturaEntityFromDatabase = oCandidaturaRepository.findById(oCandidaturaEntity.getId())
                .get();
        if (oCandidaturaEntity.getFecha() != null) {
            oCandidaturaEntityFromDatabase.setFecha(oCandidaturaEntity.getFecha());
        }
        if (oCandidaturaEntity.getAlumno() != null) {
            oCandidaturaEntityFromDatabase.setAlumno(oCandidaturaEntity.getAlumno());
        }
        if (oCandidaturaEntity.getOferta() != null) {
            oCandidaturaEntityFromDatabase.setOferta(oCandidaturaEntity.getOferta());
        }
        return oCandidaturaRepository.save(oCandidaturaEntityFromDatabase);
    }

    public Long deleteAll() {
        oCandidaturaRepository.deleteAll();
        return this.count();
    }

    public CandidaturaEntity randomSelection() {
        List<Long> listaIds = oCandidaturaRepository.findAllIds(); // método para obtener los IDs añadido en el
        // repository
        if (listaIds.isEmpty()) {
            throw new ResourceNotFoundException("No hay candidaturas disponibles para selección aleatoria");
        }
        Long idAleatorio = listaIds.get(oRandomService.getRandomInt(0, listaIds.size() - 1));
        return oCandidaturaRepository.findById(idAleatorio)
                .orElseThrow(() -> new ResourceNotFoundException("Candidatura no encontrada"));
    }
public Long randomCreate(Long cantidad) {
    List<CandidaturaEntity> buffer = new ArrayList<>();
    int batchSize = 500;

    for (int i = 0; i < cantidad; i++) {
        CandidaturaEntity c = new CandidaturaEntity();
        c.setFecha(LocalDate.now().minusDays(oRandomService.getRandomInt(0, 30)));

        AlumnoEntity alumno = oAlumnoService.randomSelection();
        OfertaEntity oferta = oOfertaService.randomSelection();

        // Evitar duplicados
        boolean yaExiste = oCandidaturaRepository.existsByAlumnoIdAndOfertaEmpresaId(
                alumno.getId(), oferta.getId()
        );
        if (yaExiste) { i--; continue; } // reintentar

        c.setAlumno(alumno);
        c.setOferta(oferta);
        buffer.add(c);

        // Guardar en lotes
        if (buffer.size() >= batchSize) {
            oCandidaturaRepository.saveAll(buffer);
            buffer.clear();
        }
    }

    // Guardar lo que quede pendiente
    if (!buffer.isEmpty()) {
        oCandidaturaRepository.saveAll(buffer);
    }

    return oCandidaturaRepository.count();
}


}
