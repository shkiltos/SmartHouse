import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import {Component, Inject} from '@angular/core';
import {FormControl, Validators} from '@angular/forms';
import { DeviceService } from '../../services/device.service';
import { deviceTypes, dimension, images } from '../dialog.constants';

@Component({
  selector: 'app-baza.dialog',
  templateUrl: './edit.dialog.html',
  styleUrls: ['./edit.dialog.scss']
})
export class EditDialogComponent {

  deviceTypes = deviceTypes;
  dimensions: string[] = dimension;
  images: string[] = images;
  onPartSwitchPattern = this.deviceService.parsePatternOn(this.data);
  offPartSwitchPattern = this.deviceService.parsePatternOff(this.data);

  constructor(public dialogRef: MatDialogRef<EditDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any, public deviceService: DeviceService) {}

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
    return this.data.type !== undefined && this.data.type !== null;
  }

  submit() {
    // emppty stuff
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  stopEdit(): void {
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

    this.deviceService.updateDevice(this.data);
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
