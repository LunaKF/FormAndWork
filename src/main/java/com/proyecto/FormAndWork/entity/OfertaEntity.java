package com.proyecto.FormAndWork.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "oferta")
public class OfertaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 255)
    private String titulo;

    @NotNull
    @Size(min = 3, max = 555)
    private String descripcion;

   @ManyToOne(fetch = jakarta.persistence.FetchType.EAGER)
    @JoinColumn(name = "id_sector")
    private SectorEntity sector;
 
    @ManyToOne(fetch = jakarta.persistence.FetchType.EAGER)
    @JoinColumn(name = "id_empresa")
    private EmpresaEntity empresa;

    @ManyToOne(fetch = jakarta.persistence.FetchType.EAGER)
    @JoinColumn(name = "id_candidatura")
    private CandidaturaEntity candidatura;


    public OfertaEntity() {
    }

    public OfertaEntity(String titulo, String descripcion, SectorEntity sector, EmpresaEntity empresa, CandidaturaEntity candidatura) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.sector = sector;
        this.empresa = empresa;
        this.candidatura = candidatura;
        
    }

    public OfertaEntity(Long id, String titulo, String descripcion, SectorEntity sector, EmpresaEntity empresa, CandidaturaEntity candidatura) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.sector = sector;            
        this.empresa = empresa;
        this.candidatura = candidatura;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {  
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }   

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public SectorEntity getSector() {
        return sector;
    }       

    public void setSector(SectorEntity sector) {
        this.sector = sector;
    }
    public CandidaturaEntity getCandidatura() {
        return candidatura;
    }

    public void setCandidatura(CandidaturaEntity candidatura) {
        this.candidatura = candidatura;
    }

    public EmpresaEntity getEmpresa() {
        return empresa;
    }
    public void setEmpresa(EmpresaEntity empresa) {
        this.empresa = empresa;
    }

}