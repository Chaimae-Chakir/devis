package ma.agilisys.devis.services;

import ma.agilisys.devis.dtos.ClientDto;
import ma.agilisys.devis.dtos.ClientRequest;

import java.util.List;

public interface ClientService {
    List<ClientDto> getAllClients();

    ClientDto getClientById(Long id);

    ClientDto createClient(ClientRequest clientRequest);

    ClientDto updateClient(Long id, ClientRequest clientRequest);

    void deleteClient(Long id);

    List<ClientDto> searchClients(String searchTerm);

    boolean existsByIce(String ice);
}
