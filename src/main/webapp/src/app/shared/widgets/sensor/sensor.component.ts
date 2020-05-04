import { Component, OnInit, Input } from '@angular/core';
import { DeviceService } from '../../services/device.service';

@Component({
  selector: 'app-widget-sensor',
  templateUrl: './sensor.component.html',
  styleUrls: ['./sensor.component.scss']
})
export class SensorComponent implements OnInit {
  @Input() name: string;
  @Input() topic: string;
  @Input() image: string;
  @Input() data: boolean;
  @Input() dimension: boolean;


  imageURL: string;

  constructor(private deviceService: DeviceService) { }

  ngOnInit(): void {
    this.imageURL =  '../../../../assets/icons/' + this.image;
  }

  update() {
  }
}
