import { EnumMapping } from './enumMapping';

/**
 * ユーザー権限
 */
export enum UsersPermission {
    /**
     * 管理者
     */
    Admin,
    /**
     * 一般ユーザー
     */
    User,
    /**
     * 参照ユーザー
     */
    Referencer,
}

/**
 * ユーザー権限の文字列マッピング
 */
export const UsersPermissionMapping: EnumMapping<UsersPermission>[] = [
    { value: UsersPermission.Admin, name: '管理者' },
    { value: UsersPermission.User, name: '一般ユーザー' },
    { value: UsersPermission.Referencer, name: '参照ユーザー' },
];
