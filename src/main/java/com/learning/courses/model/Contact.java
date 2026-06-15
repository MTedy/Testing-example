package com.learning.courses.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "contact")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contact_id_seq")
    @SequenceGenerator(name = "contact_id_seq", sequenceName = "contact_id_seq", allocationSize = 1)
    private Long id;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String address;

    @NotBlank
    @Pattern(regexp = "^\\+?[0-9]{9,15}$")
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id", nullable = false)
    private Person person;
}
