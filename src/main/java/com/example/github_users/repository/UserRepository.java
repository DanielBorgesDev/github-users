package com.example.github_users.repository;

import com.example.github_users.domain.User;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.*;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByLogin(String login);

  @Query("""
    select distinct u from User u
    left join fetch u.userRoles ur
    left join fetch ur.role
  """)
  List<User> findAllWithRoles();

  @Query("select (count(1) > 0) from User u where u.login = :login")
  boolean existsByLogin(@Param("login") String login);
}
