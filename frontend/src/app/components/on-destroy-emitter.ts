import { EventEmitter, Injectable, OnDestroy } from '@angular/core';

/**
 * OnDestroy の実装を補助する拡張クラス
 */
@Injectable()
export class OnDestroyEmitter implements OnDestroy {
    /**
     * OnDestroy の Emitter
     */
    protected readonly onDestroy$ = new EventEmitter();

    /**
     * OnDestroy
     */
    ngOnDestroy(): void {
        this.onDestroy$.emit();
    }
}
