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

import com.proyecto.FormAndWork.entity.SectorEntity;
import com.proyecto.FormAndWork.service.SectorService;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/sector")

public class sector {
    @Autowired
    SectorService oSectorService;

    @GetMapping("")
    public ResponseEntity<Page<SectorEntity>> getPage(
            Pageable oPageable,
            @RequestParam  Optional<String> filter) {
        return new ResponseEntity<Page<SectorEntity>>(oSectorService.getPage(oPageable, filter), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SectorEntity> getSector(@PathVariable Long id) {
        return new ResponseEntity<SectorEntity>(oSectorService.get(id), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<Long>(oSectorService.count(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        return new ResponseEntity<Long>(oSectorService.delete(id), HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<SectorEntity> create(@RequestBody SectorEntity oSectorEntity) {
        return new ResponseEntity<SectorEntity>(oSectorService.create(oSectorEntity), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<SectorEntity> update(@RequestBody SectorEntity oSectorEntity) {
        return new ResponseEntity<SectorEntity>(oSectorService.update(oSectorEntity), HttpStatus.OK);
    }

    @DeleteMapping("/all")
    public ResponseEntity<Long> deleteAll() {
        return new ResponseEntity<Long>(oSectorService.deleteAll(), HttpStatus.OK);
    }

}