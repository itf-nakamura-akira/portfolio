import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { MatBottomSheetModule } from '@angular/material/bottom-sheet';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatListModule } from '@angular/material/list';
import { LoginRoutingModule } from './login-routing.module';
import { LoginComponent } from './login.component';
import { TestUserBottomSheetComponent } from './test-user-bottom-sheet/test-user-bottom-sheet.component';

@NgModule({
    declarations: [LoginComponent, TestUserBottomSheetComponent],
    imports: [
        CommonModule,
        ReactiveFormsModule,
        MatInputModule,
        MatButtonModule,
        MatCardModule,
        MatBottomSheetModule,
        MatListModule,
        MatIconModule,
        LoginRoutingModule,
    ],
})
export class LoginModule {}
