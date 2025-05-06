package ma.agilisys.devis.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactResponseDTO {
    private Long id;
    private String nom;
    private String email;
    private String telephone;
    private String fonction;
    private ZonedDateTime dateCreation;
}
