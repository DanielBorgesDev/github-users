package com.example.github.domain;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "users")
public class User {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String login;

  @Column(nullable = false)
  private String url;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<UserRole> userRoles = new HashSet<>();

  // getters/setters

  public Long getId() { return id; }
  public String getLogin() { return login; }
  public String getUrl() { return url; }
  public void setLogin(String login) { this.login = login; }
  public void setUrl(String url) { this.url = url; }

  public Set<UserRole> getUserRoles() { return userRoles; }

  // Conveniência: obter nomes das roles
  @Transient
  public Set<String> getRoleNames() {
    Set<String> names = new HashSet<>();
    for (UserRole ur : userRoles) {
      if (ur.getRole() != null) names.add(ur.getRole().getName());
    }
    return names;
  }
}
