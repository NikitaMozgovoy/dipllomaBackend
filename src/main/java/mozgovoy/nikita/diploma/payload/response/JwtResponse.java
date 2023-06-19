package mozgovoy.nikita.diploma.payload.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import mozgovoy.nikita.diploma.dto.LocalReviewDTO;

import java.util.ArrayList;
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

    public JwtResponse(String accessToken, Long id, String username, String email){
        this.accessToken = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
    }

}
