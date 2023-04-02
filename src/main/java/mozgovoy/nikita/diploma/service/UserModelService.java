package mozgovoy.nikita.diploma.service;

import io.jsonwebtoken.ClaimsMutator;
import mozgovoy.nikita.diploma.model.UserModel;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserModelService extends UserDetailsService {
    UserModel findUserById(Long id);
    void saveUser(UserModel user);

    @Override
    default UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

}
