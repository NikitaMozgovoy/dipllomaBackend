package mozgovoy.nikita.diploma.payload.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import mozgovoy.nikita.diploma.model.ERole;

import java.util.List;

@Getter
@Setter
@Data
public class JwtResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private Long id;
    private String username;
    private String email;
    private ERole role;

    public JwtResponse(String accessToken, Long id, String username, String email, ERole role) {
        this.accessToken = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
    }

}
