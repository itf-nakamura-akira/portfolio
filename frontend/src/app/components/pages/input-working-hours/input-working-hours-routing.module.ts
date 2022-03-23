import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { InputWorkingHoursComponent } from './input-working-hours.component';

const routes: Routes = [
    {
        path: '',
        component: InputWorkingHoursComponent,
    },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
})
export class InputWorkingHoursRoutingModule {}
