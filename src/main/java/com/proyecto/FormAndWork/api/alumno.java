package com.proyecto.FormAndWork.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.FormAndWork.service.AlumnoService;
import com.proyecto.FormAndWork.entity.AlumnoEntity;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/sector")

public class alumno {
     @Autowired
    AlumnoService oAlumnoService;

    @GetMapping("")
    public ResponseEntity<Page<AlumnoEntity>> getPage(
            Pageable oPageable,
            @RequestParam  Optional<String> filter) {
        return new ResponseEntity<Page<AlumnoEntity>>(oAlumnoService.getPage(oPageable, filter), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlumnoEntity> getAlumno(@PathVariable Long id) {
        return new ResponseEntity<AlumnoEntity>(oAlumnoService.get(id), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<Long>(oAlumnoService.count(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        return new ResponseEntity<Long>(oAlumnoService.delete(id), HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<AlumnoEntity> create(@RequestBody AlumnoEntity oAlumnoEntity) {
        return new ResponseEntity<AlumnoEntity>(oAlumnoService.create(oAlumnoEntity), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<AlumnoEntity> update(@RequestBody AlumnoEntity oAlumnoEntity) {
        return new ResponseEntity<AlumnoEntity>(oAlumnoService.update(oAlumnoEntity), HttpStatus.OK);
    }

    @PutMapping("/random/{cantidad}")
    public ResponseEntity<Long> create(@PathVariable Long cantidad) {
        return new ResponseEntity<Long>(oAlumnoService.randomCreate(cantidad), HttpStatus.OK);
    }

    @DeleteMapping("/all")
    public ResponseEntity<Long> deleteAll() {
        return new ResponseEntity<Long>(oAlumnoService.deleteAll(), HttpStatus.OK);
    }

}