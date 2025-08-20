package com.example.github_users.repository;

import com.example.github_users.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(String name);
  boolean existsByName(String name);
}
