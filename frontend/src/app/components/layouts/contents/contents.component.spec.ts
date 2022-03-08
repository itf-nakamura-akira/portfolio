import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { RouterTestingModule } from '@angular/router/testing';
import { HeaderComponent } from '../header/header.component';
import { MenuButtonComponent } from '../header/menu-button/menu-button.component';
import { UserButtonComponent } from '../header/user-button/user-button.component';
import { ContentsComponent } from './contents.component';
import { SideMenuItemComponent } from './side-nav/side-menu-item/side-menu-item.component';
import { SideNavComponent } from './side-nav/side-nav.component';

describe('ContentsComponent', () => {
    let component: ContentsComponent;
    let fixture: ComponentFixture<ContentsComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [
                ContentsComponent,
                HeaderComponent,
                SideNavComponent,
                MenuButtonComponent,
                UserButtonComponent,
                SideMenuItemComponent,
            ],
            imports: [RouterTestingModule, MatToolbarModule, MatIconModule],
        }).compileComponents();
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(ContentsComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
