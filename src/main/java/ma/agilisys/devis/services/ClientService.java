package ma.agilisys.devis.services;

import ma.agilisys.devis.dtos.ClientPageDto;
import ma.agilisys.devis.dtos.ClientRequestDto;
import ma.agilisys.devis.dtos.ClientResponseDto;

public interface ClientService {

    ClientResponseDto getClientById(Long id);

    ClientResponseDto createClient(ClientRequestDto clientRequestDto);

    boolean existsByIce(String ice);

    ClientPageDto getAllClient(int page, int size);

    ClientResponseDto updateClient(Long id, ClientRequestDto clientRequestDto);

    void deleteClient(Long id);
}
