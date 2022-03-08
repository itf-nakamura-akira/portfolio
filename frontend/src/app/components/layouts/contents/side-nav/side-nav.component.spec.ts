import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatIconModule } from '@angular/material/icon';
import { RouterTestingModule } from '@angular/router/testing';
import { SideMenuItemComponent } from './side-menu-item/side-menu-item.component';
import { SideNavComponent } from './side-nav.component';

describe('SideNavComponent', () => {
    let component: SideNavComponent;
    let fixture: ComponentFixture<SideNavComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [SideNavComponent, SideMenuItemComponent],
            imports: [RouterTestingModule, MatIconModule],
        }).compileComponents();
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(SideNavComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
