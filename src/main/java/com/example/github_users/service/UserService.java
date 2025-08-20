package com.example.github_users.service;

import com.example.github_users.domain.Role;
import com.example.github_users.domain.User;
import com.example.github_users.domain.UserRole;
import com.example.github_users.repository.RoleRepository;
import com.example.github_users.repository.UserRepository;
import com.example.github_users.repository.UserRoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

  private final UserRepository userRepo;
  private final RoleRepository roleRepo;
  private final UserRoleRepository userRoleRepo;

  public UserService(UserRepository userRepo, RoleRepository roleRepo, UserRoleRepository userRoleRepo) {
    this.userRepo = userRepo;
    this.roleRepo = roleRepo;
    this.userRoleRepo = userRoleRepo;
  }

  @Transactional(readOnly = true)
  public List<User> listUsersWithRoles() {
    return userRepo.findAllWithRoles();
  }

  @Transactional
  public Role createRole(String name) {
    return roleRepo.findByName(name).orElseGet(() -> {
      Role r = new Role();
      r.setName(name);
      return roleRepo.save(r);
    });
  }

  @Transactional
  public void assignRole(Long userId, Long roleId) {
    var user = userRepo.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
    var role = roleRepo.findById(roleId)
        .orElseThrow(() -> new IllegalArgumentException("Role not found: " + roleId));

    if (!userRoleRepo.existsByUserIdAndRoleId(userId, roleId)) {
      UserRole ur = new UserRole();
      ur.setUser(user);
      ur.setRole(role);
      userRoleRepo.save(ur);
    }
  }
}
