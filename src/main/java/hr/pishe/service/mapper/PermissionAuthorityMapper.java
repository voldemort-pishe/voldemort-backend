package hr.pishe.service.mapper;

import hr.pishe.domain.entity.jpa.PermissionEntity;
import hr.pishe.service.dto.PermissionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = AuthorityPermissionMapper.class)
public interface PermissionAuthorityMapper extends EntityMapper<PermissionDTO, PermissionEntity> {
    @Override
    @Mapping(target = "authorities",ignore = true)
    PermissionDTO toDto(PermissionEntity entity);

    @Override
    @Mapping(target = "authorities",ignore = true)
    PermissionEntity toEntity(PermissionDTO dto);
}
