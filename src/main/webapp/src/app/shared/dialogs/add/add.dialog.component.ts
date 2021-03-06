import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Component, Inject } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { DeviceService } from '../../services/device.service';
import { IDevice } from '../../model/device';
import { deviceTypes, dimension, images } from '../dialog.constants';

@Component({
  selector: 'app-add.dialog',
  templateUrl: './add.dialog.html',
  styleUrls: ['./add.dialog.scss']
})

export class AddDialogComponent {

  deviceTypes = deviceTypes;
  dimensions: string[] = dimension;
  images: string[] = images;
  onPartSwitchPattern = '1';
  offPartSwitchPattern = '0';

  constructor(public dialogRef: MatDialogRef<AddDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: IDevice,
              public deviceService: DeviceService,
              ) { }

  formControl = new FormControl('', [
    Validators.required
    // Validators.email,
  ]);

  getErrorMessage() {
    return this.formControl.hasError('required') ? 'Required field' :
      this.formControl.hasError('email') ? 'Not a valid email' :
        '';
  }

  isValid() {
    return this.data.type !== undefined;
  }

  submit() {
  // emppty stuff
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  public confirmAdd(): void {
    this.data.switchPattern = this.createPattern();

    // getting rid of extra data for all types
    switch (this.data.type) {
      case 'onoffdevice': {
        this.data.dimension = null;
        break;
      }
      case 'sensor': {
        this.data.switchPattern = null;
        this.data.energyConsumption = null;
        break;
      }
    }

    this.deviceService.createDevice(this.data);
  }

  createPattern(): string {
    if (this.onPartSwitchPattern && this.offPartSwitchPattern) {
      return this.onPartSwitchPattern + ':' + this.offPartSwitchPattern;
    }
    return '1:0';
  }

  getImageUrl(image: string) {
    return './assets/icons/' + image + '.svg';
  }
}
