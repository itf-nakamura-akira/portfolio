import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule } from '@angular/material/dialog';
import { MessageDialogComponent } from './message-dialog.component';

@NgModule({
    declarations: [MessageDialogComponent],
    imports: [CommonModule, MatDialogModule, MatButtonModule],
    exports: [MessageDialogComponent],
})
export class MessageDialogModule {}
