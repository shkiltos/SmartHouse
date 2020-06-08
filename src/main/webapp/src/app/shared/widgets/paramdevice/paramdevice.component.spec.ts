import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ParamdeviceComponent } from './paramdevice.component';

describe('ParamdeviceComponent', () => {
  let component: ParamdeviceComponent;
  let fixture: ComponentFixture<ParamdeviceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ParamdeviceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ParamdeviceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
