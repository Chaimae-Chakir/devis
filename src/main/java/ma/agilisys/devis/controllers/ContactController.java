package ma.agilisys.devis.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.agilisys.devis.dtos.ContactDto;
import ma.agilisys.devis.dtos.ContactRequest;
import ma.agilisys.devis.services.ContactService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
@RequiredArgsConstructor
public class ContactController {
    private final ContactService contactService;

    @GetMapping
    public ResponseEntity<List<ContactDto>> getAllContacts() {
        return ResponseEntity.ok(contactService.getAllContacts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactDto> getContactById(@PathVariable Long id) {
        return ResponseEntity.ok(contactService.getContactById(id));
    }

    @PostMapping
    public ResponseEntity<ContactDto> createContact(@Valid @RequestBody ContactRequest contactRequest) {
        ContactDto createdContact = contactService.createContact(contactRequest);
        return new ResponseEntity<>(createdContact, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactDto> updateContact(
            @PathVariable Long id,
            @Valid @RequestBody ContactRequest contactRequest) {
        return ResponseEntity.ok(contactService.updateContact(id, contactRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        contactService.deleteContact(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<ContactDto>> getContactsByClient(@PathVariable Long clientId) {
        return ResponseEntity.ok(contactService.getContactsByClient(clientId));
    }
}