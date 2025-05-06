package ma.agilisys.devis.repositories;

import ma.agilisys.devis.models.Devis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DevisRepository extends JpaRepository<Devis, Long> {
    List<Devis> findByClientId(Long clientId);

    List<Devis> findByStatutAndMeta_OffrePdfUrlIsNull(String approvedStatus);
}
