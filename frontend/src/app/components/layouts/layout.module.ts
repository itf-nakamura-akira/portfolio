import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatToolbarModule } from '@angular/material/toolbar';
import { RouterModule } from '@angular/router';
import { ContentsComponent } from './contents/contents.component';
import { HeaderComponent } from './contents/header/header.component';
import { MenuButtonComponent } from './contents/header/menu-button/menu-button.component';
import { UserButtonComponent } from './contents/header/user-button/user-button.component';
import { SideMenuItemComponent } from './contents/side-nav/side-menu-item/side-menu-item.component';
import { SideNavComponent } from './contents/side-nav/side-nav.component';

@NgModule({
    declarations: [HeaderComponent, MenuButtonComponent, SideNavComponent, ContentsComponent, UserButtonComponent, SideMenuItemComponent],
    imports: [CommonModule, RouterModule, MatToolbarModule, MatIconModule, MatButtonModule, MatMenuModule],
    exports: [HeaderComponent, SideNavComponent, ContentsComponent],
})
export class LayoutModule {}
