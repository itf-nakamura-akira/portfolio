import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { HeaderComponent } from './header/header.component';
import { InputWorkingHoursRoutingModule } from './input-working-hours-routing.module';
import { InputWorkingHoursComponent } from './input-working-hours.component';
import { ListComponent } from './list/list.component';
import { SearchComponent } from './header/search/search.component';
import { ActionComponent } from './header/action/action.component';

@NgModule({
    declarations: [InputWorkingHoursComponent, HeaderComponent, ListComponent, SearchComponent, ActionComponent],
    imports: [CommonModule, InputWorkingHoursRoutingModule],
})
export class InputWorkingHoursModule {}
