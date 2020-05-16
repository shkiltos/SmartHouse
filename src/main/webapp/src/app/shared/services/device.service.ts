import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';
import { IDevice } from '../model/device';


@Injectable({
  providedIn: 'root'
})
export class DeviceService {

  // dataChange: BehaviorSubject<IDevice[]> = new BehaviorSubject<IDevice[]>([]);
  dialogData: any;

  baseUrl = '/api/devices';

  constructor(private http: HttpClient, private snackBar: MatSnackBar) { }

  // get data(): IDevice[] {
  //   return this.dataChange.value;
  // }

  getDialogData() {
    return this.dialogData;
  }

  getDevices() {
    return this.http.get<IDevice[]>(this.baseUrl);
  }

  getDeviceById(id: string) {
    return this.http.get<IDevice>(this.baseUrl + '/' + id);
  }

  createDevice(device: IDevice) {
    this.http.post(this.baseUrl, device).subscribe(data => {
      this.dialogData = device;
      this.snackBar.open('Successfully added', 'OK', {
        duration: 2000,
      });
      },
      (err: HttpErrorResponse) => {
        this.snackBar.open('Error occurred. Details: ' + err.name + ' ' + err.message, 'OK', {
          duration: 5000,
        });
    });
  }

  updateDevice(device: IDevice) {
    this.http.put(this.baseUrl + '/' + device.id, device).subscribe(data => {
      this.dialogData = device;
      this.snackBar.open('Successfully edited', 'OK', {
        duration: 2000,
      });
    },
    (err: HttpErrorResponse) => {
      this.snackBar.open('Error occurred. Details: ' + err.name + ' ' + err.message, 'OK', {
        duration: 5000,
      });
    }
  );
  }

  deleteDevice(id: string) {
    return this.http.delete(this.baseUrl + '/' + id).subscribe( () => {
      this.snackBar.open('Successfully deleted', 'OK', {
        duration: 2000,
      });
    },
    (err: HttpErrorResponse) => {
      this.snackBar.open('Error occurred. Details: ' + err.name + ' ' + err.message, 'OK', {
        duration: 5000,
      });
    }
    );
  }

  switch(msg: string) {
    this.http.post<boolean>('/api/devices/switchDevice?deviceId=' + 1 + '&msg=' + msg, {}).toPromise().then(data => {
      console.log(data);
    });
  }
}
