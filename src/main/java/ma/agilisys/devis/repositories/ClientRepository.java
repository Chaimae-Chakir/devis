package ma.agilisys.devis.repositories;

import ma.agilisys.devis.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByIce(String ice);

    boolean existsByIce(String ice);

    List<Client> findByNomContainingIgnoreCaseOrIceContainingIgnoreCase(String searchTerm, String searchTerm1);
}
