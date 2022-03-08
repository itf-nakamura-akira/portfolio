import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { RouterModule } from '@angular/router';
import { ContentsComponent } from './contents/contents.component';
import { SideMenuItemComponent } from './contents/side-nav/side-menu-item/side-menu-item.component';
import { SideNavComponent } from './contents/side-nav/side-nav.component';
import { HeaderComponent } from './header/header.component';
import { MenuButtonComponent } from './header/menu-button/menu-button.component';
import { UserButtonComponent } from './header/user-button/user-button.component';

@NgModule({
    declarations: [HeaderComponent, MenuButtonComponent, SideNavComponent, ContentsComponent, UserButtonComponent, SideMenuItemComponent],
    imports: [CommonModule, RouterModule, MatToolbarModule, MatIconModule, MatButtonModule],
    exports: [HeaderComponent, SideNavComponent, ContentsComponent],
})
export class LayoutModule {}
