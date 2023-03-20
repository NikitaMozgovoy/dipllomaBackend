package mozgovoy.nikita.diploma.service;

import mozgovoy.nikita.diploma.exception.UserNotFoundException;
import mozgovoy.nikita.diploma.model.UserModel;
import mozgovoy.nikita.diploma.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserModelService {
    private final UserRepo userRepo;

    @Autowired
    public UserModelService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public UserModel addUser(UserModel user){
//        Optional<CustomUser> customUserOptional = userRepo.findCustomUserByEmail(user.getEmail());
//        if (customUserOptional.isPresent()){
//            throw new IllegalStateException("email taken");
//        }
        return userRepo.save(user);
    }

    public List<UserModel> findAllUsers(){
        return userRepo.findAll();
    }

    public UserModel updateUser(UserModel user, Long id){
        UserModel updatedUser = userRepo.findUserById(id);
        updatedUser.setEmail(user.getEmail());
        updatedUser.setUsername(user.getUsername());
        updatedUser.setAvatarUrl(user.getAvatarUrl());
        return userRepo.save(updatedUser);
    }

    public void deleteUser(Long id){
        userRepo.deleteUserById(id);
    }

    public UserModel findUserById(Long id){
        try{
        return userRepo.findUserById(id);
        }
        catch (Exception e){
            throw new UserNotFoundException("User by id " + id + "wasn't found");
        }
    }

    public Optional<UserModel> findUserByUsername(String username){
        try{
            return userRepo.findUserByUsername(username);
        }
        catch (Exception e){
            throw new UserNotFoundException("User " + username + "wasn't found");
        }
    }
}