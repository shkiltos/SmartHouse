import { Component, OnInit, Input } from '@angular/core';
import { DeviceService } from '../../services/device.service';
import { IDevice } from '../../model/device';

@Component({
  selector: 'app-widget-onoffdevice',
  templateUrl: './onoffdevice.component.html',
  styleUrls: ['./onoffdevice.component.scss']
})
export class OnoffdeviceComponent implements OnInit {
  @Input() device: IDevice;
  @Input() theme: boolean;
  onState: string;
  offState: string;

  imageName: string;

  constructor(public deviceService: DeviceService) { }

  ngOnInit(): void {
    this.imageName =  '../../../../assets/icons/' + this.device.image + '.svg';
    this.onState = this.deviceService.parsePatternOn(this.device);
    this.offState = this.deviceService.parsePatternOff(this.device);
  }

  toggle() {
    if (this.device.state === this.offState || this.device.state === null) {
      this.deviceService.publishMessage(this.device.id, this.onState).subscribe( response => {
        this.device.state = this.onState;
      });
    } else {
      this.deviceService.publishMessage(this.device.id, this.offState).subscribe( response => {
        this.device.state = this.offState;
      });
    }
  }
}
