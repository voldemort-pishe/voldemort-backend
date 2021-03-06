package hr.pishe.service;

import hr.pishe.service.dto.CandidateSocialDTO;
import javassist.NotFoundException;

import java.util.List;

public interface CandidateSocialService {
    CandidateSocialDTO save(CandidateSocialDTO candidateSocialDTO) throws NotFoundException;

    CandidateSocialDTO findById(Long id);

    List<CandidateSocialDTO> findAllByCandidateId(Long candidateId);

    void delete(Long id) throws NotFoundException;
}
