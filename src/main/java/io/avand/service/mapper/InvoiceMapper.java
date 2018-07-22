package io.avand.service.mapper;

import io.avand.domain.entity.jpa.InvoiceEntity;
import io.avand.service.dto.InvoiceDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = {PaymentTransactionMapper.class})
public interface InvoiceMapper extends EntityMapper<InvoiceDTO, InvoiceEntity> {

    @Override
    @Mapping(source = "user.id",target = "userId")
    InvoiceDTO toDto(InvoiceEntity entity);
}
