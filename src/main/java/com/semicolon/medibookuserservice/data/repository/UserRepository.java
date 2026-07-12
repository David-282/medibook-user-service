package com.semicolon.medibookuserservice.data.repository;

import com.semicolon.medibookuserservice.data.models.User;
import com.semicolon.medibookuserservice.enums.Role;
import com.semicolon.medibookuserservice.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsById(UUID id);

    List<User> findByRole(Role role);

    List<User> findByStatus(UserStatus status);

    List<User> findByRoleAndStatus(Role role, UserStatus status);
}
