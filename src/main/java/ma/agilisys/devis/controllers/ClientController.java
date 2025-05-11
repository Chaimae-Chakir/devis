package ma.agilisys.devis.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.agilisys.devis.dtos.ClientPageDto;
import ma.agilisys.devis.dtos.ClientRequestDto;
import ma.agilisys.devis.dtos.ClientResponseDto;
import ma.agilisys.devis.services.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.POST})
public class ClientController {
    private final ClientService clientService;

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDto> getClientById(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.getClientById(id));
    }

    @PostMapping
    public ResponseEntity<ClientResponseDto> createClient(@Valid @RequestBody ClientRequestDto clientRequestDto) {
        ClientResponseDto createdClient = clientService.createClient(clientRequestDto);
        return new ResponseEntity<>(createdClient, HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<ClientPageDto> getAllClients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(clientService.getAllClient(page, size));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponseDto> updateClient(@PathVariable Long id, @Valid @RequestBody ClientRequestDto clientRequestDto) {
        return ResponseEntity.ok(clientService.updateClient(id, clientRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}