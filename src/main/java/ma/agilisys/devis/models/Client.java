package ma.agilisys.devis.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "client")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"devis", "contacts"})
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String nom;

    @Column(nullable = false, length = 20, unique = true)
    private String ice;

    @Column(name = "logo_url", length = 255)
    private String logoUrl;

    @Column(length = 255)
    private String adresse;

    @Column(length = 100)
    private String ville;

    @Column(length = 60)
    private String pays;

    @Column(name = "date_creation")
    private ZonedDateTime dateCreation = ZonedDateTime.now();

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Contact> contacts = new HashSet<>();

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private List<Devis> devis = new ArrayList<>();
}
