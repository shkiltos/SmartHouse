import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { IUser } from '../model/user';

// const CACHE_KEY_USER = 'httpUserImageCache';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) {
  }

  fetchUser(): Observable<IUser> {
      return this.http.get<IUser>('/api/user');
  }
}
