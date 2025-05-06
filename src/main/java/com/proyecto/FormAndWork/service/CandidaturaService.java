package com.proyecto.FormAndWork.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.proyecto.FormAndWork.entity.AlumnoEntity;
import com.proyecto.FormAndWork.entity.CandidaturaEntity;
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

    private String[] arrNombres = {"Pepe", "Laura", "Ignacio", "Maria", "Lorenzo", "Carmen", "Rosa", "Paco", "Luis",
        "Ana", "Rafa", "Manolo", "Lucia", "Marta", "Sara", "Rocio"};

        String[] arrDescripciones = {
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
            "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
            "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.",
            "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            "Curabitur pretium tincidunt lacus. Nulla gravida orci a odio. Nullam varius, turpis et commodo pharetra.",
            "Maecenas suscipit, mauris nec venenatis commodo, est erat pretium ante, id molestie eros magna at orci."
    };

        @Autowired
        SectorService oSectorService;

    /*private String[] arrSectores = {"Administración y gestión", "Agraria", "Artes gráficas", "Artes y artesanías", 
        "Comercio y marketing", "Electricidad y electrónica", "Energía y agua", "Fabricación mecánica", 
        "Hostelería y turismo", "Imagen personal", "Imagen y sonido", "Informática y comunicaciones", 
        "Instalación y mantenimiento", "Madera, mueble y corcho", "Marítimo-pesquera", "Química", 
        "Sanidad", "Seguridad y medio ambiente", "Servicios socioculturales y a la comunidad", 
        "Textil, confección y piel", "Transporte y mantenimiento de vehículos", "Vidrio y cerámica"};
*/

    public Long randomCreate(Long cantidad) {
       for (int i = 0; i < cantidad; i++) {
            CandidaturaEntity oAsientoEntity = new CandidaturaEntity();
            oAsientoEntity.setFecha(LocalDate.now().minusDays(oRandomService.getRandomInt(0, 30)));
            oAsientoEntity.setAlumno(oAlumnoService.randomSelection());
            oAsientoEntity.setOferta(oOfertaService.randomSelection());
            oCandidaturaRepository.save(oAsientoEntity);
        }
        return oCandidaturaRepository.count();
    }

    public Page<CandidaturaEntity> getPage(Pageable oPageable, Optional<String> filter) {

        if (filter.isPresent()) {
            return oCandidaturaRepository.findByFechaContaining(
                    filter.get(), oPageable);
        } else {
            return oCandidaturaRepository.findAll(oPageable);
        }
    }

    public Page<CandidaturaEntity> getPageXoferta(Pageable oPageable, Optional<String> filter , Long id_oferta) {

        if (filter.isPresent()) {
            return oCandidaturaRepository.findByFechaContaining(
                    filter.get(), oPageable);
        } else {
            return oCandidaturaRepository.findByOfertaId(id_oferta, oPageable);
        }
    }

    public CandidaturaEntity get(Long id) {
        return oCandidaturaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidatura no encontrado"));
        // return oCandidaturaRepository.findById(id).get();
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
        CandidaturaEntity oCandidaturaEntityFromDatabase = oCandidaturaRepository.findById(oCandidaturaEntity.getId()).get();
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
        List<Long> listaIds = oCandidaturaRepository.findAllIds(); //  método para obtener los IDs añadido en el repository
        if (listaIds.isEmpty()) {
            throw new ResourceNotFoundException("No hay candidaturas disponibles para selección aleatoria");
        }
        Long idAleatorio = listaIds.get(oRandomService.getRandomInt(0, listaIds.size() - 1));
        return oCandidaturaRepository.findById(idAleatorio).orElseThrow(() -> new ResourceNotFoundException("Candidatura no encontrada"));
    }
    

   
}