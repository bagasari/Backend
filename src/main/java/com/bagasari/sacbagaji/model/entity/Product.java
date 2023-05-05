package com.bagasari.sacbagaji.model.entity;

import com.bagasari.sacbagaji.model.dto.ProductDTO;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product")
@AllArgsConstructor
@Getter
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_book_id")
    private AccountBook accountBook;

    private String name;
    private Integer price;

    @Column(name = "purchase_date")
    private LocalDate purchaseDate;

    private String detail;
    private String country;
    private String city;

    public Product(ProductDTO productDTO, AccountBook accountBook) {
        this.accountBook = accountBook;
        this.name = productDTO.getName();
        this.price = productDTO.getPrice();
        this.purchaseDate = productDTO.getPurchaseDate();
        this.detail = productDTO.getDetail();
        this.country = productDTO.getCountry();
        this.city = productDTO.getCity();
    }
}
