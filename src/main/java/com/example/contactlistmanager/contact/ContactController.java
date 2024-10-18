package com.example.contactlistmanager.contact;

import com.example.contactlistmanager.entity.Contact;
import com.example.contactlistmanager.entity.UpdatePhoneRequest;
import com.example.contactlistmanager.exception.ContactAlreadyExistsException;
import com.example.contactlistmanager.exception.ContactLimitExceededException;
import com.example.contactlistmanager.exception.ContactNotFoundException;
import com.example.contactlistmanager.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    //swagger = http://localhost:8787/swagger-ui/index.html

    @Autowired
    private ContactService contactService;


    @GetMapping
    public List<Contact> getAllContacts(){
        return contactService.getAllContacts();
    }

    @PostMapping("/addContact")
    public ResponseEntity<String> addContact(@Valid @RequestBody Contact contact){
        try {
            contactService.addContact(contact);
            return ResponseEntity.ok("Contact added successfully!");
        } catch (ContactLimitExceededException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (ContactAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the contact.");
        }
    }


    // Update contact by firstName, lastName, and phoneNumber
    @PutMapping("/updateContact")
    public ResponseEntity<String> updateContact(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String currentPhoneNumber,
            @Valid @RequestBody UpdatePhoneRequest updatePhoneRequest) {
        try {
            String newPhoneNumber = updatePhoneRequest.getNewPhoneNumber();

            contactService.updateContactByDetails(firstName, lastName, currentPhoneNumber, newPhoneNumber);
            return ResponseEntity.ok("Contact updated successfully!");
        } catch (ContactNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ContactAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the contact.");
        }
    }


    @DeleteMapping("/deleteContact/{phoneNumber}")
    public ResponseEntity<String> deleteContact(@PathVariable String phoneNumber) {
        try {
            contactService.deleteContactByPhoneNumber(phoneNumber);
            return ResponseEntity.ok("Contact deleted successfully");
        } catch (ContactNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Search Contact by Phone Number or First and Last Name
    @GetMapping("/search")
    public ResponseEntity<?> searchContact(
            @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "lastName", required = false) String lastName) {

        if (phoneNumber != null) {
            return ResponseEntity.ok(contactService.getContactByPhoneNumber(phoneNumber));
        } else if (firstName != null && lastName != null) {
            return ResponseEntity.ok(contactService.getContactByFirstNameAndLastName(firstName, lastName));
        } else {
            return ResponseEntity.badRequest().body("Please provide phone number or first and last name to search");
        }
    }

}
