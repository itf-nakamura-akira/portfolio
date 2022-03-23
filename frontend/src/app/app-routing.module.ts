import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './guards/auth.guard';

const routes: Routes = [
    {
        path: '',
        children: [
            {
                path: 'login',
                loadChildren: () => import('./components/pages/login/login.module').then((m) => m.LoginModule),
                canActivate: [AuthGuard],
            },
            {
                path: 'dashboard',
                loadChildren: () => import('./components/pages/dashboard/dashboard.module').then((m) => m.DashboardModule),
                canActivate: [AuthGuard],
            },
            {
                path: 'inputWorkingHours',
                loadChildren: () =>
                    import('./components/pages/input-working-hours/input-working-hours.module').then((m) => m.InputWorkingHoursModule),
                canActivate: [AuthGuard],
            },
            {
                path: 'usersMaster',
                loadChildren: () => import('./components/pages/users-master/users-master.module').then((m) => m.UsersMasterModule),
                canActivate: [AuthGuard],
            },
            {
                path: '',
                pathMatch: 'full',
                redirectTo: 'dashboard',
            },
        ],
    },
];

@NgModule({
    imports: [RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })],
    exports: [RouterModule],
})
export class AppRoutingModule {}
