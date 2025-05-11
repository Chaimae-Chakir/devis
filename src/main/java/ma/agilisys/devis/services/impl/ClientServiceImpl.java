package ma.agilisys.devis.services.impl;

import lombok.RequiredArgsConstructor;
import ma.agilisys.devis.dtos.ClientPageDto;
import ma.agilisys.devis.dtos.ClientRequestDto;
import ma.agilisys.devis.dtos.ClientResponseDto;
import ma.agilisys.devis.exceptions.ResourceNotFoundException;
import ma.agilisys.devis.mappers.ClientMapper;
import ma.agilisys.devis.models.Client;
import ma.agilisys.devis.repositories.ClientRepository;
import ma.agilisys.devis.services.ClientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    @Transactional(readOnly = true)
    public ClientResponseDto getClientById(Long id) {
        Client client = clientRepository.findByIdWithContacts(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));
        return clientMapper.toDto(client);
    }

    @Override
    public ClientResponseDto createClient(ClientRequestDto clientRequestDto) {
        if (existsByIce(clientRequestDto.getIce())) {
            throw new IllegalArgumentException("Un client avec cet ICE existe déjà");
        }

        Client client = clientMapper.toEntity(clientRequestDto);
        Client savedClient = clientRepository.save(client);
        return clientMapper.toDto(savedClient);
    }

    @Override
    public boolean existsByIce(String ice) {
        return clientRepository.existsByIce(ice);
    }


    @Override
    public ClientPageDto getAllClient(int page, int size) {
        Page<Client> clientPage = clientRepository.findAll(PageRequest.of(page, size));
        List<ClientResponseDto> clientList = clientPage.getContent().stream().map(clientMapper::toDto).toList();
        return ClientPageDto.builder().clients(clientList).totalPages(clientPage.getTotalPages()).pageSize(size).totalElements(clientPage.getTotalElements()).currentPage(page).build();
    }

    @Override
    @Transactional
    public ClientResponseDto updateClient(Long id, ClientRequestDto clientRequestDto) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));
        client.setNom(clientRequestDto.getNom());
        client.setIce(clientRequestDto.getIce());
        client.setLogoUrl(clientRequestDto.getLogoUrl());
        client.setAdresse(clientRequestDto.getAdresse());
        client.setVille(clientRequestDto.getVille());
        client.setPays(clientRequestDto.getPays());
        Client updatedClient = clientRepository.save(client);
        return clientMapper.toDto(updatedClient);
    }

    @Override
    @Transactional
    public void deleteClient(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));
        client.getContacts().clear();
        client.getDevis().clear();

        clientRepository.delete(client);
    }
}