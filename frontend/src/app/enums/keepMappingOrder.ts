import { KeyValue } from '@angular/common';

/**
 * KeyValue<string, string> のEnumのMappingをkeyvaluePipeに通したときに、ソート順を維持するための関数パラメーター
 *
 * @param a 比較対象1
 * @param b 比較対象2
 * @returns 比較結果
 */
export function keepMappingOrder(a: KeyValue<string, string>, b: KeyValue<string, string>): number {
    return 0;
}
