package com.bagasari.sacbagaji.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "account_book")
public class AccountBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_book_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String name;

    private LocalDate startDate;
    private LocalDate endDate;

    @Column(name = "is_private")
    private Boolean isPrivate;

    private int totalPrice;

    @OneToMany(mappedBy = "accountBook")
    private List<Product> products = new ArrayList<>();
}
