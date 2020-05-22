import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { ThemeService } from '../../services/theme.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  rand: number;
  @Output() toggleSideBarForMe: EventEmitter<any> = new EventEmitter();

  constructor(public themeService: ThemeService) { }

  ngOnInit() {
    this.rand = this.getRandomInt(5);
  }

  toggleSideBar() {
    this.toggleSideBarForMe.emit();
    setTimeout(() => {
      window.dispatchEvent(
        new Event('resize')
      );
    }, 300);
  }

  refresh() {
    window.location.href = '/login';
  }

  logout() {
    window.location.href = '/logout';
  }

  changeTheme() {
    this.themeService.switchTheme();
  }

  getHomieText() {
    switch (this.rand) {
      case 0: return ', wassup?';
      case 1: return ', wazzup?';
      case 2: return ', ssup?';
      case 3: return ', r u high?';
      case 4: return ', miss u';
    }
  }

  getRandomInt(max) {
    return Math.floor(Math.random() * Math.floor(max));
  }
}
