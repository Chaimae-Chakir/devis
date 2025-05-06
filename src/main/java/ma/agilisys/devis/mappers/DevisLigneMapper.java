package ma.agilisys.devis.mappers;

import ma.agilisys.devis.dtos.DevisLigneDTO;
import ma.agilisys.devis.models.DevisLigne;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DevisLigneMapper {
    @Mapping(source = "devis.id", target = "devisId")
    DevisLigneDTO toDto(DevisLigne devisLigne);

    @Mapping(source = "devisId", target = "devis.id")
    DevisLigne toEntity(DevisLigneDTO devisLigneDTO);
}