package io.avand.web.rest.vm;

import io.avand.config.Constants;

import javax.validation.constraints.*;
import java.io.Serializable;

public class UserRegisterVM implements Serializable {

    @NotNull
    @Pattern(regexp = Constants.NAME_REGEX)
    private String firstName;
    @NotNull
    @Pattern(regexp = Constants.LAST_NAME_REGEX)
    private String lastName;
    @NotNull
    @Size(min = 4)
    private String password;
    @NotNull
    @Pattern(regexp = Constants.EMAIL_REGEX)
    private String email;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}