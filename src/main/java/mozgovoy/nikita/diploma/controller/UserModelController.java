package mozgovoy.nikita.diploma.controller;

import jakarta.transaction.Transactional;
import mozgovoy.nikita.diploma.model.UserModel;
import mozgovoy.nikita.diploma.service.UserModelServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
@RequestMapping("/users")
public class UserModelController {
    private final UserModelServiceImpl userModelServiceImpl;

    @Autowired
    public UserModelController(UserModelServiceImpl userModelServiceImpl) {
        this.userModelServiceImpl = userModelServiceImpl;
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserModel>> getAllUsers(){
        List<UserModel> users = userModelServiceImpl.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<UserModel> getUserById(@PathVariable("id") Long id){
        UserModel user = userModelServiceImpl.findUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<UserModel> addUser(@RequestBody UserModel user){
        UserModel newUser = userModelServiceImpl.addUser(user);
        System.out.println(newUser);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserModel> updateUser(@RequestBody UserModel user, @PathVariable("id") Long id){
        UserModel updatedUser = userModelServiceImpl.updateUser(user, id);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id){
        userModelServiceImpl.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
