import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatDividerModule } from '@angular/material/divider';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule} from '@angular/material/button';
import { MatButtonToggleModule} from '@angular/material/button-toggle';
import { MatMenuModule} from '@angular/material/menu';
import { MatListModule } from '@angular/material/list';
import { FlexLayoutModule } from '@angular/flex-layout';
import { RouterModule } from '@angular/router';

import { DeviceService } from 'src/app/shared/services/device.service';
import { UserService } from 'src/app/shared/services/user.service';

import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { SidebarComponent } from './components/sidebar/sidebar.component';
import { AreaComponent } from './widgets/area/area.component';
import { HighchartsChartModule } from 'highcharts-angular';
import { CardComponent } from './widgets/card/card.component';
import { PieComponent } from './widgets/pie/pie.component';
import { OnoffdeviceComponent } from './widgets/onoffdevice/onoffdevice.component';
import { HttpClientModule } from '@angular/common/http';
import { SensorComponent } from './widgets/sensor/sensor.component';
import { EditDialogComponent } from './dialogs/edit/edit.dialog.component';
import { AddDialogComponent } from './dialogs/add/add.dialog.component';
import { DeleteDialogComponent } from './dialogs/delete/delete.dialog.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatSelectModule } from '@angular/material/select';

@NgModule({
  declarations: [
    HeaderComponent,
    FooterComponent,
    SidebarComponent,
    AreaComponent,
    CardComponent,
    PieComponent,
    OnoffdeviceComponent,
    SensorComponent,
    AddDialogComponent,
    EditDialogComponent,
    DeleteDialogComponent
  ],
  imports: [
    CommonModule,
    HttpClientModule,
    MatDividerModule,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    MatButtonToggleModule,
    FlexLayoutModule,
    MatMenuModule,
    MatListModule,
    RouterModule,
    HighchartsChartModule,
    FormsModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatSnackBarModule,
    MatSelectModule
  ],
  exports: [
    HeaderComponent,
    FooterComponent,
    SidebarComponent,
    AreaComponent,
    CardComponent,
    PieComponent,
    OnoffdeviceComponent,
    SensorComponent
  ],
  entryComponents: [
    AddDialogComponent,
    EditDialogComponent,
    DeleteDialogComponent
  ],
  providers: [
    DeviceService,
    UserService
  ]
})
export class SharedModule { }
