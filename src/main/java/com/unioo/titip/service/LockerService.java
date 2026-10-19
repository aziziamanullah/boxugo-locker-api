package com.unioo.titip.service;

import com.unioo.titip.repository.LockerRepository;
import com.unioo.titip.entity.LockerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface LockerService {
    LockerEntity getLocker(Long id);
    List<LockerEntity> getAllLockers();
    List<LockerEntity> findAvailableLockers();
    LockerEntity saveLocker(LockerEntity lockerEntity);

}
@Service
class LockerServiceImpl implements LockerService {
    @Autowired
    private LockerRepository lockerRepository;

    @Override
    public LockerEntity saveLocker(LockerEntity lockerEntity) {
        return lockerRepository.save(lockerEntity);
    }
    @Override
    public LockerEntity getLocker(Long id) {
        return lockerRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Locker tidak ditemukan"));
    }
    @Override
    public List<LockerEntity> getAllLockers() {
    return lockerRepository.findAll();
    }

    @Override
    public List<LockerEntity> findAvailableLockers() {
        return lockerRepository.findByStatus(true);
    }


}