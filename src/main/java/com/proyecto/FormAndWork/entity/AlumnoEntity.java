package com.proyecto.FormAndWork.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

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
@Table(name = "alumno")
public class AlumnoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 255)
    private String nombre;

    @NotNull
    @Size(min = 3, max = 255)
    private String ape1;

    @NotNull
    @Size(min = 3, max = 255)
    private String ape2;

    @ManyToOne(fetch = jakarta.persistence.FetchType.EAGER)
    @JoinColumn(name = "id_sector")
    private SectorEntity sector;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @OneToMany(mappedBy = "alumno", fetch = FetchType.LAZY)
    private java.util.List<CandidaturaEntity> candidaturas;


    @Email
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    //contraseña 
    private String password;

    public AlumnoEntity() {
        candidaturas = new java.util.ArrayList<>();
    }

    public AlumnoEntity(String nombre, SectorEntity sector, String ape1, String ape2, String email) {
        this.nombre = nombre;
        this.sector = sector;
        this.ape1 = ape1;
        this.ape2 = ape2;
        this.email = email;
    }

    public AlumnoEntity(Long id, String nombre, String ape1, String ape2, SectorEntity sector, String email) {
        this.id = id;
        this.nombre = nombre;
        this.ape1 = ape1;
        this.ape2 = ape2;
        this.sector = sector;
        this.email = email;
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

    public SectorEntity getSector() {
        return sector;
    }

    public void setSector(SectorEntity sector) {
        this.sector = sector;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getApe1() {
        return ape1;
    }

    public void setApe1(String ape1) {
        this.ape1 = ape1;
    }

    public String getApe2() {
        return ape2;
    }

    public void setApe2(String ape2) {
        this.ape2 = ape2;
    }

    public int getCandidaturas() {
        return (candidaturas != null) ? candidaturas.size() : 0;
    }

}