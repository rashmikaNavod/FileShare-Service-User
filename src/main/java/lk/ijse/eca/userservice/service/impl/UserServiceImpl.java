package lk.ijse.eca.userservice.service.impl;

import lk.ijse.eca.userservice.dto.AuthResponseDTO;
import lk.ijse.eca.userservice.dto.UserRequestDTO;
import lk.ijse.eca.userservice.dto.UserResponseDTO;
import lk.ijse.eca.userservice.entity.User;
import lk.ijse.eca.userservice.exception.DuplicateUserException;
import lk.ijse.eca.userservice.exception.UserNotFoundException;
import lk.ijse.eca.userservice.repository.UserRepository;
import lk.ijse.eca.userservice.service.UserService;
import lk.ijse.eca.userservice.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public UserResponseDTO userRegister(UserRequestDTO dto) {
        log.debug("Registering user with username: {}", dto.getUsername());

        if(userRepository.existsByUsername(dto.getUsername())){
            log.warn("Duplicate username detected: {}", dto.getUsername());
             throw new DuplicateUserException(dto.getUsername());
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEmail(dto.getEmail());

        userRepository.save(user);
        log.info("User registered successfully: {}", user.getUsername());

        return new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail());
    }


    @Override
    public AuthResponseDTO login(UserRequestDTO dto) {
        log.debug("Attempting login for username: {}", dto.getUsername());

        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> {
                    log.warn("User not found: {}", dto.getUsername());
                    return new UserNotFoundException(dto.getUsername());
                });

        if(!passwordEncoder.matches(dto.getPassword(), user.getPassword())){
            log.warn("Invalid password for username: {}", dto.getUsername());
            throw new UserNotFoundException(dto.getUsername());
        }

        log.info("User authenticated, generating token for: {}", user.getUsername());
        return AuthResponseDTO.builder().token(jwtUtil.generateToken(user.getUsername())).build();
    }


}
