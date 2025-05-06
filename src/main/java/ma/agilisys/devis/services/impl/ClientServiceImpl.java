package ma.agilisys.devis.services.impl;

import lombok.RequiredArgsConstructor;
import ma.agilisys.devis.dtos.ClientRequestDto;
import ma.agilisys.devis.dtos.ClientResponseDto;
import ma.agilisys.devis.exceptions.ResourceNotFoundException;
import ma.agilisys.devis.mappers.ClientMapper;
import ma.agilisys.devis.models.Client;
import ma.agilisys.devis.repositories.ClientRepository;
import ma.agilisys.devis.services.ClientService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}