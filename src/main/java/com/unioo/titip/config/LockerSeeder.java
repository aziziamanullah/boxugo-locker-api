package com.unioo.titip.config;

import com.unioo.titip.entity.LockerEntity;
import com.unioo.titip.entity.LockerSize;
import com.unioo.titip.repository.LockerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class LockerSeeder implements CommandLineRunner {

    @Autowired
    private LockerRepository lockerRepository;

    @Override
    public void run(String... args) throws Exception {
        if (lockerRepository.count() == 0) {
            daftarLocker("M", LockerSize.KECIL, 8);
            daftarLocker("L", LockerSize.SEDANG, 8);
            daftarLocker("XL", LockerSize.BESAR, 8);
        }
    }

    private void daftarLocker(String name, LockerSize size, int quantity) {
        for (int i = 1; i <= quantity; i++) {
            LockerEntity locker = new LockerEntity();
            locker.setLockerName(name + "-" + String.format("%02d", i)); // ini untuk penamaan locker
            locker.setStatus(true);
            locker.setSize(size);
            lockerRepository.save(locker);
        }
    }
}