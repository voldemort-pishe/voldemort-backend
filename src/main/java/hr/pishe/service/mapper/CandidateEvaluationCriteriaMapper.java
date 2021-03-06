package hr.pishe.service.mapper;

import hr.pishe.domain.entity.jpa.CandidateEvaluationCriteriaEntity;
import hr.pishe.service.dto.CandidateEvaluationCriteriaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = {})
public interface CandidateEvaluationCriteriaMapper extends EntityMapper<CandidateEvaluationCriteriaDTO, CandidateEvaluationCriteriaEntity> {

    @Override
    @Mapping(source = "candidate.id",target = "candidateId")
    CandidateEvaluationCriteriaDTO toDto(CandidateEvaluationCriteriaEntity entity);
}
