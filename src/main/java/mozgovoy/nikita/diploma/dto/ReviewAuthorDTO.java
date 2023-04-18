package mozgovoy.nikita.diploma.dto;

import lombok.Data;
import mozgovoy.nikita.diploma.model.UserModel;

import java.io.Serializable;

@Data
public class ReviewAuthorDTO implements Serializable {
    private long id;
    private String username;

    public ReviewAuthorDTO(UserModel user) {
        this.id=user.getId();
        this.username=user.getUsername();
    }
}
