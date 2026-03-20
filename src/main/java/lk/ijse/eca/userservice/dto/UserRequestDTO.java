package lk.ijse.eca.userservice.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDTO {

    public interface OnCreate {}

    @NotBlank(message = "username is required")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_]{2,19}$", message = "Username must start with a letter and can contain letters, numbers, and underscores")
    private String username;

    @NotBlank(groups = OnCreate.class, message = "email is required")
    @Email(groups = OnCreate.class, message = "Invalid email format")
    private String email;

    @NotBlank(message = "password is required")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$",
            message = "Password must be 8-20 characters long and include uppercase, lowercase, number, and special character"
    )
    private String password;

}
