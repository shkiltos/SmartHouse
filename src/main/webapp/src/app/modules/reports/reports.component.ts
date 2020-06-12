import { Component, OnInit, ViewChild } from '@angular/core';
import { ReportService } from 'src/app/shared/services/report.service';
import { IDevice } from 'src/app/shared/model/device';
import { SensorReport } from 'src/app/shared/model/sensor.report';


@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html',
  styleUrls: ['./reports.component.scss']
})
export class ReportsComponent implements OnInit {
  sensors: SensorReport[];

  bigChart = [];

  pieChart = [];

  constructor(private reportService: ReportService) { }

  ngOnInit() {
    this.bigChart = this.reportService.bigChart();
    this.pieChart = this.reportService.pieChart();

    this.reportService.fetchSensorData().subscribe( data => {
      this.sensors = data;
    });
  }

  getPercentage(recentData: number[]): number {
    return recentData ? 100 * (recentData[recentData.length - 1] - recentData[0]) / recentData[0] : null;
  }

  toFloat(stringArray: string[]) {
    return stringArray ? stringArray.map( str => parseFloat(str)) : null;
  }
}
