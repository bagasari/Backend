package com.bagasari.sacbagaji.model.dto.res;

import com.bagasari.sacbagaji.model.dto.ProductListWithPurchaseDateDTO;
import com.bagasari.sacbagaji.model.entity.AccountBook;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class AccountProductListResponseDTO {

    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate endDate;

    private Integer totalPrice;
    private List<ProductListWithPurchaseDateDTO> productsByDate;

    public AccountProductListResponseDTO(AccountBook accountBook, List<ProductListWithPurchaseDateDTO> products) {
        this.name = accountBook.getName();
        this.startDate = accountBook.getStartDate();
        this.endDate = accountBook.getEndDate();
        this.totalPrice = accountBook.getTotalPrice();
        this.productsByDate = products;
    }
}
