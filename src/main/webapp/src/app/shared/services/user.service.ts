import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { IUser } from '../model/user';
import { Observable } from 'rxjs';

// const CACHE_KEY_USER = 'httpUserImageCache';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  public user: IUser = {
    name: '',
    email: '',
    picture: '../../../../assets/default_user.png'
  };
  isLoggedIn = false;
  constructor(private http: HttpClient) {
  }

  fetchUser() {
    return this.http.get<IUser>('/api/user').subscribe( data => {
      this.user = data;
      this.isLoggedIn = true;
    });
  }

  fetchUserObservable(): Observable<IUser> {
    return this.http.get<IUser>('/api/user');
  }

  getUser() {
    return this.user;
  }

  isLogged() {
    return this.isLoggedIn;
  }
}
