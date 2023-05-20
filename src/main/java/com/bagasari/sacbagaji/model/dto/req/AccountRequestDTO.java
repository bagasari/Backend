package com.bagasari.sacbagaji.model.dto.req;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class AccountRequestDTO {

    @NotBlank
    private String name;

    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isPrivate;
    private List<String> cityList;

    public AccountRequestDTO(String name, LocalDate startDate, LocalDate endDate, Boolean isPrivate, List<String> cityList) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isPrivate = isPrivate;
        this.cityList = cityList;
    }
}
