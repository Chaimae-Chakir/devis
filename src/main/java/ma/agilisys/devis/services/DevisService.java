package ma.agilisys.devis.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ma.agilisys.devis.dtos.DevisDTO;
import ma.agilisys.devis.mappers.DevisLigneMapper;
import ma.agilisys.devis.mappers.DevisMapper;
import ma.agilisys.devis.models.Client;
import ma.agilisys.devis.models.Devis;
import ma.agilisys.devis.models.DevisLigne;
import ma.agilisys.devis.models.DevisMeta;
import ma.agilisys.devis.repositories.ClientRepository;
import ma.agilisys.devis.repositories.DevisRepository;
import ma.agilisys.devis.utils.Constants;
import ma.agilisys.devis.utils.DevisNumberGenerator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DevisService {
    private final DevisRepository devisRepository;
    private final ClientRepository clientRepository;
    private final DevisNumberGenerator devisNumberGenerator;
    private final DevisMapper devisMapper;
    private final DevisLigneMapper devisLigneMapper;

    public List<DevisDTO> getAllDevis(Long clientId) {
        return devisRepository.findByClient_Id(clientId).stream().map(devisMapper::toDto).toList();
    }

    public DevisDTO getDevisByClientId(Long devisId) {
        Devis devis = devisRepository.findById(devisId)
                .orElseThrow(() -> new EntityNotFoundException("Devis non trouvé avec l'id: " + devisId));
        return devisMapper.toDto(devis);
    }

    @Transactional
    public DevisDTO createDevis(DevisDTO devisDto) {
        Client client = clientRepository.findById(devisDto.getClientId())
                .orElseThrow(() -> new EntityNotFoundException("Client non trouvé avec l'ID: " + devisDto.getClientId()));
        Devis devis = devisMapper.toEntity(devisDto);
        devis.setNumero(devisNumberGenerator.generateUniqueNumber());
        devis.setStatut(Constants.DRAFT_STATUS);
        devis.setClient(client);
        List<DevisLigne> lignes = devisDto.getLignes().stream()
                .map(devisLigneMapper::toEntity)
                .toList();
        lignes.forEach(ligne -> ligne.setDevis(devis));
        devis.setLignes(lignes);
        DevisMeta meta = DevisMeta.builder()
                .conditions(devisDto.getConditions())
                .offreFonctionnelle(devisDto.getOffreFonctionnelle())
                .offreTechnique(devisDto.getOffreTechnique())
                .perimetre(devisDto.getPerimetre())
                .offrePdfUrl(devisDto.getOffrePdfUrl())
                .planning(devisDto.getPlanning())
                .devis(devis)
                .build();
        devis.setMeta(meta);
        Devis savedDevis = devisRepository.save(devis);
        return devisMapper.toDto(savedDevis);
    }
}
