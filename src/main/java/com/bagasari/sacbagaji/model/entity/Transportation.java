package com.bagasari.sacbagaji.model.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "transportation")
public class Transportation extends Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transportation_id")
    private Long id;

    private String startLatitude;
    private String startLongitude;
    private String endLatitude;
    private String endLongitude;

    @Enumerated(value = EnumType.STRING)
    private TransportType transportType;
}
