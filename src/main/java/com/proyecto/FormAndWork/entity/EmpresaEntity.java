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


/*ACUERDATE DE CAMBIAR EL TIPO DE SECTOR DE STRING A sectorentity */
@Entity
@Table(name = "empresa")
public class EmpresaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 255)
    private String nombre;

    @NotNull
    @Size(min = 3, max = 255)
    private SectorEntity sector;


   /*@ManyToOne(fetch = jakarta.persistence.FetchType.EAGER)
    @JoinColumn(name = "id_sector")
    private SectorEntity sector;
 */ 

    @Email
    private String email;


    public EmpresaEntity() {
    }

    public EmpresaEntity(String nombre, String apellido1, SectorEntity sector, String email) {
        this.nombre = nombre;
        this.sector = sector;
        this.email = email;
    }

    public EmpresaEntity(Long id, String nombre, String apellido1, SectorEntity sector, String email) {
        this.id = id;
        this.nombre = nombre;
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

    public SectorEntity sector() {
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
    
}