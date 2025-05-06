package ma.agilisys.devis.repositories;

import ma.agilisys.devis.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
