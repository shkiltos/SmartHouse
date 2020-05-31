import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/shared/services/user.service';
import { FormControl, Validators } from '@angular/forms';
import { ICamera } from 'src/app/shared/model/camera';

interface ISettings {
  userName: string;
  maxEnergyConsumption: number;
  picture: string;
  cams: ICamera[];
}

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.scss']
})
export class SettingsComponent implements OnInit {
  public settings: ISettings = {
    userName: this.userService.user.name,
    maxEnergyConsumption: this.userService.user.maxEnergyConsumption,
    picture: this.userService.user.picture,
    cams: this.userService.user.cams
  };

  formControl = new FormControl('', [
    Validators.required
    // Validators.email,
  ]);

  constructor(public userService: UserService) {}

  ngOnInit(): void {
    this.userService.fetchUserObservable().subscribe(user => {
      this.settings.userName = user.name;
      this.settings.maxEnergyConsumption = user.maxEnergyConsumption;
      this.settings.picture = user.picture;
      this.settings.cams = user.cams;
      if (this.settings.cams === undefined || this.settings.cams === null) this.settings.cams = [];
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
    this.userService.fetchUser();
  }

  addCamera() {
    const camera = {name: 'Camera name', value: 'iframe url'};
    this.settings.cams.push(camera);
  }

  removeCamera(index: number) {
    this.settings.cams.splice(index, 1);
  }
}
