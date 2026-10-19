package com.unioo.titip.controller;


import com.unioo.titip.entity.LockerEntity;
import com.unioo.titip.service.LockerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/locker")
public class LockerController {


    @Autowired
    private LockerService lockerService;

    @PostMapping
    public LockerEntity save(@RequestBody LockerEntity lockerEntity ) {
        return lockerService.saveLocker(lockerEntity);
    }
    @GetMapping
    public List<LockerEntity> findAll() {
        return lockerService.getAllLockers();
    }
    @GetMapping("/available")
    public List<LockerEntity> findAllAvailable() {
        return lockerService.findAvailableLockers();
    }
    @GetMapping("/{id}")
    public LockerEntity findById(@PathVariable Long id) {
        return lockerService.getLocker(id);
    }
}

