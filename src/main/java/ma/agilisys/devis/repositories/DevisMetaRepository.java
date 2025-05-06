package ma.agilisys.devis.repositories;

import ma.agilisys.devis.models.DevisMeta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DevisMetaRepository extends JpaRepository<DevisMeta, Long> {
    Optional<DevisMeta> findByDevisId(Long devisId);
}
