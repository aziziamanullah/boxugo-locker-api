package com.unioo.titip.repository;

import com.unioo.titip.entity.LockerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LockerRepository extends JpaRepository<LockerEntity,Long> {
    List<LockerEntity> findByStatus(Boolean status);
}
