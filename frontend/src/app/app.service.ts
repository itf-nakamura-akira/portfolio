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
     * APIを経由してローカルの認証済み情報を更新する
     */
    fetchIsAuthorized(): void {
        this.appHttpService.getIsAuthorized().subscribe((response) => this._isAuthorized$.next(response.isAuthorized));
    }

    /**
     * ローカルの認証済み情報を更新する
     *
     * @param 更新値
     */
    setIsAuthorized(isAuthorized: boolean): void {
        this._isAuthorized$.next(isAuthorized);
    }
}
