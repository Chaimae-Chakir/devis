package ma.agilisys.devis.repositories;

import ma.agilisys.devis.models.BonCommandeClient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BonCommandeClientRepository extends JpaRepository<BonCommandeClient, Long> {
    Optional<BonCommandeClient> findByDevisId(Long devisId);

    boolean existsByDevisId(Long devisId);
}
