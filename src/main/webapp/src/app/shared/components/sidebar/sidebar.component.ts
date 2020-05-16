import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { IUser } from '../../model/user';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent implements OnInit {
  user: IUser = {
    name: '',
    email: '',
    picture: '../../../../assets/default_user.png'
  };

  constructor(private userService: UserService) {
  }

  ngOnInit() {
    this.userService.fetchUser().subscribe( data => this.user = data );
  }

}
