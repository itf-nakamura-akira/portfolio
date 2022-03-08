import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UsersMasterComponent } from './users-master.component';

const routes: Routes = [
    {
        path: '',
        component: UsersMasterComponent,
    },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
})
export class UsersMasterRoutingModule {}
