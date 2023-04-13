package com.bagasari.sacbagaji.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "city")
public class City {

    @Id
    @Column(name = "city_name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_name")
    private Country country;

//    private Long count;

    public City(String name) {
        this.name = name;
    }
}
