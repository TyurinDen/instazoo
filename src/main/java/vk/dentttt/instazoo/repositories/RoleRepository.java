package vk.dentttt.instazoo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vk.dentttt.instazoo.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role getByName(String name);

}
