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

import com.proyecto.FormAndWork.entity.CandidaturaEntity;
import com.proyecto.FormAndWork.service.CandidaturaService;


@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/candidatura")

public class candidatura {
    @Autowired
    CandidaturaService oCandidaturaService;

    @GetMapping("")
    public ResponseEntity<Page<CandidaturaEntity>> getPage(
            Pageable oPageable,
            @RequestParam  Optional<String> filter) {
        return new ResponseEntity<Page<CandidaturaEntity>>(oCandidaturaService.getPage(oPageable, filter), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CandidaturaEntity> getCandidatura(@PathVariable Long id) {
        return new ResponseEntity<CandidaturaEntity>(oCandidaturaService.get(id), HttpStatus.OK);
    }   
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<Long>(oCandidaturaService.count(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        return new ResponseEntity<Long>(oCandidaturaService.delete(id), HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<CandidaturaEntity> create(@RequestBody CandidaturaEntity oCandidaturaEntity) {
        System.out.println("Intentando crear candidatura: " + oCandidaturaEntity);
        return new ResponseEntity<CandidaturaEntity>(oCandidaturaService.create(oCandidaturaEntity), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<CandidaturaEntity> update(@RequestBody CandidaturaEntity oCandidaturaEntity) {
        return new ResponseEntity<CandidaturaEntity>(oCandidaturaService.update(oCandidaturaEntity), HttpStatus.OK);
    }

    @PutMapping("/random/{cantidad}")
    public ResponseEntity<Long> create(@PathVariable Long cantidad) {
        return new ResponseEntity<Long>(oCandidaturaService.randomCreate(cantidad), HttpStatus.OK);
    }

    @DeleteMapping("/all")
    public ResponseEntity<Long> deleteAll() {
        return new ResponseEntity<Long>(oCandidaturaService.deleteAll(), HttpStatus.OK);
    }

}
