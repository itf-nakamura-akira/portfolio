import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { UserButtonHttpService } from './user-button-http.service';
import { UserButtonService } from './user-button.service';

describe('UserButtonService', () => {
    let service: UserButtonService;

    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [RouterTestingModule, HttpClientModule],
            providers: [UserButtonHttpService, UserButtonService],
        });
        service = TestBed.inject(UserButtonService);
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });
});
