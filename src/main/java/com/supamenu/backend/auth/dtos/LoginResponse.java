package com.supamenu.backend.auth.dtos;

import com.supamenu.backend.users.Role;

public record LoginResponse(
        String accessToken,
        Role role
) {
}
