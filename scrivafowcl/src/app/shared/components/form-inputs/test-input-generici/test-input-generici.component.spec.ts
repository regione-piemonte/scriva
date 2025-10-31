import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TestInputGenericiComponent } from './test-input-generici.component';

describe('TestInputGenericiComponent', () => {
  let component: TestInputGenericiComponent;
  let fixture: ComponentFixture<TestInputGenericiComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TestInputGenericiComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TestInputGenericiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
