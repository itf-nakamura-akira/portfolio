/**
 * Enumと文字列のマッピングインターフェイス
 */
export interface EnumMapping<T> {
    /**
     * Enum
     */
    value: T;

    /**
     * 文字列
     */
    name: string;
}
