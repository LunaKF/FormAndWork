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

    private final String[] arrNombresEmpresa = {
        "GlobalTech", "Innova Solutions", "NextGen", "SmartSystems", "BlueWave", "RedFox", "GreenEnergy",
        "CyberNet", "FutureVision", "CloudWorks", "DataMind", "OpenWay", "QuantumSoft", "NeuralCode",
        "EcoLogic", "SecureSys", "Hyperion", "Orion Labs", "NovaWorks", "Vertex Dynamics", "Infinity Corp",
        "AstroLink", "Zenith Technologies", "Luminex", "Nimbus", "Atlas Group", "StellarNet", "CoreLogic",
        "UrbanEdge", "PrimeWave", "Solaris", "Skyline Systems", "Titan Solutions", "PulseNet", "VectorLabs",
        "MindForge", "Optima", "Everest Consulting", "Silverline", "PhoenixSoft", "AetherWorks", "VertexOne"
    };

    private final String[] arrNombresPropios = {
        // Apellidos y nombres que suenan corporativos
        "López & Asociados", "Fernández Consulting", "Ortega Partners", "Martínez Group",
        "García Solutions", "Pérez Holdings", "Herrera Global", "Gutiérrez Advisors",
        "Sánchez Consulting", "Jiménez Partners", "Díaz Internacional", "Torres Group",
        "Navarro Digital", "Ruiz Consulting", "Castro Partners", "Moreno Capital",
        // Nombres de ciudades y regiones
        "Madrid Capital", "Valencia Business", "Barcelona Consulting", "Bilbao Tech",
        "Sevilla Global", "Granada Partners", "Lisboa Advisors", "Andes Group",
        "Europa Consulting", "Iberia Solutions", "Mediterráneo Partners", "Atlantic Corp",
        // Palabras aspiracionales / de confianza
        "Pinnacle", "Summit", "Visionary", "Unity", "TrustPoint",
        "Vanguard", "Momentum", "Integrity", "Horizon", "Legacy",
        "Evolve", "Alliance", "Synergy", "Vertex", "Frontier"
    };

    private final String[] arrTiposEmpresa = {
        "S.A.", "S.L.", "Cooperativa", "Group", "Holding", "Corp.", "Partners", "Consulting",
        "Enterprises", "Industries", "Solutions", "Networks", "Digital", "Technologies", "Labs",
        "Systems", "Dynamics", "Global", "Innovations", "Ventures", "Associates", "Studio",
        "Works", "Hub", "Experts"
    };

    @Autowired
    SectorService oSectorService;

    public Long randomCreate(Long cantidad) {
        for (int i = 0; i < cantidad; i++) {
            EmpresaEntity e = new EmpresaEntity();

            if (oRandomService.getRandomInt(0, 1) == 0) {
                e.setNombre(
                        arrNombresEmpresa[oRandomService.getRandomInt(0, arrNombresEmpresa.length - 1)] + " "
                        + arrTiposEmpresa[oRandomService.getRandomInt(0, arrTiposEmpresa.length - 1)]
                );
            } else {
                e.setNombre(
                        arrNombresPropios[oRandomService.getRandomInt(0, arrNombresPropios.length - 1)] + " "
                        + arrTiposEmpresa[oRandomService.getRandomInt(0, arrTiposEmpresa.length - 1)]
                );
            }

            e.setSector(oSectorService.randomSelection());

            String nombreCorto = e.getNombre().trim().replaceAll("[^a-zA-Z]", "").toLowerCase();
            if (nombreCorto.length() < 3) {
                nombreCorto = (nombreCorto + "xyz").substring(0, 3); 
            }else {
                nombreCorto = nombreCorto.substring(0, 3);
            }

            // si chocara (muy raro), añade sufijo extra
            String email = nombreCorto + oRandomService.getRandomInt(1000, 9999) + "@gmail.com";
            e.setEmail(email);

            // 12345@ (sha256 ya fija, ok para semillas)
            e.setPassword("ca20cffd89c01dd095d145f54aa6a2bdb4aead6eaefc1f32d573568659ae8278");

            oEmpresaRepository.save(e);
        }
        return oEmpresaRepository.count();
    }

    public Page<EmpresaEntity> getPage(Pageable oPageable, Optional<String> filter) {

        if (filter.isPresent()) {
            return oEmpresaRepository.findByNombreIgnoreCaseContainingOrEmailIgnoreCaseContaining(
                    filter.get(), filter.get(), oPageable);
        } else {
            return oEmpresaRepository.findAll(oPageable);
        }
    }

    public Page<EmpresaEntity> getPageXsector(Pageable oPageable, Optional<String> filter, Long id_sector) {
        if (filter.isPresent()) {
            return oEmpresaRepository.findByNombreIgnoreCaseContainingOrEmailIgnoreCaseContaining(
                    filter.get(), filter.get(), oPageable);
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

        // Si llega sin password, intentamos conservarla
        if (oEmpresaEntity.getPassword() == null || oEmpresaEntity.getPassword().isBlank()) {

            // Si trae id, es claramente una "actualización" (aunque entre por create)
            if (oEmpresaEntity.getId() != null) {
                EmpresaEntity db = oEmpresaRepository.findById(oEmpresaEntity.getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Empresa no encontrada"));

                oEmpresaEntity.setPassword(db.getPassword());
            } else {
                // si NO trae id y tampoco password -> decide una política:
                // 1) lanzar error claro
                throw new IllegalArgumentException("Password es obligatorio al crear una empresa");
                // o 2) asignar una por defecto (NO recomendado)
            }
        }

        // Si también viene sector null pero BD lo necesita, aquí podrías validar igual.
        return oEmpresaRepository.save(oEmpresaEntity);
    }

public EmpresaEntity update(EmpresaEntity oEmpresaEntity) {

    EmpresaEntity oEmpresaEntityFromDatabase = oEmpresaRepository.findById(oEmpresaEntity.getId())
            .orElseThrow(() -> new ResourceNotFoundException("Empresa no encontrada"));

    if (oEmpresaEntity.getNombre() != null) {
        oEmpresaEntityFromDatabase.setNombre(oEmpresaEntity.getNombre());
    }

    if (oEmpresaEntity.getEmail() != null) {
        oEmpresaEntityFromDatabase.setEmail(oEmpresaEntity.getEmail());
    }

    // ✅ Sector (si lo envías)
    if (oEmpresaEntity.getSector() != null && oEmpresaEntity.getSector().getId() != null) {
        oEmpresaEntityFromDatabase.setSector(oEmpresaEntity.getSector());
    }

    // ✅ Password: solo si llega una nueva (si no, se conserva)
    if (oEmpresaEntity.getPassword() != null && !oEmpresaEntity.getPassword().isBlank()) {
        oEmpresaEntityFromDatabase.setPassword(oEmpresaEntity.getPassword());
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
