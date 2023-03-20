package mozgovoy.nikita.diploma.repository;

import mozgovoy.nikita.diploma.model.ERole;
import mozgovoy.nikita.diploma.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
