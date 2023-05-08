package com.bagasari.sacbagaji.model.dto.res;

import com.bagasari.sacbagaji.model.dto.ProductListWithPurchaseDateDTO;
import com.bagasari.sacbagaji.model.entity.AccountBook;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class CurrentAccountResponseDTO {

    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer totalPrice;
    private List<ProductListWithPurchaseDateDTO> products;

    public CurrentAccountResponseDTO(AccountBook accountBook, List<ProductListWithPurchaseDateDTO> products) {
        this.name = accountBook.getName();
        this.startDate = accountBook.getStartDate();
        this.endDate = accountBook.getEndDate();
        this.totalPrice = accountBook.getTotalPrice();
        this.products = products;
    }
}
