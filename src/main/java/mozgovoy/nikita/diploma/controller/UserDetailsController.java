package mozgovoy.nikita.diploma.controller;

import jakarta.transaction.Transactional;
import mozgovoy.nikita.diploma.model.UserModel;
import mozgovoy.nikita.diploma.service.UserModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserDetailsController {
    private final UserModelService userModelService;

    @Autowired
    public UserDetailsController(UserModelService userModelService) {
        this.userModelService = userModelService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserModel>> getAllUsers(){
        List<UserModel> users = userModelService.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<UserModel> getUserById(@PathVariable("id") Long id){
        UserModel user = userModelService.findUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<UserModel> addUser(@RequestBody UserModel user){
        UserModel newUser = userModelService.addUser(user);
        System.out.println(newUser);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserModel> updateUser(@RequestBody UserModel user, @PathVariable("id") Long id){
        UserModel updatedUser = userModelService.updateUser(user, id);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id){
        userModelService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
