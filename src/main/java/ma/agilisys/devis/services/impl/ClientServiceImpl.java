package ma.agilisys.devis.services.impl;

import lombok.RequiredArgsConstructor;
import ma.agilisys.devis.dtos.ClientDto;
import ma.agilisys.devis.dtos.ClientRequest;
import ma.agilisys.devis.exceptions.ResourceNotFoundException;
import ma.agilisys.devis.mappers.ClientMapper;
import ma.agilisys.devis.models.Client;
import ma.agilisys.devis.repositories.ClientRepository;
import ma.agilisys.devis.services.ClientService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;


    @Override
    public List<ClientDto> getAllClients() {
        return clientRepository.findAll().stream()
                .map(client -> clientMapper.toDto(client))
                .collect(Collectors.toList());
    }

    @Override
    public ClientDto getClientById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));
        return clientMapper.toDto(client);
    }

    @Override
    public ClientDto createClient(ClientRequest clientRequest) {
        if (existsByIce(clientRequest.getIce())) {
            throw new IllegalArgumentException("Un client avec cet ICE existe déjà");
        }

        Client client = clientMapper.toEntity(clientRequest);
        Client savedClient = clientRepository.save(client);
        return clientMapper.toDto(savedClient);
    }

    @Override
    public ClientDto updateClient(Long id, ClientRequest clientRequest) {
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));

        if (!existingClient.getIce().equals(clientRequest.getIce()) &&
                existsByIce(clientRequest.getIce())) {
            throw new IllegalArgumentException("Un client avec cet ICE existe déjà");
        }

        existingClient.setNom(clientRequest.getNom());
        existingClient.setIce(clientRequest.getIce());
        existingClient.setLogoUrl(clientRequest.getLogoUrl());
        existingClient.setAdresse(clientRequest.getAdresse());
        existingClient.setVille(clientRequest.getVille());
        existingClient.setPays(clientRequest.getPays());

        Client updatedClient = clientRepository.save(existingClient);
        return clientMapper.toDto(updatedClient);
    }

    @Override
    public void deleteClient(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));
        clientRepository.delete(client);
    }

    @Override
    public List<ClientDto> searchClients(String searchTerm) {
        return clientRepository.findByNomContainingIgnoreCaseOrIceContainingIgnoreCase(searchTerm, searchTerm)
                .stream()
                .map(clientMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByIce(String ice) {
        return clientRepository.existsByIce(ice);
    }
}