import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { MatPaginatorIntl, MAT_PAGINATOR_DEFAULT_OPTIONS } from '@angular/material/paginator';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ServiceWorkerModule } from '@angular/service-worker';
import { ToastrModule } from 'ngx-toastr';
import { environment } from '../environments/environment';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MatPaginatorIntlJa } from './classes/mat-paginator-intl-ja';
import { LayoutModule } from './components/layouts/layout.module';
import { InterceptorService } from './services/interceptor.service';

@NgModule({
    declarations: [AppComponent],
    imports: [
        BrowserModule,
        AppRoutingModule,
        BrowserAnimationsModule,
        HttpClientModule,
        ServiceWorkerModule.register('ngsw-worker.js', {
            enabled: environment.production,
            // Register the ServiceWorker as soon as the app is stable
            // or after 30 seconds (whichever comes first).
            registrationStrategy: 'registerWhenStable:30000',
        }),
        ToastrModule.forRoot({
            progressBar: true,
        }),
        LayoutModule,
    ],
    providers: [
        {
            provide: HTTP_INTERCEPTORS,
            useClass: InterceptorService,
            multi: true,
        },
        {
            provide: MAT_PAGINATOR_DEFAULT_OPTIONS,
            useValue: { pageSizeOptions: [10, 25, 50, 100], showFirstLastButtons: true },
        },
        {
            provide: MatPaginatorIntl,
            useClass: MatPaginatorIntlJa,
        },
    ],
    bootstrap: [AppComponent],
})
export class AppModule {}
