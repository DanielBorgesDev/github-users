package com.example.github.dto;

import java.util.List;

public record UserResponse(Long id, String login, String url, List<RoleResponse> roles) {}
