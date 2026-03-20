package lk.ijse.eca.userservice.exception;

public class DuplicateUserException extends RuntimeException {
    public DuplicateUserException(String username) {

        super("User with username '" + username + "' already exists");
    }
}
