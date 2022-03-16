import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { UserButtonHttpService } from './user-button-http.service';

describe('UserButtonHttpService', () => {
    let service: UserButtonHttpService;

    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [HttpClientModule, RouterTestingModule],
        });
        service = TestBed.inject(UserButtonHttpService);
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });
});
