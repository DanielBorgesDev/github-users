package com.example.github_users.controller;

import com.example.github_users.domain.User;
import com.example.github_users.dto.RoleResponse;
import com.example.github_users.dto.UserResponse;
import com.example.github_users.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

  private final UserService userService;

  public UsersController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public ResponseEntity<List<UserResponse>> list() {
    List<User> users = userService.listUsersWithRoles();
    var payload = users.stream().map(u ->
        new UserResponse(
            u.getId(),
            u.getLogin(),
            u.getUrl(),
            u.getUserRoles().stream()
              .map(ur -> new RoleResponse(ur.getRole().getId(), ur.getRole().getName()))
              .distinct()
              .toList()
        )
    ).toList();
    return ResponseEntity.ok(payload);
  }

  @PostMapping("/{userId}/roles/{roleId}")
  public ResponseEntity<Void> assign(@PathVariable Long userId, @PathVariable Long roleId) {
    userService.assignRole(userId, roleId);
    return ResponseEntity.noContent().build();
  }
}
