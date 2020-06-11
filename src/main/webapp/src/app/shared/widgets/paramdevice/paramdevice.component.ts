import { Component, OnInit, Input } from '@angular/core';
import { AlertComponent } from '../../dialogs/alert/alert.component';
import { DeviceService } from '../../services/device.service';
import { MatDialog } from '@angular/material/dialog';
import { outOfEnergyLimitMessage } from '../../dialogs/dialog.constants';
import { IDevice } from '../../model/device';

@Component({
  selector: 'app-widget-paramdevice',
  templateUrl: './paramdevice.component.html',
  styleUrls: ['./paramdevice.component.scss']
})
export class ParamdeviceComponent implements OnInit {
  @Input() device: IDevice;
  @Input() theme: boolean;
  onState: string;
  offState: string;

  stateToSend: string;

  imageName: string;

  constructor(public deviceService: DeviceService, public dialog: MatDialog) { }

  ngOnInit(): void {
    this.imageName =  '../../../../assets/icons/' + this.device.image + '.svg';
    this.onState = this.deviceService.parsePatternOn(this.device);
    this.offState = this.deviceService.parsePatternOff(this.device);
  }

  sendParam() {
    this.deviceService.publishMessage(this.device.id, this.stateToSend).subscribe( response => {
      this.device.state = this.stateToSend;
    });
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
