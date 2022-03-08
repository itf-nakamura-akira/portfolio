import { Injectable } from '@angular/core';
import { LocalStorageKey } from '../enums/LocalStorageKey';

/**
 * localstorageを扱うService
 */
@Injectable({
    providedIn: 'root',
})
export class LocalStorageService {
    /**
     * コンストラクター
     */
    constructor() {}

    /**
     * 文字列の値を保存する
     *
     * @param key キー
     * @param value 保存値
     */
    setValue(key: LocalStorageKey, value: string): void {
        localStorage.setItem(key.toString(), value);
    }

    /**
     * 文字列の値を取得する
     *
     * @param key キー
     *
     * @returns 保存値
     */
    getValue(key: LocalStorageKey): string | null {
        return localStorage.getItem(key.toString());
    }

    /**
     * ブール型の値を保存する
     *
     * @param key キー
     * @param value 保存値
     */
    setBooleanValue(key: LocalStorageKey, value: boolean): void {
        localStorage.setItem(key.toString(), String(value));
    }

    /**
     * ブール型の値を取得する
     *
     * @param key キー
     * @param defaultValue 保存された値が無い場合の戻り値(default: true)
     *
     * @returns 保存値
     */
    getBooleanValue(key: LocalStorageKey, defaultValue = true): boolean {
        return (this.getValue(key) || String(defaultValue)) === 'true';
    }
}
