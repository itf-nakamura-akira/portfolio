import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSelectModule } from '@angular/material/select';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatSortModule } from '@angular/material/sort';
import { MatTableModule } from '@angular/material/table';
import { PageModule } from '../../shared/page/page.module';
import { HeaderComponent } from './header/header.component';
import { RegistButtonComponent } from './header/regist-button/regist-button.component';
import { SearchComponent } from './header/search/search.component';
import { ListComponent } from './list/list.component';
import { RegistDialogComponent } from './regist-dialog/regist-dialog.component';
import { ResetPasswordDialogComponent } from './reset-password-dialog/reset-password-dialog.component';
import { UpdateDialogComponent } from './update-dialog/update-dialog.component';
import { UsersMasterRoutingModule } from './users-master-routing.module';
import { UsersMasterComponent } from './users-master.component';

@NgModule({
    declarations: [
        UsersMasterComponent,
        SearchComponent,
        ListComponent,
        UpdateDialogComponent,
        HeaderComponent,
        RegistButtonComponent,
        RegistDialogComponent,
        ResetPasswordDialogComponent,
    ],
    imports: [
        CommonModule,
        ReactiveFormsModule,
        MatInputModule,
        MatSelectModule,
        MatSlideToggleModule,
        MatTableModule,
        MatSortModule,
        MatPaginatorModule,
        MatIconModule,
        MatDialogModule,
        MatButtonModule,
        UsersMasterRoutingModule,
        PageModule,
    ],
})
export class UsersMasterModule {}
