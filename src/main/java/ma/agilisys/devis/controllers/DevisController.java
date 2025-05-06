package ma.agilisys.devis.controllers;

import lombok.RequiredArgsConstructor;
import ma.agilisys.devis.dtos.DevisDTO;
import ma.agilisys.devis.services.DevisService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/devis")
@RequiredArgsConstructor
public class DevisController {
    private final DevisService devisService;

    @PostMapping
    public ResponseEntity<DevisDTO> createDevis(@RequestBody DevisDTO devisDTO) {
        return ResponseEntity.ok(devisService.createDevis(devisDTO));
    }
}
