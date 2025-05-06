package ma.agilisys.devis.controllers;

import jakarta.validation.Valid;
import ma.agilisys.devis.dtos.ClientDto;
import ma.agilisys.devis.dtos.ClientRequest;
import ma.agilisys.devis.services.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public ResponseEntity<List<ClientDto>> getAllClients() {
        return ResponseEntity.ok(clientService.getAllClients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> getClientById(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.getClientById(id));
    }

    @PostMapping
    public ResponseEntity<ClientDto> createClient(@Valid @RequestBody ClientRequest clientRequest) {
        ClientDto createdClient = clientService.createClient(clientRequest);
        return new ResponseEntity<>(createdClient, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDto> updateClient(
            @PathVariable Long id,
            @Valid @RequestBody ClientRequest clientRequest) {
        return ResponseEntity.ok(clientService.updateClient(id, clientRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<ClientDto>> searchClients(@RequestParam String term) {
        return ResponseEntity.ok(clientService.searchClients(term));
    }

    @GetMapping("/exists/ice/{ice}")
    public ResponseEntity<Boolean> checkIceExists(@PathVariable String ice) {
        return ResponseEntity.ok(clientService.existsByIce(ice));
    }
}