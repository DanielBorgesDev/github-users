package com.example.github.controller;

import com.example.github.domain.Role;
import com.example.github.domain.User;
import com.example.github.domain.UserRole;
import com.example.github.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UsersController.class)
class UsersControllerTest {

  @Autowired MockMvc mvc;
  @MockBean UserService userService;

  @Test
  void list_shouldReturnOk() throws Exception {
    var user = new User();
    Role r = new Role();
    r.setName("ADMIN");
    var ur = new UserRole();
    ur.setRole(r);
    user.getUserRoles().add(ur);

    Mockito.when(userService.listUsersWithRoles()).thenReturn(List.of(user));

    mvc.perform(get("/users").accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk());
  }
}
