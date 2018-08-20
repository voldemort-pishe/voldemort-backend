package io.avand.web.rest.component;

import io.avand.service.dto.CandidateDTO;
import io.avand.web.rest.vm.response.ResponseVM;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CandidateComponent {

    ResponseVM<CandidateDTO> save(CandidateDTO candidateDTO) throws NotFoundException;

    ResponseVM<CandidateDTO> findById(Long id) throws NotFoundException;

    Page<ResponseVM<CandidateDTO>> findAll(Pageable pageable) throws NotFoundException;

    Page<ResponseVM<CandidateDTO>> findByJobId(Long jobId, Pageable pageable) throws NotFoundException;

    Page<ResponseVM<CandidateDTO>> findByCompanyId(Long companyId, Pageable pageable) throws NotFoundException;

}