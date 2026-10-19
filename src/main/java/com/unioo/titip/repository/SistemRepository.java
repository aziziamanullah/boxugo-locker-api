package com.unioo.titip.repository;

import com.unioo.titip.entity.SistemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SistemRepository extends JpaRepository<SistemEntity,Long> {
    Optional<SistemEntity> findByCode(String code);
}
