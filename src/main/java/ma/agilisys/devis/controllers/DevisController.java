package ma.agilisys.devis.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.agilisys.devis.dtos.DevisRequestDTO;
import ma.agilisys.devis.dtos.DevisResponseDTO;
import ma.agilisys.devis.services.DevisPdfGenerator;
import ma.agilisys.devis.services.DevisService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/devis")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.POST})
public class DevisController {
    private final DevisService devisService;
    private final DevisPdfGenerator pdfGenerator;

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

    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> generatePdf(@PathVariable Long id) {
        try {
            byte[] pdf = pdfGenerator.generateDevisPdf(id);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=devis_" + id + ".pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdf);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur lors de la génération du PDF");
        }
    }
}