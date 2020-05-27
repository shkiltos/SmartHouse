import { Component, OnInit, ViewChild } from '@angular/core';
import { DashboardService } from '../dashboard.service';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';


@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html',
  styleUrls: ['./reports.component.scss']
})
export class ReportsComponent implements OnInit {

  bigChart = [];
  cards1 = [21.1, 19.3, 18.9, 22.1];
  cards2 = [25.1, 30.2, 25.0, 32.5];
  cards3 = [21.1, 19.3, 18.9, 22.1];
  cards4 = [25.1, 30.2, 25.0, 32.5];

  pieChart = [];

  displayedColumns: string[] = ['position', 'name', 'weight', 'symbol'];

  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;

  constructor(private dashboardService: DashboardService) { }

  ngOnInit() {
    this.bigChart = this.dashboardService.bigChart();
    // this.cards1 = this.dashboardService.cards();
    this.pieChart = this.dashboardService.pieChart();
  }
}
