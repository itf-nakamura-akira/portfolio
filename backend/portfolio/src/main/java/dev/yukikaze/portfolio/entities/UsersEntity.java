package dev.yukikaze.portfolio.entities;

import dev.yukikaze.portfolio.enums.UsersPermission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * users テーブルのEntity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersEntity {
    /**
     * ID
     */
    private Long id;

    /**
     * アカウント
     */
    private String account;

    /**
     * パスワードハッシュ
     */
    private String passwordHash;

    /**
     * 表示名
     */
    private String name;

    /**
     * ユーザー権限
     */
    private UsersPermission permission;

    /**
     * 有効フラグ
     */
    private Boolean isEnabled;
}
