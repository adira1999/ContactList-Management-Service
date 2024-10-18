package com.example.contactlistmanager.repository;

import com.example.contactlistmanager.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ContactRepository extends JpaRepository<Contact,Long> {
    boolean existsByPhoneNumber(String phoneNumber);

    // Check how many contacts exist for a specific firstName and lastName
    @Query("SELECT COUNT(c) FROM Contact c WHERE c.firstName = :firstName AND c.lastName = :lastName")
    long countByFirstNameAndLastName(String firstName, String lastName);

    Optional<Contact> findByFirstNameAndLastNameAndPhoneNumber(String firstName, String lastName, String phoneNumber);

    Optional<Contact> findByPhoneNumber(String newPhoneNumber);

    List<Contact> findByFirstNameAndLastName(String firstName, String lastName);
}
