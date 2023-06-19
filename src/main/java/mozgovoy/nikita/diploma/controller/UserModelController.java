package mozgovoy.nikita.diploma.controller;

import mozgovoy.nikita.diploma.dto.UserDTO;
import mozgovoy.nikita.diploma.service.UserModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
@RequestMapping("/users")
public class UserModelController {
    private final UserModelService userModelService;
    @Autowired
    public UserModelController(UserModelService userModelService) {
        this.userModelService = userModelService;
    }

    @RequestMapping("/{id}/profile")
    public ResponseEntity<UserDTO> getUserProfile(@PathVariable("id") Long id){
        return new ResponseEntity<>(new UserDTO(this.userModelService.findUserById(id)), HttpStatus.OK);
    }
}
