package com.example.github.repository;

import com.example.github.domain.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
  @Query("select (count(1) > 0) from UserRole ur where ur.user.id = ?1 and ur.role.id = ?2")
  boolean existsByUserIdAndRoleId(Long userId, Long roleId);
}
