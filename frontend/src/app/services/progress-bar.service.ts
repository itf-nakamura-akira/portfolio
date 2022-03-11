import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

/**
 * Http Clientの通信中に表示するプログレスバーを制御する Service
 */
@Injectable({
    providedIn: 'root',
})
export class ProgressBarService {
    /**
     * ローディング状態
     */
    private _stete$ = new BehaviorSubject<boolean>(false);

    /**
     * ローディング状態
     */
    get state$(): Observable<boolean> {
        return this._stete$.asObservable();
    }

    /**
     * コンストラクター
     */
    constructor() {}

    /**
     * 表示開始
     */
    start(): void {
        this._stete$.next(true);
    }

    /**
     * 表示終了
     */
    stop(): void {
        this._stete$.next(false);
    }
}
