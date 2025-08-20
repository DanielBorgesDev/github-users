package com.example.github.service;

import com.example.github.domain.*;
import com.example.github.repository.*;
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
    if (roleRepo.existsByName(name)) {
      return roleRepo.findByName(name).orElseThrow();
    }
    Role r = new Role();
    r.setName(name);
    return roleRepo.save(r);
  }

  @Transactional
  public void assignRole(Long userId, Long roleId) {
    var user = userRepo.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
    var role = roleRepo.findById(roleId)
        .orElseThrow(() -> new IllegalArgumentException("Role not found: " + roleId));

    boolean exists = userRoleRepo.existsByUserIdAndRoleId(userId, roleId);
    if (!exists) {
      UserRole ur = new UserRole();
      ur.setUser(user);
      ur.setRole(role);
      userRoleRepo.save(ur);
    }
  }
}
