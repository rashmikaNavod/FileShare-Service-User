package lk.ijse.eca.userservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import lk.ijse.eca.userservice.dto.AuthResponseDTO;
import lk.ijse.eca.userservice.dto.UserRequestDTO;
import lk.ijse.eca.userservice.dto.UserResponseDTO;
import lk.ijse.eca.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
@Validated
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> userRegister(
            @Validated({Default.class, UserRequestDTO.OnCreate.class}) @RequestBody UserRequestDTO dto){
        log.info("POST /api/v1/users - Username: {}", dto.getUsername());
        UserResponseDTO response = userService.userRegister(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(
            @Valid @RequestBody UserRequestDTO dto){
        log.info("POST /api/v1/users/login - Username: {}", dto.getUsername());
        AuthResponseDTO response = userService.login(dto);
        return ResponseEntity.ok(response);
    }

}
