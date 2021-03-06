import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DefaultComponent } from './default.component';
import { DashboardComponent } from 'src/app/modules/dashboard/dashboard.component';
import { RouterModule } from '@angular/router';
import { ReportsComponent } from 'src/app/modules/reports/reports.component';
import { MatSidenavModule } from '@angular/material/sidenav';
import { SharedModule } from 'src/app/shared/shared.module';
import { MatDividerModule } from '@angular/material/divider';
import { MatCardModule} from '@angular/material/card';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatTableModule } from '@angular/material/table';
import { MatInputModule } from '@angular/material/input';
import { MatTabsModule } from '@angular/material/tabs';
import { MatSortModule } from '@angular/material/sort';
import { FlexLayoutModule } from '@angular/flex-layout';
import { DevicesComponent } from 'src/app/modules/devices/devices.component';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MainComponent } from 'src/app/modules/main/main.component';
import { SettingsComponent } from 'src/app/modules/settings/settings.component';
import { CameraComponent } from 'src/app/modules/camera/camera.component';
import { FormsModule,  ReactiveFormsModule } from '@angular/forms';
import { WikiComponent } from 'src/app/modules/wiki/wiki.component';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { SortablejsModule } from 'ngx-sortablejs';
import { SchemeComponent } from 'src/app/modules/scheme/scheme.component';

@NgModule({
  declarations: [
    DefaultComponent,
    DevicesComponent,
    DashboardComponent,
    SchemeComponent,
    ReportsComponent,
    MainComponent,
    SettingsComponent,
    CameraComponent,
    WikiComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    SharedModule,
    MatSidenavModule,
    MatDividerModule,
    FlexLayoutModule,
    MatCardModule,
    MatPaginatorModule,
    MatTableModule,
    MatInputModule,
    MatSortModule,
    MatIconModule,
    MatButtonModule,
    MatSelectModule,
    FormsModule,
    DragDropModule,
    SortablejsModule,
    MatTabsModule,
    ReactiveFormsModule
  ],
  providers: []
})
export class DefaultModule { }
