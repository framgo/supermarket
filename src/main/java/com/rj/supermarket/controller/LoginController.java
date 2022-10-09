package com.rj.supermarket.controller;

import com.rj.supermarket.config.JwtTokenUtil;
import com.rj.supermarket.domain.dto.JwtRequest;
import com.rj.supermarket.domain.dto.JwtResponse;
import com.rj.supermarket.domain.model.Login;
import com.rj.supermarket.service.JwtUserDetailsService;
import com.rj.supermarket.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(path = "auth")
@Tag(name = "Controller authentication", description = "Controller that manages the login login")
public class LoginController {
    private final LoginService loginService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final JwtUserDetailsService userDetailsService;

    public LoginController(LoginService loginService, JwtTokenUtil jwtTokenUtil, AuthenticationManager authenticationManager, JwtUserDetailsService userDetailsService) {
        this.loginService = loginService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Register a login", responses = {@ApiResponse(description = "Success when registering", responseCode = "201", content = @Content)})
    ResponseEntity<Login> save(@RequestBody Login login) {
        return new ResponseEntity<>(loginService.save(login), CREATED);
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Log a login", responses = {@ApiResponse(description = "Successfully logging in", responseCode = "200", content = @Content)})
    ResponseEntity<Login> login(@RequestBody Login login) {
        return new ResponseEntity<>(loginService.login(login), OK);
    }

    @GetMapping(value = "/logoff/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Disconnect a login by ID", responses = {@ApiResponse(description = "Success logging off", responseCode = "204")})
    ResponseEntity<?> findById(@PathVariable UUID id) {
        loginService.logoff(id);
        return new ResponseEntity<>(NO_CONTENT);
    }

    @PatchMapping(value = "/active/{id}")
    @Operation(summary = "Update an employee", responses = {@ApiResponse(description = "Update success", responseCode = "204")})
    ResponseEntity<?> active(@PathVariable UUID id) {
        loginService.active(id);
        return new ResponseEntity<>(NO_CONTENT);
    }

    @PatchMapping(value = "/disable/{id}")
    @Operation(summary = "Update an employee", responses = {@ApiResponse(description = "Update success", responseCode = "204")})
    ResponseEntity<?> disable(@PathVariable UUID id) {
        loginService.disable(id);
        return new ResponseEntity<>(NO_CONTENT);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete a login by ID", responses = {@ApiResponse(description = "Successful erase", responseCode = "204")})
    ResponseEntity<?> deleteById(@PathVariable UUID id) {
        loginService.deleteById(id);
        return new ResponseEntity<>(NO_CONTENT);
    }

    @PostMapping(value = "/authenticate", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Log a login", responses = {@ApiResponse(description = "Successfully logging in", responseCode = "200", content = @Content)})
    ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest autentication) throws Exception {
        authenticate(autentication.getUsername(), autentication.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(autentication.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(JwtResponse.builder().jwtToken(token).build());
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException de) {
            throw new Exception("USER_DISABLE", de);
        } catch (BadCredentialsException bce) {
            throw new Exception("INVALID_CREDENTIALS", bce);
        }
    }
}
