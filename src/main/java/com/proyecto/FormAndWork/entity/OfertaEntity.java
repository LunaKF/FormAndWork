package com.proyecto.FormAndWork.entity;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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

    @OneToMany(mappedBy = "oferta", fetch = FetchType.LAZY)
    private java.util.List<CandidaturaEntity> candidaturas;


    public OfertaEntity(){
        candidaturas = new java.util.ArrayList<>();
    }

    public OfertaEntity(String titulo, String descripcion, SectorEntity sector, EmpresaEntity empresa) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.sector = sector;
        this.empresa = empresa;
        
        
    }

    public OfertaEntity(Long id, String titulo, String descripcion, SectorEntity sector, EmpresaEntity empresa) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.sector = sector;            
        this.empresa = empresa;
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
    public EmpresaEntity getEmpresa() {
        return empresa;
    }
    public void setEmpresa(EmpresaEntity empresa) {
        this.empresa = empresa;
    }

    public int getCandidaturas() {
        return (candidaturas != null) ? candidaturas.size() : 0;
    }

}