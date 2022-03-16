import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { ToastrModule } from 'ngx-toastr';
import { InterceptorService } from './interceptor.service';

describe('InterceptorService', () => {
    let service: InterceptorService;

    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [
                RouterTestingModule,
                HttpClientModule,
                ToastrModule.forRoot({
                    progressBar: true,
                }),
            ],
            providers: [],
        });
        service = TestBed.inject(InterceptorService);
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });
});
