import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { InputWorkingHoursRoutingModule } from './input-working-hours-routing.module';
import { InputWorkingHoursComponent } from './input-working-hours.component';

@NgModule({
    declarations: [InputWorkingHoursComponent],
    imports: [CommonModule, InputWorkingHoursRoutingModule],
})
export class InputWorkingHoursModule {}
