package ma.agilisys.devis.services.impl;

import ma.agilisys.devis.dtos.ContactDto;
import ma.agilisys.devis.dtos.ContactRequest;
import ma.agilisys.devis.exceptions.ResourceNotFoundException;
import ma.agilisys.devis.mappers.ContactMapper;
import ma.agilisys.devis.models.Client;
import ma.agilisys.devis.models.Contact;
import ma.agilisys.devis.repositories.ClientRepository;
import ma.agilisys.devis.repositories.ContactRepository;
import ma.agilisys.devis.services.ContactService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final ClientRepository clientRepository;
    private final ContactMapper contactMapper;

    public ContactServiceImpl(ContactRepository contactRepository,
                              ClientRepository clientRepository,
                              ContactMapper contactMapper) {
        this.contactRepository = contactRepository;
        this.clientRepository = clientRepository;
        this.contactMapper = contactMapper;
    }

    @Override
    public List<ContactDto> getAllContacts() {
        return contactRepository.findAll().stream()
                .map(contactMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ContactDto getContactById(Long id) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found with id: " + id));
        return contactMapper.toDto(contact);
    }

    @Override
    public ContactDto createContact(ContactRequest contactRequest) {
        Client client = clientRepository.findById(contactRequest.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + contactRequest.getClientId()));

        Contact contact = contactMapper.toEntity(contactRequest);
        contact.setClient(client);

        Contact savedContact = contactRepository.save(contact);
        return contactMapper.toDto(savedContact);
    }

    @Override
    public ContactDto updateContact(Long id, ContactRequest contactRequest) {
        Contact existingContact = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found with id: " + id));

        Client client = clientRepository.findById(contactRequest.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + contactRequest.getClientId()));

        existingContact.setEmail(contactRequest.getEmail());
        existingContact.setTelephone(contactRequest.getTelephone());
        existingContact.setFax(contactRequest.getFax());
        existingContact.setClient(client);

        Contact updatedContact = contactRepository.save(existingContact);
        return contactMapper.toDto(updatedContact);
    }

    @Override
    public void deleteContact(Long id) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found with id: " + id));
        contactRepository.delete(contact);
    }

    @Override
    public List<ContactDto> getContactsByClient(Long clientId) {
        return contactRepository.findByClientId(clientId).stream()
                .map(contactMapper::toDto)
                .collect(Collectors.toList());
    }
}
