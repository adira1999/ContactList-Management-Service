package com.example.contactlistmanager.service;

import com.example.contactlistmanager.entity.Contact;
import com.example.contactlistmanager.exception.ContactAlreadyExistsException;
import com.example.contactlistmanager.exception.ContactLimitExceededException;
import com.example.contactlistmanager.exception.ContactNotFoundException;
import com.example.contactlistmanager.exception.InvalidPhoneNumberLengthException;
import com.example.contactlistmanager.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;


    public List<Contact> getAllContacts(){
        return contactRepository.findAll();
    }

    public Contact addContact(Contact contact) {
        if (contactRepository.existsByPhoneNumber(contact.getPhoneNumber())) {
            throw new ContactAlreadyExistsException("Contact with this phone number already exists.");
        }

        // Check if the person already has 2 contacts (based on firstName and lastName)
        long contactCount = contactRepository.countByFirstNameAndLastName(contact.getFirstName(), contact.getLastName());
        if (contactCount >= 2) {
            throw new ContactLimitExceededException("A person can only have a maximum of 2 contacts.");
        }

        if (contact.getPhoneNumber().length() != 10) {
            throw new InvalidPhoneNumberLengthException("Phone number must be exactly 10 digits long.");
        }

        return contactRepository.save(contact);
    }

    public void updateContactByDetails(String firstName, String lastName, String currentPhoneNumber, String newPhoneNumber) {

        // Find the contact by firstName, lastName, and currentPhoneNumber
        Optional<Contact> optionalContact = contactRepository.findByFirstNameAndLastNameAndPhoneNumber(firstName, lastName, currentPhoneNumber);

        if (!optionalContact.isPresent()) {

            throw new ContactNotFoundException("No contact found with the provided first name, last name, and current phone number.");
        }

        Contact contact = optionalContact.get();

        // Check if the new phone number already exists for another contact
        Optional<Contact> contactWithNewPhoneNumber = contactRepository.findByPhoneNumber(newPhoneNumber);
        if (contactWithNewPhoneNumber.isPresent() && !contactWithNewPhoneNumber.get().equals(contact)) {
            // Throw custom exception if another contact has the same new phone number
            throw new ContactAlreadyExistsException("The new phone number is already in use by another contact.");
        }


        contact.setPhoneNumber(newPhoneNumber);
        contactRepository.save(contact);

    }

    public void deleteContactByPhoneNumber(String phoneNumber) {

        Contact contact = contactRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new ContactNotFoundException("Contact with phone number " + phoneNumber + " not found"));

        contactRepository.delete(contact);
    }

    public Object getContactByPhoneNumber(String phoneNumber) {

        return contactRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new ContactNotFoundException("Contact with phone number " + phoneNumber + " not found"));
    }

    // Search by first name and last name
    public List<Contact> getContactByFirstNameAndLastName(String firstName, String lastName) {
        List<Contact> contacts = contactRepository.findByFirstNameAndLastName(firstName, lastName);
        if (contacts.isEmpty()) {
            throw new ContactNotFoundException("No contacts found for " + firstName + " " + lastName);
        }
        return contacts;
    }
}
