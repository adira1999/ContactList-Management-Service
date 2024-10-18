package com.example.contactlistmanager.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

    @Entity
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Table(name = "contact")
    public class Contact {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;


        @NotNull(message = "First Name cannot be null")
        @Size(min = 2, max = 30, message = "First Name must be between 2 and 30 characters")
        @Column(nullable = false)
        private String firstName;

        @NotNull(message = "Last Name cannot be null")
        @Size(min = 2, max = 30, message = "Last Name must be between 2 and 30 characters")
        @Column(nullable = false)
        private String lastName;

        @NotNull(message = "Phone number is required")
        @Pattern(regexp = "^\\d{10}$", message = "Phone number must be exactly 10 digits")
        @Column(unique = true)
        private String phoneNumber;

        @Email(message = "Email should be valid")
        private String email;

        @Column(name = "created_at", updatable = false)
        private LocalDateTime createdAt;

        @Column(name = "updated_at")
        private LocalDateTime updatedAt;

        @PrePersist
        protected void onCreate() {
            createdAt = LocalDateTime.now();
            updatedAt = LocalDateTime.now();
        }

        @PreUpdate
        protected void onUpdate() {
            updatedAt = LocalDateTime.now();
        }
    }
