package mozgovoy.nikita.diploma.dto;

import lombok.Data;
import mozgovoy.nikita.diploma.model.UserModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserDTO implements Serializable {
    private Long id;
    private String username;
    private String email;
    private List<LocalReviewDTO> reviews = new ArrayList<>();

    public UserDTO(UserModel user) {
        this.id= user.getId();
        this.username=user.getUsername();
        this.email=user.getEmail();
        this.reviews = LocalReviewDTO.getDTOArray(user.getReviews());
    }
}
