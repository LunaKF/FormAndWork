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

import com.proyecto.FormAndWork.entity.EmpresaEntity;
import com.proyecto.FormAndWork.entity.OfertaEntity;
import com.proyecto.FormAndWork.service.OfertaService;


@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/oferta")

public class oferta {
    @Autowired
    OfertaService oOfertaService;

    @GetMapping("")
    public ResponseEntity<Page<OfertaEntity>> getPage(
            Pageable oPageable,
            @RequestParam  Optional<String> filter) {
        return new ResponseEntity<Page<OfertaEntity>>(oOfertaService.getPage(oPageable, filter), HttpStatus.OK);
    }

    @GetMapping("xempresa/{id}")
    public ResponseEntity<Page<OfertaEntity>> getPageXempresa(
            @PathVariable Long id,
            Pageable oPageable,
            @RequestParam Optional<String> filter) {
        return new ResponseEntity<Page<OfertaEntity>>(oOfertaService.getPageXempresa(oPageable, filter, id), HttpStatus.OK);
    } 

        @GetMapping("xsector/{id}")
    public ResponseEntity<Page<OfertaEntity>> getPageXsector(
            @PathVariable Long id,
            Pageable oPageable,
            @RequestParam Optional<String> filter) {
        return new ResponseEntity<Page<OfertaEntity>>(oOfertaService.getPageXsector(oPageable, filter, id), HttpStatus.OK);
    } 

    @GetMapping("/{id}")
    public ResponseEntity<OfertaEntity> getOferta(@PathVariable Long id) {
        return new ResponseEntity<OfertaEntity>(oOfertaService.get(id), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<Long>(oOfertaService.count(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        return new ResponseEntity<Long>(oOfertaService.delete(id), HttpStatus.OK);
    }

@PostMapping("") // Crear
public ResponseEntity<OfertaEntity> create(@RequestBody OfertaEntity oOfertaEntity) {
    return new ResponseEntity<>(oOfertaService.create(oOfertaEntity), HttpStatus.CREATED);
}

@PutMapping("/{id}") // Actualizar
public ResponseEntity<OfertaEntity> update(@PathVariable Long id, @RequestBody OfertaEntity oOfertaEntity) {
    oOfertaEntity.setId(id);
    return new ResponseEntity<>(oOfertaService.update(oOfertaEntity), HttpStatus.OK);
}


    @PostMapping("/random/{cantidad}")
    public ResponseEntity<Long> create(@PathVariable Long cantidad) {
        return new ResponseEntity<Long>(oOfertaService.randomCreate(cantidad), HttpStatus.OK);
    }

    @DeleteMapping("/all")
    public ResponseEntity<Long> deleteAll() {
        return new ResponseEntity<Long>(oOfertaService.deleteAll(), HttpStatus.OK);
    }

}