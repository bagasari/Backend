package com.bagasari.sacbagaji.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    @JoinColumn(name = "member_id")
    private Member member;

    private String destination;

    @Column(name = "is_private")
    private Boolean isPrivate;

    @OneToMany(mappedBy = "accountBook")
    private List<Product> products = new ArrayList<>();
}
