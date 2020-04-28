import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DefaultComponent } from './default.component';
import { DashboardComponent } from 'src/app/modules/dashboard/dashboard.component';
import { RouterModule } from '@angular/router';
import { ReportsComponent } from 'src/app/modules/reports/reports.component';
import { MatSidenavModule } from '@angular/material/sidenav';
import { DashboardService } from 'src/app/modules/dashboard.service';
import { SharedModule } from 'src/app/shared/shared.module';
import { MatDividerModule } from '@angular/material/divider';
import { MatCardModule} from '@angular/material/card';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatTableModule } from '@angular/material/table';
import { FlexLayoutModule } from '@angular/flex-layout';

@NgModule({
  declarations: [
    DefaultComponent,
    DashboardComponent,
    ReportsComponent
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
    MatTableModule
  ],
  providers: [
    DashboardService
  ]
})
export class DefaultModule { }
