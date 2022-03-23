import { Component } from '@angular/core';
import { InputWorkingHoursService } from './input-working-hours.service';

@Component({
    selector: 'app-input-working-hours',
    templateUrl: './input-working-hours.component.html',
    styleUrls: ['./input-working-hours.component.scss'],
    providers: [InputWorkingHoursService],
})
export class InputWorkingHoursComponent {
    /**
     * コンストラクター
     */
    constructor() {}
}
