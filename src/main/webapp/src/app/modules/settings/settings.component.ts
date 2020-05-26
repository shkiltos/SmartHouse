import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/shared/services/user.service';
import { FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.scss']
})
export class SettingsComponent implements OnInit {
  public settings = {
    userName: this.userService.user.name,
    maxEnergyConsumption: this.userService.user.maxEnergyConsumption,
    picture: this.userService.user.picture
  };

  formControl = new FormControl('', [
    Validators.required
    // Validators.email,
  ]);

  constructor(public userService: UserService) { }

  ngOnInit(): void {
    this.userService.fetchUserObservable().subscribe(user => {
      this.settings.userName = user.name;
      this.settings.maxEnergyConsumption = user.maxEnergyConsumption;
      this.settings.picture = user.picture;
    });
  }

  submit() {
  // emppty stuff
  }

  getErrorMessage() {
    return this.formControl.hasError('required') ? 'Required field' :
      this.formControl.hasError('email') ? 'Not a valid email' :
        '';
  }

  public updateSettings(settings: any) {
    this.userService.updateSettings(settings);
  }
}
