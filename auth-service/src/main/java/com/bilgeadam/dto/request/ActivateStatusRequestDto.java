package com.bilgeadam.dto.request;

import com.bilgeadam.repository.entity.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivateStatusRequestDto {
    Integer id;
    String activationCode;
}
