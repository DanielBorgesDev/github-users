package com.example.github_users.dto;

import java.util.List;

public record UserResponse(Long id, String login, String url, List<RoleResponse> roles) {}
