import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { IUser } from '../model/user';
import { Observable } from 'rxjs';
import { MatSnackBar } from '@angular/material/snack-bar';

// const CACHE_KEY_USER = 'httpUserImageCache';
const baseUrl = '/api/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  public user: IUser = {
    name: '',
    email: '',
    picture: '../../../../assets/default_user.png',
    maxEnergyConsumption: 0
  };
  isLoggedIn = false;
  constructor(private http: HttpClient, private snackBar: MatSnackBar) {
  }

  fetchUser() {
    return this.http.get<IUser>(baseUrl).subscribe( data => {
      this.user = data;
      this.isLoggedIn = true;
    });
  }

  fetchUserObservable(): Observable<IUser> {
    return this.http.get<IUser>(baseUrl);
  }

  getUser() {
    return this.user;
  }

  isLogged() {
    return this.isLoggedIn;
  }

  updateSettings(settings: any) {
    this.http.post(baseUrl + '/settings', settings).subscribe(data => {
      this.snackBar.open('Successfully updated user settings', 'OK', {
        duration: 2000,
      });
      },
      (err: HttpErrorResponse) => {
        this.snackBar.open('Error occurred. Details: ' + err.name + ' ' + err.message, 'OK', {
          duration: 5000,
      });
    });
  }
}
