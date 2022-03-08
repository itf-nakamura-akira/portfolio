import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { AppHttpService } from './app-http.service';

/**
 * アプリケーション Service
 */
@Injectable({
    providedIn: 'root',
})
export class AppService {
    /**
     * 認証済み情報
     */
    private _isAuthorized$ = new Subject<boolean>();

    /**
     * 認証済み情報
     */
    get isAuthorized$(): Observable<boolean> {
        return this._isAuthorized$.asObservable();
    }

    /**
     * コンストラクター
     *
     * @param appHttpService AppHttpService
     */
    constructor(private appHttpService: AppHttpService) {}

    /**
     * 認証済み情報を更新する
     *
     * @param isLogouted ログアウト済みか
     */
    updateIsAuthorized(isLogouted: boolean = false): void {
        if (isLogouted) {
            this._isAuthorized$.next(false);
        } else {
            this.appHttpService.getIsAuthorized().subscribe((response) => this._isAuthorized$.next(response.isAuthorized));
        }
    }
}
