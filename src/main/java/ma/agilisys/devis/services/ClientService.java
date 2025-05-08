package ma.agilisys.devis.services;

import ma.agilisys.devis.dtos.ClientRequestDto;
import ma.agilisys.devis.dtos.ClientResponseDto;

import java.util.List;

public interface ClientService {

    ClientResponseDto getClientById(Long id);

    ClientResponseDto createClient(ClientRequestDto clientRequestDto);

    boolean existsByIce(String ice);

    List<ClientResponseDto> getAllClient();
}
