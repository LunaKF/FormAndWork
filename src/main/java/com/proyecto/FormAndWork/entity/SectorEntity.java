package com.proyecto.FormAndWork.entity;

import com.proyecto.FormAndWork.api.empresa;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.persistence.OneToMany;
import jakarta.persistence.FetchType;

@Entity
@Table(name = "sector")
public class SectorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 255)
    private String nombre;

    @OneToMany(mappedBy = "sector", fetch = FetchType.LAZY)
    private java.util.List<EmpresaEntity> empresa;

    
    @OneToMany(mappedBy = "sector", fetch = FetchType.LAZY)
    private java.util.List<AlumnoEntity> alumno;



    public SectorEntity() {
        empresa = new java.util.ArrayList<>();
        alumno = new java.util.ArrayList<>();
    }

    public SectorEntity(String nombre) {
        this.nombre = nombre;

    }

    public SectorEntity(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /*
     * si empresa es null, la función devolverá 0 en lugar de intentar llamar a
     * size(), evitando el error.
     */
    public int getEmpresas() {
        return (empresa != null) ? empresa.size() : 0;
    }

    public int getAlumnos() {
        return (alumno != null) ? alumno.size() : 0;
    }

}