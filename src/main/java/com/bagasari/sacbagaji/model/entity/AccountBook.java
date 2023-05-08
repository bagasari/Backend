package com.bagasari.sacbagaji.model.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
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

    private Integer totalPrice;

    @CreationTimestamp
    @Column(name = "create_time")
    private LocalDateTime createTime;

    @UpdateTimestamp
    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @OneToMany(mappedBy = "accountBook")
    private List<Destination> destinations = new ArrayList<>();

    public void updateTotalPrice(AccountBook accountBook, Integer totalPrice) {
        this.id = accountBook.getId();
        this.user = accountBook.getUser();
        this.name = accountBook.getName();
        this.startDate = accountBook.getStartDate();
        this.endDate = accountBook.getEndDate();
        this.isPrivate = accountBook.getIsPrivate();
        this.totalPrice = totalPrice;
        this.createTime = accountBook.getCreateTime();
    }
}
