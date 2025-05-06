package ma.agilisys.devis.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.agilisys.devis.dtos.DevisRequestDTO;
import ma.agilisys.devis.dtos.DevisResponseDTO;
import ma.agilisys.devis.services.DevisService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/devis")
@RequiredArgsConstructor
public class DevisController {
    private final DevisService devisService;

    @GetMapping
    public ResponseEntity<List<DevisResponseDTO>> getAllDevis(@RequestParam(required = false) Long clientId) {
        return ResponseEntity.ok(devisService.getAllDevis(clientId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DevisResponseDTO> getDevisById(@PathVariable Long id) {
        return ResponseEntity.ok(devisService.getDevisById(id));
    }

    @PostMapping
    public ResponseEntity<DevisResponseDTO> createDevis(@Valid @RequestBody DevisRequestDTO devisRequestDTO) {
        return ResponseEntity.ok(devisService.createDevis(devisRequestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DevisResponseDTO> updateDevis(@PathVariable Long id,
                                                        @Valid @RequestBody DevisRequestDTO devisRequestDTO) {
        return ResponseEntity.ok(devisService.updateDevis(id, devisRequestDTO));
    }

    @PutMapping("/{id}/validate")
    public ResponseEntity<DevisResponseDTO> validateDevis(@PathVariable Long id,
                                                          @RequestParam String validatedBy) {
        return ResponseEntity.ok(devisService.validateDevis(id, validatedBy));
    }

    @PostMapping("/{id}/duplicate")
    public ResponseEntity<DevisResponseDTO> duplicateDevis(@PathVariable Long id) {
        return ResponseEntity.ok(devisService.duplicateDevis(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevis(@PathVariable Long id) {
        devisService.deleteDevis(id);
        return ResponseEntity.noContent().build();
    }
}