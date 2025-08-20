package com.example.github_users.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.github_users.domain.Role;
import com.example.github_users.domain.User;
import com.example.github_users.repository.RoleRepository;
import com.example.github_users.repository.UserRepository;
import com.example.github_users.repository.UserRoleRepository;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock UserRepository userRepo;
  @Mock RoleRepository roleRepo;
  @Mock UserRoleRepository userRoleRepo;

  @InjectMocks UserService service;

  @Test
  void assignRole_shouldSave_whenNotExists() {
    var user = new User();
    var role = new Role();
    when(userRepo.findById(1L)).thenReturn(Optional.of(user));
    when(roleRepo.findById(10L)).thenReturn(Optional.of(role));
    when(userRoleRepo.existsByUserIdAndRoleId(1L, 10L)).thenReturn(false);

    service.assignRole(1L, 10L);

    verify(userRoleRepo, times(1)).save(any());
  }
}
