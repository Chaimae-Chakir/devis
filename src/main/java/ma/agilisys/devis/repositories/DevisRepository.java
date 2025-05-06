package ma.agilisys.devis.repositories;

import ma.agilisys.devis.models.Devis;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DevisRepository extends JpaRepository<Devis, Long> {
    List<Devis> findByClientId(Long clientId);

    @Query("SELECT d FROM Devis d LEFT JOIN FETCH d.client LEFT JOIN FETCH d.lignes LEFT JOIN FETCH d.meta WHERE d.id = :id")
    Optional<Devis> findByIdWithDetails(@Param("id") Long id);

    @EntityGraph(attributePaths = {"meta"})
    List<Devis> findByStatutAndMetaOffrePdfUrlIsNull(String statut);

    List<Devis> findByMetaOffrePdfUrlIsNotNull();
}
