package com.bilgeadam.dto.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponseDto {
    private Integer id;
    private String username;
    private String activationCode;
}
