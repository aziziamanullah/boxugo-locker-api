package com.unioo.titip.service;


import com.unioo.titip.repository.CustRepository;
import com.unioo.titip.entity.CustEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CustService {

    CustEntity saveNewCust(CustEntity dto);
    List<CustEntity> findAllCust();
    CustEntity findCustById(Long id);
    CustEntity updateCust(CustEntity cust, Long id);
    void deleteCustById(Long id);
    void deleteAllCust();

}
@Service
class CustServiceImpl implements CustService {
    @Autowired
    private CustRepository custRepository;

    @Override
    public CustEntity saveNewCust(CustEntity dto) {
        return custRepository.save(dto);
    }

    @Override
    public List<CustEntity> findAllCust() {
        return custRepository.findAll();
    }

    @Override
    public CustEntity findCustById(Long id) {
        return custRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer tidak ditemukan"));
    }

    @Override
    public CustEntity updateCust(CustEntity cust, Long id) {
        CustEntity data =  custRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer tidak ditemukan"));

        data.setName(cust.getName());
        data.setEmail(cust.getEmail());
        data.setPhone(cust.getPhone());

        return custRepository.save(data);
    }

    @Override
    public void deleteCustById(Long id) {
        custRepository.deleteById(id);

    }

    @Override
    public void deleteAllCust() {
        custRepository.deleteAll();
    }
}