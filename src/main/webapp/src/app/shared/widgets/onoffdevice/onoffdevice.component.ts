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
  @Input() image: string;
  @Input() state: boolean;


  imageURL: string;

  constructor(private deviceService: DeviceService) { }

  ngOnInit(): void {
    this.imageURL =  '../../../../assets/icons/' + this.image;
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
