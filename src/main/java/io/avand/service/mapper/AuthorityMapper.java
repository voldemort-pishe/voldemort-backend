package io.avand.service.mapper;

import io.avand.domain.entity.jpa.AuthorityEntity;
import io.avand.service.dto.AuthorityDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {PermissionAuthorityMapper.class})
public interface AuthorityMapper extends EntityMapper<AuthorityDTO, AuthorityEntity> {
}
