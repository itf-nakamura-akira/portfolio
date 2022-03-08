import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
    {
        path: 'login',
        loadChildren: () => import('./components/pages/login/login.module').then((m) => m.LoginModule),
    },
    {
        path: 'dashboard',
        loadChildren: () => import('./components/pages/dashboard/dashboard.module').then((m) => m.DashboardModule),
    },
    {
        path: 'usersMaster',
        loadChildren: () => import('./components/pages/users-master/users-master.module').then((m) => m.UsersMasterModule),
    },
    {
        path: '',
        pathMatch: 'full',
        redirectTo: 'dashboard',
    },
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule],
})
export class AppRoutingModule {}
