/**
 * ユーザー権限
 */
export enum UsersPermission {
    /**
     * 管理者
     */
    Admin = 'Admin',
    /**
     * 一般ユーザー
     */
    User = 'User',
    /**
     * 参照ユーザー
     */
    Referencer = 'Referencer',
}

/**
 * ユーザー権限の文字列マッピング
 */
export const UsersPermissionMapping: { [key: string]: string } = {
    Admin: '管理者',
    User: '一般ユーザー',
    Referencer: '参照ユーザー',
};
