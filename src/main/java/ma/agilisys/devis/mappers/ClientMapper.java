package ma.agilisys.devis.mappers;

import ma.agilisys.devis.dtos.ClientDto;
import ma.agilisys.devis.dtos.ClientRequest;
import ma.agilisys.devis.models.Client;
import ma.agilisys.devis.models.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mapping(target = "contactIds", source = "contacts", qualifiedByName = "mapContactsToIds")
    ClientDto toDto(Client client);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "contacts", ignore = true)
    @Mapping(target = "devis", ignore = true)
    Client toEntity(ClientRequest clientRequest);

    @Named("mapContactsToIds")
    default Set<Long> mapContactsToIds(Set<Contact> contacts) {
        return contacts.stream()
                .map(Contact::getId)
                .collect(Collectors.toSet());
    }
}