package ma.agilisys.devis.mappers;

import ma.agilisys.devis.dtos.ContactDto;
import ma.agilisys.devis.dtos.ContactRequest;
import ma.agilisys.devis.models.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ContactMapper {

    @Mapping(target = "clientId", source = "client.id")
    ContactDto toDto(Contact contact);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "client", ignore = true)
    Contact toEntity(ContactRequest contactRequest);
}