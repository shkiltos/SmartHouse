import { Component, OnInit } from '@angular/core';
import { DeviceService } from 'src/app/shared/services/device.service';
import { SchemeService } from 'src/app/shared/services/scheme.service';
import { FormControl, Validators } from '@angular/forms';
import { Scheme } from 'src/app/shared/model/scheme';
import { IDevice } from 'src/app/shared/model/device';
import { ThemeService } from 'src/app/shared/services/theme.service';

@Component({
  selector: 'app-scheme',
  templateUrl: './scheme.component.html',
  styleUrls: ['./scheme.component.scss']
})
export class SchemeComponent implements OnInit {

  schemes: Scheme[] = [];
  currentScheme: Scheme = {
    id: null,
    title: '',
    image: null,
    dashboardSchemeItems: []
  };

  deviceList: IDevice[] = [];
  schemeDevices: IDevice[] = [];
  isDisabled = false;

  backGroundImageBase64: any;
  selected = new FormControl(0);
  editMode = false;
  creatingNewScheme = false;

  formControl = new FormControl('', [
    Validators.required
    // Validators.email,
  ]);

  constructor(public deviceService: DeviceService, public schemeService: SchemeService, public themeService: ThemeService) { }

  ngOnInit(): void {
    this.deviceService.getDevices().subscribe( data => this.deviceList = data);
    this.refreshSchemes();
  }

  refreshSchemes() {
    this.schemeService.getSchemes().subscribe(data => {
      if (data) {
        if (data.length > 0) {
          this.schemes = data;
          this.currentScheme = this.schemes[this.selected.value];
          this.refreshSchemeDevices();
        }
      }
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

  selectTab($event) {
    if (!this.editMode) {
      this.selected.setValue($event);
      this.currentScheme = this.schemes[this.selected.value];
      this.refreshSchemeDevices();
    }
  }

  refreshSchemeDevices() {
    this.schemeDevices = [];
    this.currentScheme.dashboardSchemeItems.forEach( item => this.schemeDevices.push(this.deviceList.find(d => d.id === item.deviceId)));
  }

  save() {
    if (this.creatingNewScheme) {
      this.schemeService.createScheme(this.currentScheme);
      this.creatingNewScheme = false;
    } else {
      this.schemeService.updateScheme(this.currentScheme);
    }
    this.editMode = false;
  }

  addTab() {
    this.currentScheme.dashboardSchemeItems = [];
    const newScheme = {
      id: null,
      title: 'new scheme',
      image: null,
      dashboardSchemeItems: []
    };
    this.schemes.push(newScheme);
    this.selected.setValue(this.schemes.length - 1);
    this.currentScheme = newScheme;
    this.editMode = true;
    this.creatingNewScheme = true;
    this.refreshSchemeDevices();
  }

  removeTab(index: number, id: string) {
    if (this.selected.value === index) {
      this.schemeService.deleteScheme(id);
      this.schemes.splice(index, 1);
      this.cancelEditMode();
    }
  }

  switchToEditMode() {
    this.editMode = true;
  }

  cancelEditMode() {
    this.editMode = false;
    if (this.creatingNewScheme) {
      this.schemes.splice(this.schemes.length - 1, 1);
      this.creatingNewScheme = false;
    }
    this.refreshSchemes();
  }

  onFileSelected(event) {
    this.currentScheme.image = event.target.files[0];
    if (this.currentScheme.image) {
      const reader = new FileReader();

      reader.onload = this._handleReaderLoaded.bind(this);

      reader.readAsBinaryString(this.currentScheme.image);
    }
  }

  trimLong(str: string, limit: number) {
    if (str.length < limit){
      return str;
    }
    return str.slice(0, limit).concat((str.length > limit) ? '...' : '');
  }

  getBackground() {
    return 'url('
     + 'data:image/png;base64,'
     + (this.currentScheme.image && typeof this.currentScheme.image === 'string' ? this.currentScheme.image : this.backGroundImageBase64)
     + ')';
  }

  _handleReaderLoaded(readerEvt) {
    const binaryString = readerEvt.target.result;
    this.backGroundImageBase64 = btoa(binaryString);
  }

  addDeviceOnScheme(device: IDevice) {
    this.schemeDevices.push(device);
    // replace this with normal refresh or reset to default value
    this.deviceService.getDevices().subscribe( data => this.deviceList = data);

    if (!this.currentScheme.dashboardSchemeItems) {
      this.currentScheme.dashboardSchemeItems = [];
    }
    this.currentScheme.dashboardSchemeItems.push({ deviceId: device.id, x: 0, y: 0 });
  }

  removeDeviceFromScheme(i: number) {
    this.currentScheme.dashboardSchemeItems = this.currentScheme.dashboardSchemeItems
    .filter(item => item.deviceId !== this.schemeDevices[i].id);
    this.schemeDevices.splice(i, 1);
  }

  changePosition() {
    // this.dragPosition = {x: this.dragPosition.x + 50, y: this.dragPosition.y + 50};
  }

  onDragEnded(event, device: IDevice) {
    const element = event.source.getRootElement();
    const boundingClientRect = element.getBoundingClientRect();
    const parentPosition = this.getPosition(element);
    const dragDevice = this.currentScheme.dashboardSchemeItems.find( d => device.id === d.deviceId );
    dragDevice.x = boundingClientRect.x - parentPosition.left;
    dragDevice.y = boundingClientRect.y - parentPosition.top;
  }

  getPosition(el) {
    let x = 0;
    let y = 0;
    while (el && !isNaN(el.offsetLeft) && !isNaN(el.offsetTop)) {
      x += el.offsetLeft - el.scrollLeft;
      y += el.offsetTop - el.scrollTop;
      el = el.offsetParent;
    }
    return { top: y, left: x };
  }

  getDragPosition(device: IDevice) {
    if ( this.currentScheme.dashboardSchemeItems && this.currentScheme.dashboardSchemeItems.length > 0 ) {
      const dragDevice = this.currentScheme.dashboardSchemeItems.find( d => device.id === d.deviceId );
      return { x: dragDevice.x, y: dragDevice.y };
    } else {
      return { x: 0, y: 0 };
    }
  }

  isDeviceListItemDisabled(device: IDevice) {
    return this.schemeDevices.some(d => d.id === device.id);
  }
}
