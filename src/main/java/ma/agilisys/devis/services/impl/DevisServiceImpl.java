package ma.agilisys.devis.services.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ma.agilisys.devis.dtos.DevisRequestDTO;
import ma.agilisys.devis.dtos.DevisResponseDTO;
import ma.agilisys.devis.mappers.DevisLigneMapper;
import ma.agilisys.devis.mappers.DevisMapper;
import ma.agilisys.devis.models.Client;
import ma.agilisys.devis.models.Devis;
import ma.agilisys.devis.models.DevisLigne;
import ma.agilisys.devis.models.DevisMeta;
import ma.agilisys.devis.repositories.ClientRepository;
import ma.agilisys.devis.repositories.DevisRepository;
import ma.agilisys.devis.services.DevisService;
import ma.agilisys.devis.utils.Constants;
import ma.agilisys.devis.utils.DevisNumberGenerator;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DevisServiceImpl implements DevisService {
    private final DevisRepository devisRepository;
    private final ClientRepository clientRepository;
    private final DevisNumberGenerator devisNumberGenerator;
    private final DevisMapper devisMapper;
    private final DevisLigneMapper devisLigneMapper;

    @Override
    public List<DevisResponseDTO> getAllDevis(Long clientId) {
        if (clientId == null) {
            throw new IllegalArgumentException("L'identifiant du client ne doit pas être nul.");
        }
        List<Devis> devisList = devisRepository.findByClientId(clientId);
        if (devisList.isEmpty()) {
            throw new EntityNotFoundException("Aucun devis trouvé pour le client avec l'identifiant : " + clientId);
        }
        return devisList.stream()
                .map(devisMapper::toDto)
                .toList();
    }

    @Override
    public DevisResponseDTO getDevisById(Long devisId) {
        Devis devis = devisRepository.findById(devisId)
                .orElseThrow(() -> new EntityNotFoundException("Devis non trouvé avec l'id: " + devisId));
        return devisMapper.toDto(devis);
    }

    @Transactional
    @Override
    public DevisResponseDTO createDevis(DevisRequestDTO devisRequestDTO) {
        Client client = clientRepository.findById(devisRequestDTO.getClientId())
                .orElseThrow(() -> new EntityNotFoundException("Client non trouvé avec l'ID: " + devisRequestDTO.getClientId()));
        Devis devis = devisMapper.toEntity(devisRequestDTO);
        devis.setNumero(devisNumberGenerator.generateUniqueNumber());
        devis.setStatut(Constants.DRAFT_STATUS);
        devis.setClient(client);
        devis.setDateCreation(ZonedDateTime.now());
        List<DevisLigne> lignes = devisRequestDTO.getLignes().stream()
                .map(devisLigneMapper::toEntity)
                .toList();
        lignes.forEach(ligne -> ligne.setDevis(devis));
        devis.setLignes(lignes);
        DevisMeta meta = DevisMeta.builder()
                .conditions(devisRequestDTO.getConditions())
                .offreFonctionnelle(devisRequestDTO.getOffreFonctionnelle())
                .offreTechnique(devisRequestDTO.getOffreTechnique())
                .perimetre(devisRequestDTO.getPerimetre())
                .offrePdfUrl(devisRequestDTO.getOffrePdfUrl())
                .planning(devisRequestDTO.getPlanning())
                .devis(devis)
                .build();
        devis.setMeta(meta);
        Devis savedDevis = devisRepository.save(devis);
        return devisMapper.toDto(savedDevis);
    }

    @Transactional
    @Override
    public DevisResponseDTO updateDevis(Long id, DevisRequestDTO devisRequestDTO) {
        Devis existingDevis = devisRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Devis non trouvé avec l'ID: " + id));
        if (existingDevis.getMeta() != null) {
            existingDevis.getMeta().setPerimetre(devisRequestDTO.getPerimetre());
            existingDevis.getMeta().setOffreFonctionnelle(devisRequestDTO.getOffreFonctionnelle());
            existingDevis.getMeta().setOffreTechnique(devisRequestDTO.getOffreTechnique());
            existingDevis.getMeta().setConditions(devisRequestDTO.getConditions());
            existingDevis.getMeta().setPlanning(devisRequestDTO.getPlanning());
        }
        existingDevis.getLignes().clear();
        List<DevisLigne> nouvelleLignes = devisRequestDTO.getLignes().stream()
                .map(devisLigneMapper::toEntity)
                .toList();
        nouvelleLignes.forEach(ligne -> ligne.setDevis(existingDevis));
        existingDevis.getLignes().addAll(nouvelleLignes);
        Devis updatedDevis = devisRepository.save(existingDevis);
        return devisMapper.toDto(updatedDevis);
    }

    @Transactional
    @Override
    public DevisResponseDTO validateDevis(Long id, String validatedBy) {
        Devis devis = devisRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Devis non trouvé avec l'ID: " + id));
        if (!Constants.DRAFT_STATUS.equals(devis.getStatut())) {
            throw new IllegalStateException("Seul un devis en statut Brouillon peut être validé");
        }
        devis.setStatut(Constants.APPROVED_STATUS);
        devis.setDateValidation(ZonedDateTime.now());
        devis.setValidatedBy(validatedBy);
        Devis validatedDevis = devisRepository.save(devis);
        return devisMapper.toDto(validatedDevis);
    }

    @Transactional
    @Override
    public DevisResponseDTO duplicateDevis(Long id) {
        Devis devis = devisRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Devis non trouvé avec l'ID: " + id));
        Devis duplicatedDevis = new Devis();
        duplicatedDevis.setNumero(devisNumberGenerator.generateUniqueNumber());
        duplicatedDevis.setClient(devis.getClient());
        duplicatedDevis.setStatut(Constants.DRAFT_STATUS);
        duplicatedDevis.setDateCreation(ZonedDateTime.now());
        duplicatedDevis.setCreatedBy(devis.getCreatedBy());
        List<DevisLigne> newLignes = new ArrayList<>();
        for (DevisLigne ligne : devis.getLignes()) {
            DevisLigne newLigne = new DevisLigne();
            newLigne.setDescriptionLibre(ligne.getDescriptionLibre());
            newLigne.setPrixUnitaireHt(ligne.getPrixUnitaireHt());
            newLigne.setQuantite(ligne.getQuantite());
            newLigne.setRistournePct(ligne.getRistournePct());
            newLigne.setTvaPct(ligne.getTvaPct());
            newLigne.setDevis(duplicatedDevis);
            newLignes.add(newLigne);
        }
        duplicatedDevis.setLignes(newLignes);
        if (devis.getMeta() != null) {
            DevisMeta newMeta = DevisMeta.builder()
                    .perimetre(devis.getMeta().getPerimetre())
                    .offreFonctionnelle(devis.getMeta().getOffreFonctionnelle())
                    .offreTechnique(devis.getMeta().getOffreTechnique())
                    .conditions(devis.getMeta().getConditions())
                    .planning(devis.getMeta().getPlanning())
                    .devis(duplicatedDevis)
                    .build();
            duplicatedDevis.setMeta(newMeta);
        }
        Devis savedDuplicate = devisRepository.save(duplicatedDevis);
        return devisMapper.toDto(savedDuplicate);
    }

    @Transactional
    @Override
    public void deleteDevis(Long id) {
        Devis devis = devisRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Devis non trouvé avec l'ID: " + id));
        if (!Constants.DRAFT_STATUS.equals(devis.getStatut())) {
            throw new IllegalStateException("Seul un devis en statut DRAFT peut être supprimé");
        }
        devisRepository.delete(devis);
    }
}