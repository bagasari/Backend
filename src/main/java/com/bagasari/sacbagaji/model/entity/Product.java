package com.bagasari.sacbagaji.model.entity;

import com.bagasari.sacbagaji.model.dto.ProductDTO;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "products")
@AllArgsConstructor
@Getter
public abstract class Product {

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
    private Long likeCount;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductLike> productLikes = new ArrayList<>();

    /**
     * product type 가져오는 메서드
     */
    public String getProductType(Product product) {
        if (product instanceof Food) {
            return "Food";
        } else {
            return "Transportation";
        }
    }

    public Product(ProductDTO productDTO, AccountBook accountBook) {
        this.accountBook = accountBook;
        this.name = productDTO.getName();
        this.price = productDTO.getPrice();
        this.purchaseDate = productDTO.getPurchaseDate();
        this.detail = productDTO.getDetail();
        this.country = productDTO.getCountry();
        this.city = productDTO.getCity();
        this.likeCount = 0L;
    }

    public void updateLike(Long like) {
        this.likeCount += like;
    }
}
