package com.bagasari.sacbagaji.model.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_book_id")
    private AccountBook accountBook;

    private String name;
    private int price;
    private String location;

    @Column(name = "purchase_date")
    private LocalDate purchaseDate;

    private String content;
    private String country;
    private String city;

}
