package org.spacebank.co.repository;

import java.util.List;
import java.util.Optional;

import org.spacebank.co.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsernameOrEmail(String username, String email);

    List<User> findByIdIn(List<Long> userIds);

    Optional<User> findByUsername(String username);
    
//    void createVerificationToken(User user, String token);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
