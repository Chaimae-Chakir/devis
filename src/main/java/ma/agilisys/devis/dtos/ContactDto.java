package ma.agilisys.devis.dtos;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class ContactDto {
    private Long id;
    private String email;
    private String telephone;
    private String fax;
    private Long clientId;
    private ZonedDateTime dateCreation;
}