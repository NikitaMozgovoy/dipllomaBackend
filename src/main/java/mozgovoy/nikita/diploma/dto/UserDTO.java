package mozgovoy.nikita.diploma.dto;

import lombok.Data;
import mozgovoy.nikita.diploma.model.UserModel;

import java.io.Serializable;
@Data
public class UserDTO implements Serializable {
    private Long id;
    private String username;
    private String email;

    public UserDTO(UserModel user) {
        this.id= user.getId();
        this.username=user.getUsername();
        this.email=user.getEmail();
    }
}
