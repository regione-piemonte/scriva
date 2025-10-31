import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StepperIstanzaComponent } from './stepper-istanza.component';

describe('StepperIstanzaComponent', () => {
  let component: StepperIstanzaComponent;
  let fixture: ComponentFixture<StepperIstanzaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StepperIstanzaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StepperIstanzaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
