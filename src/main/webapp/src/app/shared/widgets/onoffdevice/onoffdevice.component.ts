import { Component, OnInit, Input, ViewEncapsulation } from '@angular/core';
import { DeviceService } from '../../services/device.service';
import { IDevice } from '../../model/device';
import { MatDialog } from '@angular/material/dialog';
import { AlertComponent } from '../../dialogs/alert/alert.component';
import { outOfEnergyLimitMessage } from '../../dialogs/dialog.constants';

@Component({
  selector: 'app-widget-onoffdevice',
  templateUrl: './onoffdevice.component.html',
  styleUrls: ['./onoffdevice.component.scss']
})
export class OnoffdeviceComponent implements OnInit {
  @Input() device: IDevice;
  @Input() theme: boolean;
  @Input() mini = false;
  onState: string;
  offState: string;

  imageName: string;

  constructor(public deviceService: DeviceService, public dialog: MatDialog) { }

  ngOnInit(): void {
    this.imageName =  '../../../../assets/icons/' + this.device.image + '.svg';
    this.onState = this.deviceService.parsePatternOn(this.device);
    this.offState = this.deviceService.parsePatternOff(this.device);
  }

  toggle() {
    if (this.device.state === this.offState || this.device.state === null) {
      this.deviceService.publishOnOffMessage(this.device.id, this.onState).subscribe( response => {
        if (this.exeeds(response)) {
          this.openAlert(outOfEnergyLimitMessage);
        } else {
          this.device.state = this.onState;
        }
      });
    } else {
      this.deviceService.publishOnOffMessage(this.device.id, this.offState).subscribe( response => {
        this.device.state = this.offState;
      });
    }
  }

  openAlert(alertMsg: string) {
    this.dialog.open(AlertComponent, {
      data: alertMsg
    });
  }

  exeeds(response: string): boolean {
    return response === 'exceed';
  }
}
