package hr.pishe.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class UserAuthorityDTO implements Serializable {

    @NotNull
    private Long id;

    @NotNull
    private Long authorityId;

    @NotNull
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(Long authorityId) {
        this.authorityId = authorityId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserAuthorityDTO{" +
            "id=" + id +
            ", authorityId=" + authorityId +
            ", userId=" + userId +
            '}';
    }
}
