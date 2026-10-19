package com.unioo.titip.controller;


import com.unioo.titip.entity.CustEntity;
import com.unioo.titip.service.CustService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/customer")
public class CustController {

    @Autowired
    private CustService custService;

    @PostMapping
    public CustEntity saveNewCust(@RequestBody CustEntity dto) {
        return custService.saveNewCust(dto);
    }

    @GetMapping
    public List<CustEntity> findAllCust() {
        return custService.findAllCust();
    }
    @GetMapping("/{id}")
    public CustEntity findCustById(@PathVariable Long id) {
        return custService.findCustById(id);
    }

    @PutMapping("/{id}")
    public CustEntity updateCust(@PathVariable Long id, @RequestBody CustEntity dto) {
        return custService.updateCust(dto, id);
    }

    @DeleteMapping("/{id}")
    public void deleteCust(@PathVariable Long id) {
        custService.deleteCustById(id);
    }

    @DeleteMapping
    void deleteAllCust() {
        custService.deleteAllCust();
    }



}
