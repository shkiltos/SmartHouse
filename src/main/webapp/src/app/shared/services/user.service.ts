import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';


export interface User {
  name: string;
  email: string;
  picture: string;
}

// const CACHE_KEY_USER = 'httpUserImageCache';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  public user: User;
  // public userImageObservable: Observable<string>;

  constructor(private http: HttpClient) {
      // this.http.get('/api/getPrismUserPhoto', {responseType: 'text'}).pipe(url => this.userImageObservable = url);

      // this.userImageObservable.subscribe(next => {
      //     localStorage[CACHE_KEY_IMAGE] = JSON.stringify(next);
      // });
      // this.userImageObservable = this.userImageObservable.pipe(
      //     startWith(JSON.parse(localStorage[CACHE_KEY_IMAGE] || 'null'))
      // );
  }

  fetchUser() {
      this.http.get<User>('/api/user').toPromise().then(data => {
        this.user = data;
        console.log(this.user);
      });;
  }
}
