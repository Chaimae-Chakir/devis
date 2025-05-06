package ma.agilisys.devis.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "devis_meta")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"devis"})
public class DevisMeta {
    @Id
    private Long idDevis;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id_devis")
    private Devis devis;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String perimetre;

    @Lob
    @Column(name = "offre_fonctionnelle", columnDefinition = "TEXT")
    private String offreFonctionnelle;

    @Lob
    @Column(name = "offre_technique", columnDefinition = "TEXT")
    private String offreTechnique;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String conditions;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String planning;

    @Column(name = "offre_pdf_url", length = 255)
    private String offrePdfUrl;// URL devis généré
}