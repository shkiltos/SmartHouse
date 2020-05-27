import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  constructor() { }

  bigChart() {
    return [{
      name: 'February',
      data: [230, 230, 275, 121, 100, 116, 200, 100, 70]
    }, {
      name: 'March',
      data: [250, 200, 210, 195, 193, 240, 254, 230, 205]
    }, {
      name: 'April',
      data: [104, 110, 125, 126, 124, 129, 132, 133, 130]
    }, {
      name: 'May',
      data: [106, 200, 215, 215, 234, 205, 210, 220, 205]
    }];
  }

  cards() {
    return [21.1, 19.3, 18.9, 22.1];
  }

  pieChart() {
    return [{
      name: 'Chrome',
      y: 61.41,
      sliced: true,
      selected: true
    }, {
      name: 'Internet Explorer',
      y: 11.84
    }, {
      name: 'Firefox',
      y: 10.85
    }, {
      name: 'Edge',
      y: 4.67
    }, {
      name: 'Safari',
      y: 4.18
    }, {
      name: 'Sogou Explorer',
      y: 1.64
    }, {
      name: 'Opera',
      y: 1.6
    }, {
      name: 'QQ',
      y: 1.2
    }, {
      name: 'Other',
      y: 2.61
    }];
  }
}
