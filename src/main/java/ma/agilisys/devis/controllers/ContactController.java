package ma.agilisys.devis.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.agilisys.devis.dtos.ContactRequestDto;
import ma.agilisys.devis.dtos.ContactResponseDto;
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

    @PostMapping
    public ResponseEntity<ContactResponseDto> createContact(@Valid @RequestBody ContactRequestDto contactRequestDto) {
        ContactResponseDto createdContact = contactService.createContact(contactRequestDto);
        return new ResponseEntity<>(createdContact, HttpStatus.CREATED);
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<ContactResponseDto>> getContactsByClient(@PathVariable Long clientId) {
        return ResponseEntity.ok(contactService.getContactsByClient(clientId));
    }
}