package dev.yukikaze.portfolio.objects;

import dev.yukikaze.portfolio.enums.UsersPermission;

/**
 * Cookieに含まれるJWTのPayload情報
 */
public record JwtPayload(Long id, UsersPermission permission, Long exp) {
}
