package io.avand.web.rest.component.impl;

import io.avand.service.CompanyMemberService;
import io.avand.service.CompanyService;
import io.avand.service.UserService;
import io.avand.service.dto.CompanyMemberDTO;
import io.avand.service.dto.UserDTO;
import io.avand.service.mapper.CompanyMapper;
import io.avand.service.mapper.UserMapper;
import io.avand.web.rest.component.CompanyMemberComponent;
import io.avand.web.rest.util.PageMaker;
import io.avand.web.rest.vm.response.ResponseVM;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CompanyMemberComponentImpl implements CompanyMemberComponent {

    private final Logger log = LoggerFactory.getLogger(CompanyMemberComponentImpl.class);
    private final CompanyMemberService companyMemberService;
    private final UserService userService;
    private final CompanyService companyService;
    private final UserMapper userMapper;
    private final CompanyMapper companyMapper;

    public CompanyMemberComponentImpl(CompanyMemberService companyMemberService,
                                      UserService userService,
                                      CompanyService companyService,
                                      UserMapper userMapper,
                                      CompanyMapper companyMapper) {
        this.companyMemberService = companyMemberService;
        this.userService = userService;
        this.companyService = companyService;
        this.userMapper = userMapper;
        this.companyMapper = companyMapper;
    }

    @Override
    public List<ResponseVM<CompanyMemberDTO>> save(List<String> emails, Long companyId) throws NotFoundException {
        log.debug("Request to save companyMemberDTO via component");
        List<CompanyMemberDTO> companyMemberDTOS = companyMemberService.save(emails, companyId);
        List<ResponseVM<CompanyMemberDTO>> responseVMS = new ArrayList<>();
        for (CompanyMemberDTO companyMemberDTO : companyMemberDTOS) {
            ResponseVM<CompanyMemberDTO> responseVM = new ResponseVM<>();
            responseVM.setData(companyMemberDTO);
            responseVM.setInclude(this.createIncluded(companyMemberDTO));
            responseVMS.add(responseVM);
        }
        return responseVMS;
    }

    @Override
    public ResponseVM<CompanyMemberDTO> findById(Long id) throws NotFoundException {
        CompanyMemberDTO companyMemberDTO = companyMemberService.findById(id);
        ResponseVM<CompanyMemberDTO> responseVM = new ResponseVM<>();
        responseVM.setData(companyMemberDTO);
        responseVM.setInclude(this.createIncluded(companyMemberDTO));
        return responseVM;
    }

    @Override
    public Page<ResponseVM<CompanyMemberDTO>> findAll(Long companyId, Pageable pageable) throws NotFoundException {
        Page<CompanyMemberDTO> companyMemberDTOS = companyMemberService.findAll(companyId, pageable);
        List<ResponseVM<CompanyMemberDTO>> responseVMS = new ArrayList<>();
        for (CompanyMemberDTO companyMemberDTO : companyMemberDTOS) {
            ResponseVM<CompanyMemberDTO> responseVM = new ResponseVM<>();
            responseVM.setData(companyMemberDTO);
            responseVM.setInclude(this.createIncluded(companyMemberDTO));
            responseVMS.add(responseVM);
        }
        return new PageMaker<>(responseVMS, companyMemberDTOS);
    }

    private Map<String, Object> createIncluded(CompanyMemberDTO companyMemberDTO) throws NotFoundException {
        Map<String, Object> included = new HashMap<>();

        Optional<UserDTO> userDTOOptional = userService.findById(companyMemberDTO.getUserId());
        userDTOOptional.ifPresent(userDTO -> included.put("user", userMapper.dtoToVm(userDTO)));

        included.put("company", companyMapper.dtoToVm(companyService.findById(companyMemberDTO.getCompanyId())));

        return included;
    }
}