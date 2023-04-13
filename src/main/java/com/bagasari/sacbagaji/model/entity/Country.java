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
    @Column(name = "country_name")
    private String name;

//    private Long count;

}
