package com.bagasari.sacbagaji.model.entity;

import com.bagasari.sacbagaji.model.dto.ProductDTO;
import com.bagasari.sacbagaji.model.dto.TransportationDTO;
import com.bagasari.sacbagaji.model.enums.TransportType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "transportation")
public class Transportation extends Product {

    private String startLatitude;
    private String startLongitude;
    private String endLatitude;
    private String endLongitude;

    @Enumerated(value = EnumType.STRING)
    private TransportType transportType;

    public Transportation(ProductDTO productDTO, AccountBook accountBook, TransportationDTO transportationDTO) {
        super(productDTO, accountBook);
        this.startLatitude = transportationDTO.getStartLatitude();
        this.startLongitude = transportationDTO.getStartLongitude();
        this.endLatitude = transportationDTO.getEndLatitude();
        this.endLongitude = transportationDTO.getEndLongitude();
        this.transportType = transportationDTO.getTransportType();
    }
}
