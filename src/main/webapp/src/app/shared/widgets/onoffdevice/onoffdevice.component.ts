import { Component, OnInit, Input } from '@angular/core';
import { DeviceService } from '../../services/device.service';

@Component({
  selector: 'app-widget-onoffdevice',
  templateUrl: './onoffdevice.component.html',
  styleUrls: ['./onoffdevice.component.scss']
})
export class OnoffdeviceComponent implements OnInit {
  @Input() name: string;
  @Input() topic: string;
  @Input() state: boolean;

  constructor(private deviceService: DeviceService) { }

  ngOnInit(): void {
  }


  toggle() {
    this.state = !this.state;
    if (this.state) {
      this.deviceService.switch('0');
    } else {
      this.deviceService.switch('1');
    }
  }
}
