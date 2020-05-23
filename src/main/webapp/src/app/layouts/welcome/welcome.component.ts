import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from 'src/app/shared/services/user.service';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.scss']
})
export class WelcomeComponent implements OnInit {
  username: string;

  constructor(public router: Router, private userService: UserService) {}

  ngOnInit() {
    this.userService.fetchUserObservable().subscribe(user => {
      this.userService.user = user;
      this.startWork();
    });
    // this.username = this.getUserEmail();
  }

  getUserEmail() {
    return this.userService.user.email;
  }

  startWork() {
    this.router.navigate(['/', this.getUserEmail()]);
  }

  logIn() {
    window.location.href = '/login';
  }
}
