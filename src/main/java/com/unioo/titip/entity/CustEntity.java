package com.unioo.titip.entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table (name = "Customer")

public class CustEntity {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phone;
}
