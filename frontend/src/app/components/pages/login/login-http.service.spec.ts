import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { LoginHttpService } from './login-http.service';

describe('LoginHttpService', () => {
    let service: LoginHttpService;

    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [HttpClientModule],
        });
        service = TestBed.inject(LoginHttpService);
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });
});
