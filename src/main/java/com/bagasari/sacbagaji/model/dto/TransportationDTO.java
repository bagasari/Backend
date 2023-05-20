package com.bagasari.sacbagaji.model.dto;

import com.bagasari.sacbagaji.model.enums.TransportType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@NoArgsConstructor
@Getter
public class TransportationDTO {

    private String startLatitude;
    private String startLongitude;
    private String endLatitude;
    private String endLongitude;

    @Enumerated(value = EnumType.STRING)
    private TransportType transportType;

}
