import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OnoffdeviceComponent } from './onoffdevice.component';

describe('OnoffdeviceComponent', () => {
  let component: OnoffdeviceComponent;
  let fixture: ComponentFixture<OnoffdeviceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OnoffdeviceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OnoffdeviceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
