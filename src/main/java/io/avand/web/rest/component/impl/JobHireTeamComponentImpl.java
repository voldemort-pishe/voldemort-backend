package io.avand.web.rest.component.impl;

import io.avand.service.JobHireTeamService;
import io.avand.service.JobService;
import io.avand.service.UserService;
import io.avand.service.dto.JobHireTeamDTO;
import io.avand.service.dto.UserDTO;
import io.avand.service.mapper.JobMapper;
import io.avand.service.mapper.UserMapper;
import io.avand.web.rest.component.JobHireTeamComponent;
import io.avand.web.rest.vm.response.ResponseVM;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class JobHireTeamComponentImpl implements JobHireTeamComponent {

    private final Logger log = LoggerFactory.getLogger(JobHireTeamComponentImpl.class);
    private final JobHireTeamService jobHireTeamService;
    private final UserService userService;
    private final UserMapper userMapper;
    private final JobService jobService;
    private final JobMapper jobMapper;

    public JobHireTeamComponentImpl(JobHireTeamService jobHireTeamService,
                                    UserService userService,
                                    UserMapper userMapper,
                                    JobService jobService,
                                    JobMapper jobMapper) {
        this.jobHireTeamService = jobHireTeamService;
        this.userService = userService;
        this.userMapper = userMapper;
        this.jobService = jobService;
        this.jobMapper = jobMapper;
    }

    @Override
    public ResponseVM<JobHireTeamDTO> save(JobHireTeamDTO jobHireTeamDTO) throws NotFoundException {
        log.debug("Request to save jobHireTeam via component : {}", jobHireTeamDTO);
        ResponseVM<JobHireTeamDTO> responseVM = new ResponseVM<>();
        responseVM.setData(jobHireTeamService.save(jobHireTeamDTO));
        responseVM.setInclude(this.createIncluded(jobHireTeamDTO));
        return responseVM;
    }

    @Override
    public List<ResponseVM<JobHireTeamDTO>> saveAll(List<JobHireTeamDTO> jobHireTeamDTOS) throws NotFoundException {
        log.debug("Request to saveAll jobHireTeamDTOs");
        List<ResponseVM<JobHireTeamDTO>> responseVMS = new ArrayList<>();
        for (JobHireTeamDTO jobHireTeamDTO : jobHireTeamDTOS) {
            responseVMS.add(this.save(jobHireTeamDTO));
        }
        return responseVMS;
    }

    @Override
    public List<ResponseVM<JobHireTeamDTO>> findByJobId(Long jobId) throws NotFoundException {
        List<ResponseVM<JobHireTeamDTO>> responseVMS = new ArrayList<>();
        List<JobHireTeamDTO> jobHireTeamDTOS = jobHireTeamService.findByJobId(jobId);
        for (JobHireTeamDTO jobHireTeamDTO : jobHireTeamDTOS) {
            ResponseVM<JobHireTeamDTO> responseVM = new ResponseVM<>();
            responseVM.setData(jobHireTeamDTO);
            responseVM.setInclude(this.createIncluded(jobHireTeamDTO));
            responseVMS.add(responseVM);
        }
        return responseVMS;
    }

    private Map<String, Object> createIncluded(JobHireTeamDTO jobHireTeamDTO) throws NotFoundException {
        Map<String, Object> included = new HashMap<>();

        Optional<UserDTO> hiredManager = userService.findById(jobHireTeamDTO.getUserId());
        hiredManager.ifPresent(userDTO -> included.put("owner", userMapper.dtoToVm(userDTO)));

        included.put("job", jobMapper.dtoToVm(jobService.findById(jobHireTeamDTO.getJobId())));

        return included;
    }
}
