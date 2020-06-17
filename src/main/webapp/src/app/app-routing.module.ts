import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DefaultComponent } from './layouts/default/default.component';
import { DashboardComponent } from './modules/dashboard/dashboard.component';
import { ReportsComponent } from './modules/reports/reports.component';
import { DevicesComponent } from './modules/devices/devices.component';
import { MainComponent } from './modules/main/main.component';
import { WelcomeComponent } from './layouts/welcome/welcome.component';
import { SettingsComponent } from './modules/settings/settings.component';
import { CameraComponent } from './modules/camera/camera.component';
import { WikiComponent } from './modules/wiki/wiki.component';
import { SchemeComponent } from './modules/scheme/scheme.component';


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
    path: 'scheme',
    component: SchemeComponent
  },
  {
    path: 'reports',
    component: ReportsComponent
  },
  {
    path: 'settings',
    component: SettingsComponent
  },
  {
    path: 'cam',
    component: CameraComponent
  },
  {
    path: 'wiki',
    component: WikiComponent
  }]
}];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
