import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class DeviceService {


  constructor(private http: HttpClient) { }

  switch(msg: string) {
    this.http.post<boolean>('/api/switchDevice?deviceId=' + 1 + '&msg=' + msg, {}).toPromise().then(data => {
      console.log(data);
    });
  }
}
