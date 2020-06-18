import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { DeviceService } from 'src/app/shared/services/device.service';
import { MatDialog } from '@angular/material/dialog';
import { HttpClient } from '@angular/common/http';
import { AddDialogComponent } from 'src/app/shared/dialogs/add/add.dialog.component';
import { EditDialogComponent } from 'src/app/shared/dialogs/edit/edit.dialog.component';
import { DeleteDialogComponent } from 'src/app/shared/dialogs/delete/delete.dialog.component';
import { IDevice } from 'src/app/shared/model/device';


@Component({
  selector: 'app-devices',
  templateUrl: './devices.component.html',
  styleUrls: ['./devices.component.scss']
})
export class DevicesComponent implements OnInit {
  displayedColumns: string[] = ['name', 'topic', 'type', 'actions'];
  dataSource: MatTableDataSource<IDevice>;
  devices: IDevice[];

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  constructor(public dialog: MatDialog,
              public deviceService: DeviceService) {
  }

  ngOnInit() {
    this.refreshTable();
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  private refreshTable() {
    this.deviceService.getDevices().subscribe( data => {
      this.devices = data;
      this.dataSource = new MatTableDataSource(this.devices);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    });
  }

  addNew() {
    const dialogRef = this.dialog.open(AddDialogComponent, {
      data: {}
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result === 1) {
        this.refreshTable();
      }
    });
  }

  startEdit(id: string) {
    this.deviceService.getDeviceById(id).subscribe(data => {
      const dialogRef = this.dialog.open(EditDialogComponent, {
        data
      });

      dialogRef.afterClosed().subscribe(result => {
        if (result === 1) {
          this.refreshTable();
        }
      });
    });
  }

  deleteItem(id: string) {
    this.deviceService.getDeviceById(id).subscribe(data => {
      const dialogRef = this.dialog.open(DeleteDialogComponent, {
        data
      });

      dialogRef.afterClosed().subscribe(result => {
        if (result === 1) {
          this.refreshTable();
        }
      });
    });
  }
}

