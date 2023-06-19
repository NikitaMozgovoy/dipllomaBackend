package mozgovoy.nikita.diploma.repository;

import mozgovoy.nikita.diploma.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserModel, Long> {
    void deleteUserModelById(Long id);
    UserModel findUserModelById(Long id);
    UserModel findUserModelByUsername(String username);
    UserModel findUserModelByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}