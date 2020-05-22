import { Component, OnInit, Input } from '@angular/core';
import { DeviceService } from '../../services/device.service';
import { IDevice } from '../../model/device';

@Component({
  selector: 'app-widget-sensor',
  templateUrl: './sensor.component.html',
  styleUrls: ['./sensor.component.scss']
})
export class SensorComponent implements OnInit {
  @Input() device: IDevice;
  @Input() image: string;
  @Input() theme: boolean;


  imageName: string;

  constructor(private deviceService: DeviceService) { }

  ngOnInit(): void {
    this.imageName =  '../../../../assets/icons/' + this.image;
  }

  update() {
  }
}
