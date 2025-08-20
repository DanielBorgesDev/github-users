package com.example.github_users.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.github_users.domain.Role;
import com.example.github_users.domain.User;
import com.example.github_users.domain.UserRole;
import com.example.github_users.service.UserService;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UsersController.class) // <- só o controller
class UsersControllerTest {

  @Autowired
  MockMvc mvc;

  @MockBean
  UserService userService; // <- dependência do controller, mockada

  @Test
  void list_shouldReturnOk() throws Exception {
    var user = new User();
    var role = new Role();
    role.setName("ADMIN");
    var ur = new UserRole();
    ur.setRole(role);
    user.getUserRoles().add(ur);

    Mockito.when(userService.listUsersWithRoles()).thenReturn(List.of(user));

    mvc.perform(get("/users").accept(MediaType.APPLICATION_JSON))
       .andExpect(status().isOk());
  }
}
