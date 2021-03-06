package hr.pishe.service.impl;

import hr.pishe.domain.entity.jpa.CompanyContactEntity;
import hr.pishe.repository.jpa.CompanyContactRepository;
import hr.pishe.service.CompanyContactService;
import hr.pishe.service.dto.CompanyContactDTO;
import hr.pishe.service.mapper.CompanyContactMapper;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CompanyContactServiceImpl implements CompanyContactService {
    private final Logger log = LoggerFactory.getLogger(CompanyContactServiceImpl.class);

    private final CompanyContactRepository companyContactRepository;
    private final CompanyContactMapper companyContactMapper;

    public CompanyContactServiceImpl(CompanyContactRepository companyContactRepository,
                                     CompanyContactMapper companyContactMapper) {
        this.companyContactRepository = companyContactRepository;
        this.companyContactMapper = companyContactMapper;
    }

    @Override
    public CompanyContactDTO save(CompanyContactDTO companyContactDTO) {
        log.debug("Request to save company contact : {}", companyContactDTO);
        CompanyContactEntity companyContactEntity = companyContactMapper.toEntity(companyContactDTO);
        companyContactEntity = companyContactRepository.save(companyContactEntity);
        return companyContactMapper.toDto(companyContactEntity);
    }

    @Override
    public CompanyContactDTO findById(Long id) {
        log.debug("Request to find company contact by id : {}", id);
        CompanyContactEntity companyContactEntity = companyContactRepository.findOne(id);
        return companyContactMapper.toDto(companyContactEntity);
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        log.debug("Request to delete company contact : {}", id);
        CompanyContactEntity companyContactEntity = companyContactRepository.findOne(id);
        if (companyContactEntity != null) {
            companyContactRepository.delete(companyContactEntity);
        } else {
            throw new NotFoundException("Company Contact Not Found");
        }
    }
}
