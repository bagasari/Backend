package com.bagasari.sacbagaji.model.dto.req;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDTO {

    @NotNull
    @Size(min = 3, max = 50)
    private String email;
    @NotNull
    @Size(min = 3, max = 100)
    private String password;
    @NotNull
    @Size(min = 3, max = 50)
    private String name;

}
