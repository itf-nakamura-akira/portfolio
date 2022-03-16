import { TestBed } from '@angular/core/testing';
import { MatDialogModule } from '@angular/material/dialog';
import { MessageDialogService } from './message-dialog.service';

describe('MessageDialogService', () => {
    let service: MessageDialogService;

    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [MatDialogModule],
        });
        service = TestBed.inject(MessageDialogService);
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });
});
