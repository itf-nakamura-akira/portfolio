package dev.yukikaze.portfolio.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import dev.yukikaze.portfolio.enums.UsersPermission;

/**
 * ユーザー権限のチェックを行う 指定されたUsersPermissionが無ければ、403を返す
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
public @interface Permission {
    /**
     * APIリクエストに必要なユーザー権限
     *
     * @return APIリクエストに必要なユーザー権限
     */
    UsersPermission[] value();
}
