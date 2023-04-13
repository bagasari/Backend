package com.bagasari.sacbagaji.model.dto.req;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class AccountRequestDto {

    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isPrivate;

    public AccountRequestDto(String name, LocalDate startDate, LocalDate endDate, Boolean isPrivate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isPrivate = isPrivate;
    }
}
