package com.bagasari.sacbagaji.model.dto.res;

import com.bagasari.sacbagaji.model.entity.AccountBook;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class AccountResponseDTO {

    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String city;

    public AccountResponseDTO(AccountBook accountBook) {
        this.id = accountBook.getId();
        this.name = accountBook.getName();
        this.startDate = accountBook.getStartDate();
        this.endDate = accountBook.getEndDate();
        this.city = accountBook.getDestinations().get(0).getCity().getName();
    }
}
