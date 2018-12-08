package io.avand.web.rest.vm;

import io.avand.service.dto.CompanyMemberDTO;

import java.io.Serializable;
import java.util.List;

public class CompanyMemberVM implements Serializable {
    private List<CompanyMemberDTO> members;

    public List<CompanyMemberDTO> getMembers() {
        return members;
    }

    public void setMembers(List<CompanyMemberDTO> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "CompanyMemberVM{" +
            "members=" + members +
            '}';
    }
}
