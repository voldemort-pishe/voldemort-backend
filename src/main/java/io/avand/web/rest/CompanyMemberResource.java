package io.avand.web.rest;

import io.avand.service.CompanyMemberService;
import io.avand.service.dto.CompanyMemberDTO;
import io.avand.web.rest.errors.BadRequestAlertException;
import io.avand.web.rest.errors.ServerErrorException;
import io.avand.web.rest.util.HeaderUtil;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/company-member")
public class CompanyMemberResource {

    private final static String ENTITY_NAME = "CompanyMemberEntity";
    private final Logger log = LoggerFactory.getLogger(CompanyMemberResource.class);
    private final CompanyMemberService companyMemberService;

    public CompanyMemberResource(CompanyMemberService companyMemberService) {
        this.companyMemberService = companyMemberService;
    }

    @PostMapping
    public ResponseEntity save(@RequestBody @Valid CompanyMemberDTO companyMemberDTO) throws URISyntaxException {
        log.debug("REST Request to save company member : {}", companyMemberDTO);

        if (companyMemberDTO.getId() != null) {
            throw new BadRequestAlertException("A new comment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        try {
            CompanyMemberDTO result = companyMemberService.save(companyMemberDTO);
            return ResponseEntity.created(new URI("/api/company-member/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
        } catch (NotFoundException | SecurityException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity getAll(@PathVariable("companyId") Long companyId) {
        log.debug("Request to find all company member by company id : {}", companyId);
        try {
            List<CompanyMemberDTO> companyMemberDTOS = companyMemberService.findAll(companyId);
            return new ResponseEntity<>(companyMemberDTOS, HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable("id") Long id) {
        log.debug("REST Request to find company member by id : {}", id);
        try {
            CompanyMemberDTO companyMemberDTO = companyMemberService.findById(id);
            return new ResponseEntity<>(companyMemberDTO, HttpStatus.OK);
        } catch (NotFoundException | SecurityException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        log.debug("REST Request to delete company member by id : {}", id);
        try {
            companyMemberService.delete(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (NotFoundException | SecurityException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }
}