import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DefaultComponent } from './layouts/default/default.component';
import { DashboardComponent } from './modules/dashboard/dashboard.component';
import { ReportsComponent } from './modules/reports/reports.component';
import { DevicesComponent } from './modules/devices/devices.component';
import { MainComponent } from './modules/main/main.component';
import { WelcomeComponent } from './layouts/welcome/welcome.component';


const routes: Routes = [{
  path: '',
  component: WelcomeComponent
  },
  {
  path: ':username',
  component: DefaultComponent,
  children: [
  {
    path: '',
    component: MainComponent
  },
  {
    path: 'devices',
    component: DevicesComponent
  },
  {
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
