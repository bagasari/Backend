package com.bagasari.sacbagaji.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "country")
@Builder
@AllArgsConstructor
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "country_id")
    private Long id;
    private String name;
    private Long count;

    @OneToMany(mappedBy = "country")
    private List<City> cities = new ArrayList<>();
}
