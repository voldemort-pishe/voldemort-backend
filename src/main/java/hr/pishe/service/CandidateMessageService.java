package hr.pishe.service;

import hr.pishe.service.dto.CandidateMessageDTO;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CandidateMessageService {

    CandidateMessageDTO save(CandidateMessageDTO candidateMessageDTO) throws NotFoundException;

    CandidateMessageDTO save(String subject,String message,Long parent,Long candidateId) throws NotFoundException;

    CandidateMessageDTO saveInReply(CandidateMessageDTO candidateMessageDTO) throws NotFoundException;

    CandidateMessageDTO findById(Long id) throws NotFoundException;

    Page<CandidateMessageDTO> findByCandidateId(Long candidateId, Pageable pageable) throws NotFoundException;

    Optional<CandidateMessageDTO> findByMessageId(String messageId);

    void delete(Long id) throws NotFoundException;
}
