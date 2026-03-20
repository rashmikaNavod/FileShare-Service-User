package lk.ijse.eca.userservice.service;

import lk.ijse.eca.userservice.dto.AuthResponseDTO;
import lk.ijse.eca.userservice.dto.UserRequestDTO;
import lk.ijse.eca.userservice.dto.UserResponseDTO;

public interface UserService {
    UserResponseDTO userRegister(UserRequestDTO dto);
    AuthResponseDTO login(UserRequestDTO dto);
}
