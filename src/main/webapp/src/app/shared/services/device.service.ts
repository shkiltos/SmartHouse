import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';
import { IDevice } from '../model/device';

const baseUrl = '/api/devices';
const headers = new HttpHeaders().set('Content-Type', 'text/plain; charset=utf-8');

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
    return this.http.get<boolean>(baseUrl + '/init');
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

  publishOnOffMessage(deviceId: string, msg: string) {
    return this.http.post(baseUrl + '/publishOnOffMessage' + '?deviceId=' + deviceId + '&msg=' + msg, {}, { headers, responseType: 'text'});
  }

  publishMessage(deviceId: string, msg: string) {
    return this.http.post(baseUrl + '/publishMessage' + '?deviceId=' + deviceId + '&msg=' + msg, {}, { headers, responseType: 'text'});
  }

  parsePatternOn(device: IDevice) {
    if (device.switchPattern) {
      if (device.switchPattern.split(':')[0]) {
        return device.switchPattern.split(':')[0];
      }
    }
    return '0';
  }

  parsePatternOff(device: IDevice) {
    if (device.switchPattern) {
      if (device.switchPattern.split(':')[1]) {
        return device.switchPattern.split(':')[1];
      }
    }
    return '1';
  }
}
