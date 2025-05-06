package ma.agilisys.devis.services;

import ma.agilisys.devis.dtos.ContactDto;
import ma.agilisys.devis.dtos.ContactRequest;

import java.util.List;

public interface ContactService {
    List<ContactDto> getAllContacts();

    ContactDto getContactById(Long id);

    ContactDto createContact(ContactRequest contactRequest);

    ContactDto updateContact(Long id, ContactRequest contactRequest);

    void deleteContact(Long id);

    List<ContactDto> getContactsByClient(Long clientId);
}