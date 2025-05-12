package ma.agilisys.devis.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "devis_pdf_file")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DevisPdfFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String fileType;

    @Lob
    @Column(nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private byte[] data;

    @OneToOne
    @JoinColumn(name = "id_devis", referencedColumnName = "id")
    private Devis devis;
}