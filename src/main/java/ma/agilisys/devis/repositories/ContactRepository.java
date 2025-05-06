package ma.agilisys.devis.repositories;

import ma.agilisys.devis.models.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}
