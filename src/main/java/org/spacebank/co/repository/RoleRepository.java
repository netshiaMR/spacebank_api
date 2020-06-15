package org.spacebank.co.repository;

import java.util.Optional;

import org.spacebank.co.models.Role;
import org.spacebank.co.models.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(RoleName roleName);
}
