package io.avand.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.avand.security.jwt.JWTConfigurer;
import io.avand.service.UserService;
import io.avand.service.dto.TokenDTO;
import io.avand.service.dto.UserDTO;
import io.avand.web.rest.errors.ServerErrorConstants;
import io.avand.web.rest.errors.ServerErrorException;
import io.avand.web.rest.errors.ServerMessage;
import io.avand.web.rest.vm.UserActivationVM;
import io.avand.web.rest.vm.UserLoginVM;
import io.avand.web.rest.vm.UserVM;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/account")
public class AccountResource {

    private final Logger log = LoggerFactory.getLogger(AccountResource.class);
    private final UserService userService;

    public AccountResource(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Validated UserVM userVM) {
        log.debug("REST Request to register user : {}", userVM);
        Optional<UserDTO> userDTOOptional = userService.findByLogin(userVM.getEmail());
        if (userDTOOptional.isPresent()) {
            throw new ServerErrorException(ServerErrorConstants.EMAIL_ALREADY_EXIST);
        } else {
            userService
                .save(
                    userVM.getEmail(),
                    userVM.getFirstName(),
                    userVM.getLastName(),
                    userVM.getEmail(),
                    userVM.getPassword()
                );

            ServerMessage serverMessage = new ServerMessage();
            serverMessage.setMessage("حساب کاربری شما با موفقیت ایجاد شد، جهت تایید حساب کاربری خود یک پیغام به آدرس پست الکترونیکی شما ارسال شد.");
            return new ResponseEntity<>(serverMessage, HttpStatus.OK);
        }
    }

    @GetMapping("/activate/{activation-key}")
    public ResponseEntity activate(@PathVariable("activation-key") String activationKey,
                                   HttpServletResponse response,
                                   HttpServletRequest request) {
        log.debug("REST Request to activate user by activationKey : {}", activationKey);
        try {
            TokenDTO tokenDTO = userService.activate(activationKey);
            response.addHeader(JWTConfigurer.AUTHORIZATION_HEADER, "Bearer " + tokenDTO.getToken());
            return new ResponseEntity<>(tokenDTO, HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    @PostMapping("/activate")
    public ResponseEntity resendActivationEmail(@RequestBody @Valid UserActivationVM activationVM) {
        log.debug("REST Request to resend activation email : {}", activationVM);
        try {
            userService.requestToResendActivationEmail(activationVM.getEmail());
            ServerMessage serverMessage = new ServerMessage();
            serverMessage.setMessage("پیام فعال سازی حساب به ایمیل شما ارسال گردید.");
            return new ResponseEntity<>(serverMessage, HttpStatus.OK);
        } catch (NotFoundException | IllegalStateException e) {
            throw  new ServerErrorException(e.getMessage());
        }
    }


    @PostMapping("/authenticate")
    @Timed
    public ResponseEntity authorize(@Valid @RequestBody UserLoginVM userLoginVM,
                                    HttpServletResponse response,
                                    HttpServletRequest request) {
        log.debug("REST Request to authorize user : {}", userLoginVM);
        try {
            TokenDTO tokenDTO = userService.authorize(userLoginVM.getUsername(), userLoginVM.getPassword(), userLoginVM.isRememberMe());
            response.addHeader(JWTConfigurer.AUTHORIZATION_HEADER, "Bearer " + tokenDTO.getToken());
            return new ResponseEntity<>(tokenDTO, HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }
}
