package mozgovoy.nikita.diploma.service;

import mozgovoy.nikita.diploma.exception.EmailNotFoundException;
import mozgovoy.nikita.diploma.exception.UserNotFoundException;
import mozgovoy.nikita.diploma.model.UserModel;
import mozgovoy.nikita.diploma.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserModelService implements UserDetailsService {
    private final UserRepo userRepo;
    @Autowired
    public UserModelService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public UserModel findUserById(Long id){
        try{
        return userRepo.findUserModelById(id);
        }
        catch (Exception e){
            throw new UserNotFoundException("User by id " + id + "wasn't found");
        }
    }

    public UserModel saveUser(UserModel user) {
        return userRepo.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findUserByUsername(username);
    }

    public UserDetails findUserByUsername(String username) throws UserNotFoundException {
        try{
            return userRepo.findUserModelByUsername(username);
        }
        catch (Exception e){
            throw new UserNotFoundException("User " + username + "wasn't found");
        }
    }

    public boolean existsByUsername(String username){
        return userRepo.existsByUsername(username);
    }

    public boolean existsByEmail(String email){
        return userRepo.existsByEmail(email);
    }

}
