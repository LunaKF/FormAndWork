package com.proyecto.FormAndWork.entity;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
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
@Table(name = "candidatura")
public class CandidaturaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha", nullable = false, columnDefinition = "DATE")
    private LocalDate fecha;


   @ManyToOne(fetch = jakarta.persistence.FetchType.EAGER)
    @JoinColumn(name = "id_alumno")
    private AlumnoEntity alumno;
 
    @ManyToOne(fetch = jakarta.persistence.FetchType.EAGER)
    @JoinColumn(name = "id_oferta")
    private OfertaEntity oferta;



    public CandidaturaEntity() {
    }

    public CandidaturaEntity(LocalDate fecha, AlumnoEntity alumno, OfertaEntity oferta) {
        this.fecha = fecha;
        this.alumno = alumno;
        this.oferta = oferta;
    }

    public CandidaturaEntity(Long id, LocalDate fecha, AlumnoEntity alumno, OfertaEntity oferta) {
        this.id = id;
        this.fecha = fecha;
        this.alumno = alumno;
        this.oferta = oferta;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }    

    public LocalDate getFecha() {
        return fecha;
    }    

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }    

    public AlumnoEntity getAlumno() {
        return alumno;
    }

    public void setAlumno(AlumnoEntity alumno) {
        this.alumno = alumno;
    }

    public OfertaEntity getOferta() {
        return oferta;
    }

    public void setOferta(OfertaEntity oferta) {
        this.oferta = oferta;
    }       

}