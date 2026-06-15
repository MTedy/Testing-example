package com.learning.courses.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CreateContactDTO implements Serializable {

    @NotBlank
    @Email
    @Schema(example = "student@example.com")
    private String email;

    @NotBlank
    @Schema(example = "ul. Warszawska 1, 00-001 Warszawa")
    private String address;

    @NotBlank
    @Pattern(regexp = "^\\+?[0-9]{9,15}$")
    @Schema(example = "+48123456789")
    private String phoneNumber;
}