import { Component, OnInit, ViewChild } from '@angular/core';
import { IDevice } from 'src/app/shared/model/device';
import { DeviceService } from 'src/app/shared/services/device.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  devices: IDevice[];
  isDisabled = true;

  constructor(public deviceService: DeviceService) { }

  ngOnInit(): void {
    this.refreshDashboard();
  }

  private refreshDashboard() {
    this.deviceService.getDevices().subscribe( data => {
      this.devices = data;
    });
    this.checkConnection();
  }

  checkConnection() {
    this.deviceService.hasConnection().subscribe( hasConnection => {
      if (hasConnection) {
        console.log('User has connection with dashboard devices');
        this.isDisabled = false;
      } else {
        this.deviceService.init().subscribe( response => {
          this.isDisabled = false;
          console.log(response);
        });
      }
    });
  }
}