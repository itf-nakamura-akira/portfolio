import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatIconModule } from '@angular/material/icon';
import { RouterTestingModule } from '@angular/router/testing';
import { SideMenuItemComponent } from './side-menu-item.component';

describe('SideMenuItemComponent', () => {
    let component: SideMenuItemComponent;
    let fixture: ComponentFixture<SideMenuItemComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [SideMenuItemComponent],
            imports: [MatIconModule, RouterTestingModule],
        }).compileComponents();
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(SideMenuItemComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
