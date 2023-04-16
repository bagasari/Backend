package com.bagasari.sacbagaji.model.entity;

import com.bagasari.sacbagaji.model.dto.req.FoodDTO;
import com.bagasari.sacbagaji.model.dto.req.ProductDTO;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "food")
public class Food extends Product{

    private Integer count;
    private Integer weight;
    private String latitude;
    private String longitude;

    public Food(ProductDTO productDTO, AccountBook accountBook, FoodDTO foodDTO) {
        super(productDTO, accountBook);
        this.count = foodDTO.getCount();
        this.weight = foodDTO.getWeight();
        this.latitude = foodDTO.getLatitude();
        this.longitude = foodDTO.getLongitude();
    }
}
