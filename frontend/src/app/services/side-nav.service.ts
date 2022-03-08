import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { LocalStorageKey } from '../enums/localStorageKey';
import { LocalStorageService } from './local-storage.service';

/**
 * サイドナビのService
 */
@Injectable({
    providedIn: 'root',
})
export class SideNavService {
    /**
     * 開閉状況
     */
    private _isOpen$ = new BehaviorSubject<boolean>(this.localStorageService.getBooleanValue(LocalStorageKey.SideNavOpenStatus, true));

    /**
     * 開閉状況
     */
    get isOpen$(): Observable<boolean> {
        return this._isOpen$.asObservable();
    }

    /**
     * コンストラクター
     *
     * @param localStorageService LocalStorageService
     */
    constructor(private localStorageService: LocalStorageService) {}

    /**
     * 開閉状況の切り替え
     */
    toggle(): void {
        const newValue: boolean = !this._isOpen$.value;

        this._isOpen$.next(newValue);
        this.localStorageService.setBooleanValue(LocalStorageKey.SideNavOpenStatus, newValue);
    }
}
