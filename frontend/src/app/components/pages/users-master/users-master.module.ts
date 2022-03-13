import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatSortModule } from '@angular/material/sort';
import { MatTableModule } from '@angular/material/table';
import { ListComponent } from './list/list.component';
import { SearchComponent } from './search/search.component';
import { UsersMasterRoutingModule } from './users-master-routing.module';
import { UsersMasterComponent } from './users-master.component';

@NgModule({
    declarations: [UsersMasterComponent, SearchComponent, ListComponent],
    imports: [
        CommonModule,
        ReactiveFormsModule,
        MatInputModule,
        MatSelectModule,
        MatSlideToggleModule,
        MatTableModule,
        MatSortModule,
        UsersMasterRoutingModule,
    ],
})
export class UsersMasterModule {}
