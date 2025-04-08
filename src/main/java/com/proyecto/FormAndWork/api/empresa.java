package com.proyecto.FormAndWork.api;

import java.util.List;
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

import com.proyecto.FormAndWork.entity.EmpresaEntity;
import com.proyecto.FormAndWork.service.EmpresaService;


@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/empresa")

public class empresa {
    @Autowired
    EmpresaService oEmpresaService;

    @GetMapping("")
    public ResponseEntity<Page<EmpresaEntity>> getPage(
            Pageable oPageable,
            @RequestParam  Optional<String> filter) {
        return new ResponseEntity<Page<EmpresaEntity>>(oEmpresaService.getPage(oPageable, filter), HttpStatus.OK);
    }

    @GetMapping("xsector/{id}")
    public ResponseEntity<Page<EmpresaEntity>> getPageXsector(
            @PathVariable Long id,
            Pageable oPageable,
            @RequestParam Optional<String> filter) {
        return new ResponseEntity<Page<EmpresaEntity>>(oEmpresaService.getPageXsector(oPageable, filter, id), HttpStatus.OK);
    }   

    @GetMapping("/{id}")
    public ResponseEntity<EmpresaEntity> getEmpresa(@PathVariable Long id) {
        return new ResponseEntity<EmpresaEntity>(oEmpresaService.get(id), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<Long>(oEmpresaService.count(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        return new ResponseEntity<Long>(oEmpresaService.delete(id), HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<EmpresaEntity> create(@RequestBody EmpresaEntity oEmpresaEntity) {
        return new ResponseEntity<EmpresaEntity>(oEmpresaService.create(oEmpresaEntity), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<EmpresaEntity> update(@RequestBody EmpresaEntity oEmpresaEntity) {
        return new ResponseEntity<EmpresaEntity>(oEmpresaService.update(oEmpresaEntity), HttpStatus.OK);
    }

    @PutMapping("/random/{cantidad}")
    public ResponseEntity<Long> create(@PathVariable Long cantidad) {
        return new ResponseEntity<Long>(oEmpresaService.randomCreate(cantidad), HttpStatus.OK);
    }

    @DeleteMapping("/all")
    public ResponseEntity<Long> deleteAll() {
        return new ResponseEntity<Long>(oEmpresaService.deleteAll(), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<EmpresaEntity>> getAll() {
    return new ResponseEntity<>(oEmpresaService.getAllOrdered(), HttpStatus.OK);
    }

}