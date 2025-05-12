package ma.agilisys.devis.services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import ma.agilisys.devis.models.DevisPdfFile;
import ma.agilisys.devis.repositories.DevisPdfFileRepository;
import ma.agilisys.devis.services.DevisPdfService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DevisPdfServiceImpl implements DevisPdfService {

    private final DevisPdfFileRepository devisPdfFileRepository;

    @Override
    public DevisPdfFile getDevisPdfFile(Long devisId) {
        return devisPdfFileRepository.findByDevis_Id(devisId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "PDF non trouvé pour le devis ID: " + devisId));
    }
}