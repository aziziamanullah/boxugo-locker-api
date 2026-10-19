package com.unioo.titip.controller;


import com.unioo.titip.entity.SistemEntity;
import com.unioo.titip.service.SistemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/sistem")


public class SistemController {

    @Autowired
    private SistemService sistemService;



    @PostMapping
    public SistemEntity saveNewSistem(
            @RequestParam Long custId,
            @RequestParam Long lockerId,
            @RequestParam int hourlyRental,
            @RequestParam boolean paid
    ) {
        return sistemService.titipBarang(custId, lockerId, hourlyRental, paid);
    }
    @GetMapping
    public List<SistemEntity> findAll() {
        return sistemService.findAll();
    }
    @GetMapping("/{id}")
    public SistemEntity findSistemById(@PathVariable Long id) {
        return sistemService.findById(Long.valueOf(id));
    }
    @PutMapping("/{code}")
    public SistemEntity ambilBarang(@PathVariable String code) {
        return sistemService.ambilBarang(code);
    }

    @PutMapping("{code}/denda")
    public SistemEntity bayarDenda(@PathVariable String code) {
        return sistemService.bayarDenda(code);
    }

    @DeleteMapping("/{id}")
    public void deleteSistemById(@PathVariable Long id) {
        sistemService.deleteById(id);
    }

}
