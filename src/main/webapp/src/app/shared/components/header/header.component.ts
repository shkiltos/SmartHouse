import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { ThemeService } from '../../services/theme.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  @Output() toggleSideBarForMe: EventEmitter<any> = new EventEmitter();

  constructor(public themeService: ThemeService) { }

  ngOnInit() { }

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
}
