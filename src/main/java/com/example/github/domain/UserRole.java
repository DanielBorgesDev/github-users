package com.example.github.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "user_roles",
       uniqueConstraints = @UniqueConstraint(columnNames = {"user_id","role_id"}))
public class UserRole {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "role_id", nullable = false)
  private Role role;

  // getters/setters
  public Long getId() { return id; }
  public User getUser() { return user; }
  public Role getRole() { return role; }
  public void setUser(User user) { this.user = user; }
  public void setRole(Role role) { this.role = role; }
}
