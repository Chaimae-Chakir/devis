package ma.agilisys.devis.services;

import ma.agilisys.devis.dtos.ClientRequestDto;
import ma.agilisys.devis.dtos.ClientResponseDto;

public interface ClientService {

    ClientResponseDto getClientById(Long id);

    ClientResponseDto createClient(ClientRequestDto clientRequestDto);

    boolean existsByIce(String ice);
}
