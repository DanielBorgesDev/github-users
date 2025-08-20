package com.example.github.controller;

import com.example.github.domain.Role;
import com.example.github.dto.RoleCreateRequest;
import com.example.github.dto.RoleResponse;
import com.example.github.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles")
public class RoleController {

  private final UserService userService;

  public RoleController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping
  public ResponseEntity<RoleResponse> create(@RequestBody RoleCreateRequest req) {
    Role r = userService.createRole(req.name());
    return ResponseEntity.ok(new RoleResponse(r.getId(), r.getName()));
  }
}
