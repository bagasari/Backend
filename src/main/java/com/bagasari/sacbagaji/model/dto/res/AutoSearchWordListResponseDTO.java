package com.bagasari.sacbagaji.model.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class AutoSearchWordListResponseDTO {

    private List<String> name;

}
