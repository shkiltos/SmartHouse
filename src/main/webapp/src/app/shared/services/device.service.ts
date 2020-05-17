import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';
import { IDevice } from '../model/device';

const baseUrl = '/api/devices';

@Injectable({
  providedIn: 'root'
})
export class DeviceService {

  dialogData: any;

  constructor(private http: HttpClient, private snackBar: MatSnackBar) { }

  hasConnection() {
    return this.http.get<boolean>(baseUrl + '/hasConnection');
  }

  init() {
    return this.http.get<String>(baseUrl + '/init');
  }

  getDialogData() {
    return this.dialogData;
  }

  getDevices() {
    return this.http.get<IDevice[]>(baseUrl);
  }

  getDeviceById(id: string) {
    return this.http.get<IDevice>(baseUrl + '/' + id);
  }

  createDevice(device: IDevice) {
    this.http.post(baseUrl, device).subscribe(data => {
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
    this.http.put(baseUrl + '/' + device.id, device).subscribe(data => {
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
    return this.http.delete(baseUrl + '/' + id).subscribe( () => {
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

  publishMessage(deviceId: string, msg: string) {
    return this.http.post<String>(baseUrl + '/publishMessage' + '?deviceId=' + deviceId + '&msg=' + msg, {});
  }
}
