package com.unioo.titip.entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table (name = "Locker")

public class LockerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String lockerName;

    @Column(nullable = false)
    private Boolean status;
//@Enumerated(EnumType.STRING) memberi tahu Hibernate, "simpan nilai enum ini sebagai teks nama aslinya di database, jangan sebagai angka urutan" — supaya data tetap benar meskipun urutan enum berubah nanti.
    @Enumerated(EnumType.STRING) //
    @Column(nullable = false)
    private LockerSize size;

}
