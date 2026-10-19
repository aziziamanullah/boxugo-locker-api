package com.unioo.titip.service;

import com.unioo.titip.entity.LockerSize;
import com.unioo.titip.repository.CustRepository;
import com.unioo.titip.repository.LockerRepository;

import com.unioo.titip.repository.SistemRepository;
import com.unioo.titip.entity.CustEntity;
import com.unioo.titip.entity.LockerEntity;
import com.unioo.titip.entity.SistemEntity;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

public interface SistemService {
    SistemEntity findById(Long id);
    List<SistemEntity> findAll();
    SistemEntity titipBarang(Long custId,Long lockerId,int hourlyRental , boolean paid);
    SistemEntity ambilBarang(String code);
    SistemEntity bayarDenda(String code);
    void deleteById(Long id);

}

@Service
class SistemServiceImpl implements SistemService {

    private static final long PENALTY =25000;

    @Autowired
    private SistemRepository sistemRepository;

    @Autowired
    private LockerRepository lockerRepository;

    @Autowired
    private CustRepository custRepository;

    @Override
    public SistemEntity findById(Long id) {
        return sistemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Locker tidak ditemukan"));
    }

    @Override
    public List<SistemEntity> findAll() {
        return sistemRepository.findAll();
    }


    @Transactional //
    @Override
    public SistemEntity titipBarang(Long custId, Long lockerId, int hourlyRental, boolean paid) {
        if (!paid){
            throw new RuntimeException("Pembayaran belum dikonfirmasi");
        }

        CustEntity customer = custRepository.findById(custId)
                .orElseThrow(() -> new RuntimeException("Customer tidak ditemukan"));
        LockerEntity locker = lockerRepository.findById(lockerId)
                .orElseThrow(() -> new RuntimeException("Locker tidak ditemukan"));

        if (!locker.getStatus()) {
            throw new RuntimeException("Locker sudah diisi");
        }


        long payment = hitungTarif(locker.getSize(), hourlyRental);
        LocalDateTime lockTime = LocalDateTime.now();

        SistemEntity sistemEntity = new SistemEntity();
        sistemEntity.setCustomer(customer);
        sistemEntity.setLocker(locker);
        sistemEntity.setPayment(payment);
        sistemEntity.setLockTime(lockTime);
        sistemEntity.setExpireTime(lockTime.plusHours(hourlyRental));
        sistemEntity.setCode(generateCode());

        locker.setStatus(false);
        lockerRepository.save(locker);

        return sistemRepository.save(sistemEntity);
    }

    //UNTUK CODE
    private String generateCode() {
        int number = (int) (Math.random() * 900000 + 100000);
        return String.valueOf(number);
    }




    @Override
    public SistemEntity ambilBarang(String code) {
        SistemEntity sistem = sistemRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Kode tidak valid"));

        LocalDateTime sekarang = LocalDateTime.now();
        boolean late = sekarang.isAfter(sistem.getExpireTime());
        boolean UnpaidFine= sistem.getPenaltyPaid() == null || !sistem.getPenaltyPaid();


        if(late && UnpaidFine){
            sistem.setPenalty(PENALTY);
            sistem.setPenaltyPaid(false);
            sistemRepository.save(sistem);
            throw new RuntimeException("Terlambat mengambil barang. Denda Rp" + PENALTY + " harus dibayar dulu.");
        }

        LockerEntity locker = sistem.getLocker();
        locker.setStatus(true);
        lockerRepository.save(locker);


        sistem.setTakeTime(sekarang);///
        return sistemRepository.save(sistem);
    }

    @Override
    public SistemEntity bayarDenda(String code){
        SistemEntity sistem = sistemRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Kode tidak valid"));

        if(sistem.getPenalty() == null){
            throw new RuntimeException("Tidak ada denda untuk transaksi ini");
        }

        sistem.setPenaltyPaid(true);
        return sistemRepository.save(sistem);
    }


    @Override
    public void deleteById(Long id) {
        SistemEntity sistem = sistemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaksi tidak ditemukan"));

        LockerEntity locker = sistem.getLocker();
        locker.setStatus(true);
        lockerRepository.save(locker);

        sistemRepository.deleteById(id);
    }


    private int hitungTarif(LockerSize size, int hourlyRental) {
        switch (size) {
            case KECIL:
                switch (hourlyRental) {
                    case 1: // 1jam
                        return 10000;
                    case 3:
                        return 20000;
                    case 6:
                        return 40000;
                    case 12:
                        return 50000;
                    default:
                        throw new RuntimeException("Paket tidak ditemukan");

                }
            case SEDANG:
                switch (hourlyRental) {
                    case 1:
                        return 15000;
                    case 3:
                        return 30000;
                    case 6:
                        return 50000;
                    case 12:
                        return 70000;
                    default:
                        throw new RuntimeException("Paket tidak ditemukan");

                }
            case BESAR:
                switch (hourlyRental) {
                    case 1:
                        return 20000;
                    case 3:
                        return 45000;
                    case 6:
                        return 70000;
                    case 12:
                        return 100000;
                    default:
                        throw new RuntimeException("Paket tidak ditemukan");
                }
            default:
                throw new RuntimeException("Ukuran Tidak ditemukan");
        }
    }


}