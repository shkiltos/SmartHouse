import { Component, OnInit, Input } from '@angular/core';
import { DeviceService } from '../../services/device.service';
import { IDevice } from '../../model/device';

@Component({
  selector: 'app-widget-onoffdevice',
  templateUrl: './onoffdevice.component.html',
  styleUrls: ['./onoffdevice.component.scss']
})
export class OnoffdeviceComponent implements OnInit {
  @Input() image: string;
  @Input() device: IDevice;
  @Input() theme: boolean;


  imageName: string;

  constructor(private deviceService: DeviceService) { }

  ngOnInit(): void {
    this.imageName =  '../../../../assets/icons/' + this.image;
  }


  toggle() {
    if (this.device.state === '0') {
      this.deviceService.publishMessage(this.device.id, '1').subscribe( response => {
        this.device.state = '1';
      });
    } else {
      this.deviceService.publishMessage(this.device.id, '0').subscribe( response => {
        this.device.state = '0';
      });
    }
  }
}
