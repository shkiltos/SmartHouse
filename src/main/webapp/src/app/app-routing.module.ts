import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DefaultComponent } from './layouts/default/default.component';
import { DashboardComponent } from './modules/dashboard/dashboard.component';
import { ReportsComponent } from './modules/reports/reports.component';


const routes: Routes = [{
  path: '',
  component: DefaultComponent,
  children: [{
    path: 'dashboard',
    component: DashboardComponent
  },
  {
    path: 'reports',
    component: ReportsComponent
  }]
}];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
