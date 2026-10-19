package com.unioo.titip.entity;



import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Sistem")
public class SistemEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private CustEntity customer;

    @ManyToOne
    private LockerEntity locker;

    private LocalDateTime lockTime;
    private  LocalDateTime expireTime;
    private LocalDateTime takeTime;
    private Long payment;

    private Long penalty;
    private Boolean penaltyPaid;

    @Column(unique = true)
    private String code;

}
