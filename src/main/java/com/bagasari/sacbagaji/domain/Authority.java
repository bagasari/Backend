package com.bagasari.sacbagaji.domain;

import lombok.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "authority")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Authority {

    @Id
    @Column(name = "authority_name", length = 50)
    private String authorityName;
}
